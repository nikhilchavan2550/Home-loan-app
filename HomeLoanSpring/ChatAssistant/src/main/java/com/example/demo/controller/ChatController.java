package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.List;
import java.util.Arrays;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = {"http://localhost:5501", "http://127.0.0.1:5501", "http://localhost:3000", "http://127.0.0.1:3000"})
public class ChatController {

    private static final String GEMINI_API_KEY = "";
    private static final String GEMINI_API_URL = "";

    @Autowired
    private RestTemplate restTemplate;

    // Home loan keywords for topic validation
    private static final List<String> HOME_LOAN_KEYWORDS = Arrays.asList(
        "home loan", "mortgage", "emi", "interest rate", "eligibility", "documentation",
        "down payment", "principal", "tenure", "prepayment", "foreclosure", "refinance",
        "home purchase", "property", "real estate", "housing loan", "construction loan",
        "land purchase", "home improvement", "tax benefit", "deduction", "repayment",
        "loan amount", "credit score", "income proof", "bank statement", "salary slip",
        "property documents", "agreement", "registration", "stamp duty", "processing fee",
        "legal charges", "valuation", "insurance", "guarantor", "co-applicant"
    );

    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestBody ChatRequest request) {
        try {
            // Validate if question is related to home loans
            if (!isHomeLoanRelated(request.getMessage())) {
                return ResponseEntity.ok(new ChatResponse(
                    "I'm sorry, but I can only answer questions related to home loans. " +
                    "Please ask me about home loan eligibility, interest rates, documentation, " +
                    "application process, EMI calculations, tax benefits, or any other home loan related topics. " +
                    "How can I help you with your home loan questions?"
                ));
            }

            // Create the prompt for Gemini API
            String prompt = createPrompt(request.getMessage());

            // Call Gemini API
            String response = callGeminiAPI(prompt);

            return ResponseEntity.ok(new ChatResponse(response));

        } catch (Exception e) {
            System.err.println("Error in chat controller: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ChatResponse("I apologize, but I'm experiencing technical difficulties right now. Please try again in a moment."));
        }
    }

    private boolean isHomeLoanRelated(String question) {
        String lowerQuestion = question.toLowerCase();
        
        // Check for home loan keywords
        boolean hasKeywords = HOME_LOAN_KEYWORDS.stream()
            .anyMatch(keyword -> lowerQuestion.contains(keyword));
        
        // Check for general loan/home related terms
        boolean hasGeneralTerms = lowerQuestion.contains("loan") || 
                                 lowerQuestion.contains("home") ||
                                 lowerQuestion.contains("property") ||
                                 lowerQuestion.contains("house") ||
                                 lowerQuestion.contains("mortgage");
        
        return hasKeywords || hasGeneralTerms;
    }

    private String createPrompt(String userQuestion) {
        return "You are an AI assistant specialized in home loan applications, powered by the Gemini API. " +
               "Your primary function is to answer questions exclusively related to home loans, including but not limited to: " +
               "eligibility criteria, interest rates, documentation required, application process, repayment options, " +
               "tax benefits, and common terminology. " +
               "\n\n**New Feature Integration: Google Authentication**\n" +
               "The application has now integrated Google Authentication to provide a convenient login and registration option for users. " +
               "This feature allows users to:\n" +
               "• Sign in to their existing accounts using their Google credentials\n" +
               "• Register for a new account using their Google profile, streamlining the onboarding process\n\n" +
               "**User Guidance for Google Auth:**\n" +
               "If a user asks about logging in, signing up, or account creation, you should inform them about the Google login option. " +
               "Specifically, instruct them:\n" +
               "1. **To log in using Google:** \"You can now log in using your Google account. Look for the 'Sign in with Google' button usually located below the email and password fields on the login page.\"\n" +
               "2. **To register using Google:** \"If you don't have an account, you can quickly sign up using your Google account. Navigate to the registration page, and you will find a 'Sign up with Google' option.\"\n\n" +
               "**Important Constraints:**\n" +
               "• You do **NOT** handle the actual login or registration process. Your role is solely to provide information and guidance on *how* to use this new feature.\n" +
               "• You **MUST NOT** ask for or process any sensitive user credentials (passwords, Google account details, etc.).\n" +
               "• You **MUST NOT** engage in or answer questions about general topics, current events, personal opinions, or any subject outside the scope of home loans or directly related application navigation/login guidance. " +
               "If a user asks a question unrelated to these defined scopes, politely redirect them to ask about home loan topics or state that you are unable to answer that specific query.\n\n" +
               "Your responses should be clear, concise, accurate, and helpful. Maintain a professional and informative tone. " +
               "Use proper formatting with **bold text** for important terms, bullet points (•) for lists, and clear structure. " +
               "For formulas, use **bold** for variable names and provide clear explanations. " +
               "Remember, your sole purpose is to assist users with their home loan queries and guide them through the basic application flow, including awareness of the Google login feature. " +
               "\n\nUser Question: " + userQuestion + "\n\nPlease provide a helpful response focused only on home loan matters:";
    }

    private String callGeminiAPI(String prompt) {
        try {
            // Create request body
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode requestBody = mapper.createObjectNode();
            ArrayNode contents = mapper.createArrayNode();
            ObjectNode content = mapper.createObjectNode();
            ArrayNode parts = mapper.createArrayNode();
            ObjectNode part = mapper.createObjectNode();
            
            part.put("text", prompt);
            parts.add(part);
            content.set("parts", parts);
            contents.add(content);
            requestBody.set("contents", contents);

            // Set headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create HTTP entity
            HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);

            // Make API call
            String url = GEMINI_API_URL + "?key=" + GEMINI_API_KEY;
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                // Parse response
                ObjectNode responseJson = mapper.readValue(response.getBody(), ObjectNode.class);
                
                if (responseJson.has("candidates") && responseJson.get("candidates").isArray()) {
                    ArrayNode candidates = (ArrayNode) responseJson.get("candidates");
                    if (candidates.size() > 0) {
                        ObjectNode candidate = (ObjectNode) candidates.get(0);
                        if (candidate.has("content")) {
                            ObjectNode contentNode = (ObjectNode) candidate.get("content");
                            if (contentNode.has("parts") && contentNode.get("parts").isArray()) {
                                ArrayNode partsArray = (ArrayNode) contentNode.get("parts");
                                if (partsArray.size() > 0) {
                                    ObjectNode partNode = (ObjectNode) partsArray.get(0);
                                    if (partNode.has("text")) {
                                        return partNode.get("text").asText();
                                    }
                                }
                            }
                        }
                    }
                }
            }

            throw new RuntimeException("Invalid response format from Gemini API");

        } catch (Exception e) {
            System.err.println("Error calling Gemini API: " + e.getMessage());
            throw new RuntimeException("Failed to get response from AI service", e);
        }
    }

    // Request and Response classes
    public static class ChatRequest {
        private String message;

        public ChatRequest() {}

        public ChatRequest(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public static class ChatResponse {
        private String response;

        public ChatResponse() {}

        public ChatResponse(String response) {
            this.response = response;
        }

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }
    }
} 