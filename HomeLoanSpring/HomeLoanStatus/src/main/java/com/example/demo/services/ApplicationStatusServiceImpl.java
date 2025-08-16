package com.example.demo.services;

import com.example.demo.dto.IncomeDetailsResponseDto;
import com.example.demo.dto.LoanApplicationResponseDto;
import com.example.demo.entities.ApplicationStatus;

import com.example.demo.entities.ApplicationStatusHistory;
import com.example.demo.entities.LoanAccount;
import com.example.demo.repositories.ApplicationStatusRepository;
import com.example.demo.repositories.LoanAccountRepository;
import com.example.demo.repositories.ApplicationStatusHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ApplicationStatusServiceImpl implements ApplicationStatusService {

    @Autowired
    private ApplicationStatusRepository applicationStatusRepository;

    @Autowired
    private ApplicationStatusHistoryRepository historyRepository;
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private LoanAccountRepository loanAccountRepository;


    @Override
	public ApplicationStatus createNewApplication(Long applicationId) {
        ApplicationStatus application = new ApplicationStatus();
        application.setApplicationId(applicationId);
        application.setCurrentStatus("PENDING");

        ApplicationStatus saved = applicationStatusRepository.save(application);

        ApplicationStatusHistory history = new ApplicationStatusHistory();
        history.setApplication(saved);
        history.setStatus("PENDING");
        history.setChangedAt(LocalDate.now());
        history.setChangedByAdminId(null); 

        historyRepository.save(history);
        notifyNodeApi(applicationId, "Your loan application request has been sent to the Admin!!");


        return saved;
    }

    @Override
	public ApplicationStatus updateStatus(Long applicationId, String newStatus, Long adminId) {
        ApplicationStatus application = applicationStatusRepository.findByApplicationId(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        application.setCurrentStatus(newStatus);
        applicationStatusRepository.save(application);

        ApplicationStatusHistory history = new ApplicationStatusHistory();
        history.setApplication(application);
        history.setStatus(newStatus);
        history.setChangedAt(LocalDate.now());
        history.setChangedByAdminId(adminId);
        historyRepository.save(history);
        

        
        

        if (newStatus.equalsIgnoreCase("approved")) {
            if (loanAccountRepository.findByApplicationId(applicationId) == null) {
            	
            	String loanAppUrl = "http://localhost:8080/api/loan/" + applicationId;

                LoanApplicationResponseDto loanApplication = null;

                try {
                    ResponseEntity<LoanApplicationResponseDto> loanResponse =
                            restTemplate.getForEntity(loanAppUrl, LoanApplicationResponseDto.class);
                    if (loanResponse.getStatusCode() == HttpStatus.OK) {
                        loanApplication = loanResponse.getBody();
                        System.out.println("✅ Loan application fetched: " + loanApplication.getRequestedLoanAmount());
                    } else {
                        throw new RuntimeException("❌ Failed to fetch loan application. Status: " + loanResponse.getStatusCode());
                    }
                } catch (Exception e) {
                    System.err.println("❌ Error fetching loan application: " + e.getMessage());
                    throw new RuntimeException("Failed to fetch loan application");
                }
                
                String incomeUrl = "http://localhost:8080/api/income/" + loanApplication.getIncomeId();

                IncomeDetailsResponseDto incomeDetails = null;

                try {
                    ResponseEntity<IncomeDetailsResponseDto> incomeResponse =
                            restTemplate.getForEntity(incomeUrl, IncomeDetailsResponseDto.class);
                    if (incomeResponse.getStatusCode() == HttpStatus.OK) {
                        incomeDetails = incomeResponse.getBody();
                    } else {
                        throw new RuntimeException("❌ Failed to fetch loan application. Status: " + incomeResponse.getStatusCode());
                    }
                } catch (Exception e) {
                    System.err.println("❌ Error fetching loan application: " + e.getMessage());
                    throw new RuntimeException("Failed to fetch loan application");
                }
            	
             // Assuming incomeDetails is fetched from income service or from loanApplication
                Double monthlyIncome = incomeDetails.getMonthlyIncome().doubleValue();
                Double principal = loanApplication.getRequestedLoanAmount().doubleValue();
                Double annualRate = 8.0; // 8% interest
                Integer tenureMonths = loanApplication.getLoanTenureYears() * 12;

                double monthlyRate = annualRate / 12 / 100;

                double emi = (principal * monthlyRate * Math.pow(1 + monthlyRate, tenureMonths)) /
                             (Math.pow(1 + monthlyRate, tenureMonths) - 1);


                LoanAccount loanAccount = new LoanAccount();
                loanAccount.setAccountNumber("LN" + System.currentTimeMillis());
                loanAccount.setApplicationId(applicationId);
                loanAccount.setDisbursementDate(LocalDate.now());
                loanAccount.setPrincipalAmount(principal);
                loanAccount.setInterestRate(annualRate);
                loanAccount.setEmiAmount(emi);  // Now using calculated EMI

                

                LoanAccount savedAccount = loanAccountRepository.save(loanAccount);

                try {
                	
                    String emiUrl = "http://localhost:8081/api/emi/generate?accountNumber=" 
                                  + savedAccount.getAccountNumber()
                                  + "&emiAmount=" + savedAccount.getEmiAmount()
                                  + "&durationInMonths="+tenureMonths; // or any duration you choose

                    ResponseEntity<String> response = restTemplate.postForEntity(emiUrl, null, String.class);

                    if (response.getStatusCode() == HttpStatus.OK) {
                        System.out.println("✅ EMI schedule created.");


                    } else {
                        System.err.println("⚠️ EMI schedule creation failed: " + response.getStatusCode());
                    }
                } catch (Exception e) {
                    System.err.println("❌ Error calling EMI API: " + e.getMessage());
                }
                
                notifyNodeApi(applicationId, 
                        "Your new loan account has been created and your EMI schedule has been processed.\n" +
                        "Account Number: " + savedAccount.getAccountNumber() + "\n" +
                        "EMI Amount: " + savedAccount.getEmiAmount());
            }
        }
        else
        {
            notifyNodeApi(applicationId, "Your loan account status has been updated to "+ newStatus);

        }

        return application;
    }
    
    @Override
	public ApplicationStatus revertLastStatus(Long applicationId) {
        ApplicationStatus application = applicationStatusRepository.findByApplicationId(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        List<ApplicationStatusHistory> historyList = application.getStatusHistory();

        if (historyList.size() < 2) {
            throw new IllegalStateException("Cannot revert. Only one status exists.");
        }

        historyList.sort((a, b) -> b.getId().compareTo(a.getId()));

        ApplicationStatusHistory latest = historyList.get(0);
        ApplicationStatusHistory previous = historyList.get(1);

        String currentStatus = latest.getStatus();

        if ("approved".equalsIgnoreCase(currentStatus)) {
            LoanAccount account = loanAccountRepository.findByApplicationId(applicationId);
            if (account != null) {
                loanAccountRepository.delete(account);

                String deleteUrl = "http://localhost:8081/api/emi/" + account.getAccountNumber();

                try {
                    HttpClient client = HttpClient.newHttpClient();
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create(deleteUrl))
                            .DELETE()
                            .build();

                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                    if (response.statusCode() == 200) {
                        System.out.println("✅ EMI schedule deleted via HTTP DELETE for account: " + account.getAccountNumber());
                    } else {
                        System.err.println("❌ Failed to delete EMI schedule. Status: " + response.statusCode());
                    }
                } catch (Exception e) {
                    System.err.println("❌ Exception during EMI schedule deletion: " + e.getMessage());
                }
            }
        }
        
        notifyNodeApi(applicationId, "Your Loan Application Status has been reverted to "+previous.getStatus());



        historyRepository.delete(latest);
        historyList.remove(0);

        application.setCurrentStatus(previous.getStatus());
        applicationStatusRepository.save(application);

        return application;
    }


    @Override
	public Optional<ApplicationStatus> getStatusByApplicationId(Long applicationId) {
        return applicationStatusRepository.findByApplicationId(applicationId);
    }
    
    private void notifyNodeApi(Long applicationId, String message) {
        String nodeApiUrl = "http://localhost:3000/send-email";

        Map<String, Object> payload = new HashMap<>();
        payload.put("to", "sahilbhilave@gmail.com"); // or dummy email
        payload.put("subject", "Loan Application Status Notification");
        payload.put("message", message);
        payload.put("applicationId", applicationId);

        try {
            restTemplate.postForEntity(nodeApiUrl, payload, String.class);
            System.out.println("Simulated email request sent to Node.js API.");
        } catch (Exception e) {
            System.err.println("Error calling Node.js API: " + e.getMessage());
        }
    }

}
