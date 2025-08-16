class GoogleAuth {
    constructor() {
        this.clientId = '';
        this.clientSecret = '';
        this.redirectUri = window.location.origin + '/auth/google/callback';
        this.scope = 'email profile';
        this.isInitialized = false;
    }

    // Initialize Google Auth
    async initialize() {
        if (this.isInitialized) return;

        try {
            // Load Google Identity Services
            await this.loadGoogleIdentityServices();
            
            // Initialize Google Sign-In
            google.accounts.id.initialize({
                client_id: this.clientId,
                callback: this.handleCredentialResponse.bind(this),
                auto_select: false,
                cancel_on_tap_outside: true
            });

            // Render sign-in buttons
            this.renderSignInButtons();
            
            this.isInitialized = true;
            console.log('Google Auth initialized successfully');
        } catch (error) {
            console.error('Failed to initialize Google Auth:', error);
        }
    }

    // Load Google Identity Services script
    loadGoogleIdentityServices() {
        return new Promise((resolve, reject) => {
            if (window.google && window.google.accounts) {
                resolve();
                return;
            }

            const script = document.createElement('script');
            script.src = 'https://accounts.google.com/gsi/client';
            script.async = true;
            script.defer = true;
            
            script.onload = () => resolve();
            script.onerror = () => reject(new Error('Failed to load Google Identity Services'));
            
            document.head.appendChild(script);
        });
    }

    // Render Google Sign-In buttons
    renderSignInButtons() {
        // Render on login page
        const loginGoogleBtn = document.getElementById('google-signin-login');
        if (loginGoogleBtn) {
            google.accounts.id.renderButton(loginGoogleBtn, {
                theme: 'outline',
                size: 'large',
                text: 'signin_with',
                shape: 'rectangular',
                width: 280
            });
        }

        // Render on register page
        const registerGoogleBtn = document.getElementById('google-signin-register');
        if (registerGoogleBtn) {
            google.accounts.id.renderButton(registerGoogleBtn, {
                theme: 'outline',
                size: 'large',
                text: 'signup_with',
                shape: 'rectangular',
                width: 280
            });
        }
    }

    // Handle credential response from Google
    // Handle credential response from Google
async handleCredentialResponse(response) {
    try {
        console.log('Google Auth response received');

        // Decode the JWT token
        const payload = this.decodeJwtToken(response.credential);
        const email = payload.email;

        // Step 1: Check if user exists in backend
        const customerRes = await fetch(`http://localhost:8085/auth/by-email?email=${encodeURIComponent(email)}`);
        
        if (customerRes.ok) {
            const customer = await customerRes.json();

            // ✅ User exists - store info & redirect
            localStorage.setItem("customerData", JSON.stringify(customer));
            // sessionStorage.setItem('user', JSON.stringify(customer));
            // sessionStorage.setItem('isAuthenticated', 'true');
            this.showMessage(`Welcome back, ${customer.firstName || customer.name || 'User'}!`, 'success');

            setTimeout(() => window.location.href = './Loan_App/index.html', 1500);
        } else if (customerRes.status === 404) {
            this.showMessage('No user found. Please register first.', 'error');
        } else {
            throw new Error(`Unexpected error: ${customerRes.status}`);
        }

    } catch (error) {
        console.error('Error during Google login:', error);
        this.showMessage('Authentication failed. Try again.', 'error');
    }
}


    // Decode JWT token
    decodeJwtToken(token) {
        try {
            const base64Url = token.split('.')[1];
            const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
            const jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
                return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
            }).join(''));
            return JSON.parse(jsonPayload);
        } catch (error) {
            console.error('Error decoding JWT token:', error);
            throw error;
        }
    }

    // Authenticate with backend
    async authenticateWithBackend(credential, payload) {
        try {
            const response = await fetch('http://localhost:8085/api/auth/google', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    credential: credential,
                    email: payload.email,
                    name: payload.name,
                    picture: payload.picture,
                    sub: payload.sub
                })
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const data = await response.json();
            return data;
        } catch (error) {
            console.error('Error authenticating with backend:', error);
            throw error;
        }
    }

    // Show message to user
    showMessage(message, type = 'info') {
        // Create message element
        const messageDiv = document.createElement('div');
        messageDiv.className = `fixed top-4 right-4 p-4 rounded-lg shadow-lg z-50 max-w-sm ${
            type === 'success' ? 'bg-green-500 text-white' :
            type === 'error' ? 'bg-red-500 text-white' :
            'bg-blue-500 text-white'
        }`;
        messageDiv.textContent = message;

        // Add to page
        document.body.appendChild(messageDiv);

        // Remove after 3 seconds
        setTimeout(() => {
            if (messageDiv.parentNode) {
                messageDiv.parentNode.removeChild(messageDiv);
            }
        }, 3000);
    }

    // Sign out
    signOut() {
        try {
            // Clear session storage
            sessionStorage.removeItem('user');
            sessionStorage.removeItem('isAuthenticated');
            
            // Sign out from Google
            if (window.google && window.google.accounts) {
                google.accounts.id.disableAutoSelect();
            }
            
            // Redirect to login page
            window.location.href = 'login.html';
        } catch (error) {
            console.error('Error signing out:', error);
        }
    }

    // Check if user is authenticated
    isAuthenticated() {
        return sessionStorage.getItem('isAuthenticated') === 'true';
    }

    // Get current user
    getCurrentUser() {
        const userStr = sessionStorage.getItem('user');
        return userStr ? JSON.parse(userStr) : null;
    }
}

// Initialize Google Auth when DOM is loaded
document.addEventListener('DOMContentLoaded', function() {
    window.googleAuth = new GoogleAuth();
    window.googleAuth.initialize();
});

// Export for use in other scripts
window.GoogleAuth = GoogleAuth; 