// Home Loan Assistant JavaScript

document.addEventListener('DOMContentLoaded', function() {
    const chatForm = document.getElementById('chat-form');
    const chatInput = document.getElementById('chat-input');
    const chatMessages = document.getElementById('chat-messages');
    const sendButton = document.getElementById('send-button');
    const quickQuestions = document.querySelectorAll('.quick-question');



    // Home loan keywords for topic validation
    const homeLoanKeywords = [
        'home loan', 'mortgage', 'emi', 'interest rate', 'eligibility', 'documentation',
        'down payment', 'principal', 'tenure', 'prepayment', 'foreclosure', 'refinance',
        'home purchase', 'property', 'real estate', 'housing loan', 'construction loan',
        'land purchase', 'home improvement', 'tax benefit', 'deduction', 'repayment',
        'loan amount', 'credit score', 'income proof', 'bank statement', 'salary slip',
        'property documents', 'agreement', 'registration', 'stamp duty', 'processing fee',
        'legal charges', 'valuation', 'insurance', 'guarantor', 'co-applicant'
    ];

    // Function to check if question is related to home loans
    function isHomeLoanRelated(question) {
        const lowerQuestion = question.toLowerCase();
        return homeLoanKeywords.some(keyword => lowerQuestion.includes(keyword)) ||
               lowerQuestion.includes('loan') || 
               lowerQuestion.includes('home') ||
               lowerQuestion.includes('property') ||
               lowerQuestion.includes('house') ||
               lowerQuestion.includes('mortgage');
    }

    // Function to format AI response text
    function formatResponseText(text) {
        // Replace **text** with <strong>text</strong> for bold
        text = text.replace(/\*\*(.*?)\*\*/g, '<strong>$1</strong>');
        
        // Replace *text* with <em>text</em> for italic
        text = text.replace(/\*(.*?)\*/g, '<em>$1</em>');
        
        // Replace bullet points with proper HTML lists
        text = text.replace(/^\s*•\s*(.*)$/gm, '<li>$1</li>');
        text = text.replace(/^\s*-\s*(.*)$/gm, '<li>$1</li>');
        
        // Replace numbered lists
        text = text.replace(/^\s*(\d+)\.\s*(.*)$/gm, '<li>$2</li>');
        
        // Wrap lists in <ul> tags
        text = text.replace(/(<li>.*<\/li>)/gs, '<ul class="list-disc list-inside space-y-1 my-2">$1</ul>');
        
        // Replace line breaks with <br> tags
        text = text.replace(/\n/g, '<br>');
        
        // Add spacing around formulas
        text = text.replace(/(EMI\s*=\s*\[.*?\])/g, '<div class="bg-gray-200 p-3 rounded-lg my-3 font-mono text-sm">$1</div>');
        
        // Add spacing around Where: sections
        text = text.replace(/(Where:)/g, '<div class="mt-3 mb-2"><strong>$1</strong></div>');
        
        // Add spacing around variable definitions
        text = text.replace(/(\*\*[^*]+\*\*\s*=\s*[^*]+)/g, '<div class="ml-4 mb-1">$1</div>');
        
        return text;
    }

    // Function to add message to chat
    function addMessage(content, isUser = false) {
        const messageDiv = document.createElement('div');
        messageDiv.className = 'flex items-start space-x-3';
        
        if (isUser) {
            messageDiv.innerHTML = `
                <div class="w-8 h-8 bg-gray-400 rounded-full flex items-center justify-center flex-shrink-0">
                    <span class="text-white text-sm">👤</span>
                </div>
                <div class="bg-primary text-white rounded-lg p-4 max-w-3xl">
                    <p>${content}</p>
                </div>
            `;
        } else {
            const formattedContent = formatResponseText(content);
            messageDiv.innerHTML = `
                <div class="w-8 h-8 bg-primary rounded-full flex items-center justify-center flex-shrink-0">
                    <span class="text-white text-sm">🤖</span>
                </div>
                <div class="bg-gray-100 rounded-lg p-4 max-w-3xl">
                    <div class="text-gray-800 leading-relaxed">${formattedContent}</div>
                </div>
            `;
        }
        
        chatMessages.appendChild(messageDiv);
        chatMessages.scrollTop = chatMessages.scrollHeight;
    }

    // Function to show loading state
    function showLoading() {
        const loadingDiv = document.createElement('div');
        loadingDiv.id = 'loading-message';
        loadingDiv.className = 'flex items-start space-x-3';
        loadingDiv.innerHTML = `
            <div class="w-8 h-8 bg-primary rounded-full flex items-center justify-center flex-shrink-0">
                <span class="text-white text-sm">🤖</span>
            </div>
            <div class="bg-gray-100 rounded-lg p-4 max-w-3xl">
                <div class="flex items-center space-x-2">
                    <div class="animate-spin rounded-full h-4 w-4 border-b-2 border-primary"></div>
                    <span class="text-gray-600">Thinking...</span>
                </div>
            </div>
        `;
        chatMessages.appendChild(loadingDiv);
        chatMessages.scrollTop = chatMessages.scrollHeight;
    }

    // Function to remove loading state
    function removeLoading() {
        const loadingMessage = document.getElementById('loading-message');
        if (loadingMessage) {
            loadingMessage.remove();
        }
    }

    // Function to call backend API
    async function callBackendAPI(userQuestion) {
        try {
            const response = await fetch('http://localhost:8089/api/chat/send', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    message: userQuestion
                })
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const data = await response.json();
            return data.response;
        } catch (error) {
            console.error('Error calling backend API:', error);
            return 'I apologize, but I\'m having trouble connecting to my knowledge base right now. Please try again in a moment, or feel free to ask your home loan question again.';
        }
    }

    // Function to handle chat submission
    async function handleChatSubmission(question) {
        if (!question.trim()) return;

        // Add user message
        addMessage(question, true);

        // Check if question is home loan related
        if (!isHomeLoanRelated(question)) {
            addMessage('I\'m sorry, but I can only answer questions related to home loans. Please ask me about home loan eligibility, interest rates, documentation, application process, EMI calculations, tax benefits, or any other home loan related topics. How can I help you with your home loan questions?');
            return;
        }

        // Show loading
        showLoading();

        try {
            // Call backend API
            const response = await callBackendAPI(question);
            
            // Remove loading
            removeLoading();
            
            // Add AI response
            addMessage(response);
        } catch (error) {
            // Remove loading
            removeLoading();
            
            // Add error message
            addMessage('I apologize, but I\'m experiencing technical difficulties right now. Please try again in a moment, or feel free to ask your home loan question again.');
        }
    }

    // Event listener for chat form submission
    chatForm.addEventListener('submit', async function(e) {
        e.preventDefault();
        
        const question = chatInput.value.trim();
        if (!question) return;

        // Disable input and button during processing
        chatInput.disabled = true;
        sendButton.disabled = true;
        
        // Clear input
        chatInput.value = '';
        
        // Handle submission
        await handleChatSubmission(question);
        
        // Re-enable input and button
        chatInput.disabled = false;
        sendButton.disabled = false;
        chatInput.focus();
    });

    // Event listeners for quick questions
    quickQuestions.forEach(button => {
        button.addEventListener('click', function() {
            const question = this.querySelector('h4').textContent.replace(/^[📋💰✅📊🏠💳]\s*/, '');
            chatInput.value = question;
            chatForm.dispatchEvent(new Event('submit'));
        });
    });

    // Auto-resize textarea (if needed in future)
    chatInput.addEventListener('input', function() {
        this.style.height = 'auto';
        this.style.height = this.scrollHeight + 'px';
    });

    // Keyboard shortcuts
    chatInput.addEventListener('keydown', function(e) {
        if (e.key === 'Enter' && !e.shiftKey) {
            e.preventDefault();
            chatForm.dispatchEvent(new Event('submit'));
        }
    });

    // Focus input on page load
    chatInput.focus();
}); 