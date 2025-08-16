# Home Loan Application

A comprehensive home loan management system with a microservices architecture featuring user authentication, loan application processing, EMI calculation, and admin dashboards.

## Live Demo
[![Live Demo Video](https://img.youtube.com/vi/LyEy0jTBzfs/0.jpg)](https://youtu.be/LyEy0jTBzfs)

## Features
- User registration & login with Google OAuth
- Loan application submission with document upload
- EMI calculator & schedule generation
- Real-time application status updates
- Admin panel for application review and management
- Bank integration for loan processing
- Email notifications for application status changes
- Chat assistant for customer support

## Tech Stack
- **Backend**: Spring Boot (Microservices architecture)
  - Admin Service
  - Applicant Module
  - Chat Assistant
  - Eureka Server (Service Discovery)
  - Home Loan Document Service
  - Home Loan Status Service
  - Loan App Service
  - Orlo Bank Service
- **Frontend**: HTML, CSS, JavaScript
- **Authentication**: Google OAuth
- **Email Service**: Node.js with Nodemailer
- **Build Tools**: Maven, npm
- **Version Control**: Git

## Project Structure
```
HomeLoan-main/
├── .vscode/
├── HomeLoanSpring/              # Backend microservices
│   ├── Admin/
│   ├── ApplicantModule/
│   ├── ChatAssistant/
│   ├── EurekaServer/
│   ├── HomeLoanDocument/
│   ├── HomeLoanStatus/
│   ├── LoanApp/
│   ├── OrloBank/
│   └── homeloan/
└── HomeLoanUI/                  # Frontend
    ├── EmailSender/            # Email notification service
    ├── HomeLoan/               # Main application UI
    └── OrloBank/               # Bank integration UI
```

## Installation and Setup
### Prerequisites
- Java 11+
- Node.js
- Maven
- Git

### Steps
1. Clone the repository
   ```bash
   git clone https://github.com/nikhilchavan2550/Home-loan-app.git
   cd Home-loan-app
   ```

2. Set up the backend services
   ```bash
   # Navigate to each microservice directory and build
   cd HomeLoanSpring/Admin
   mvn clean install
   
   # Repeat for other microservices
   ```

3. Set up the frontend
   ```bash
   cd HomeLoanUI/HomeLoan
   # No build step required for basic HTML/CSS/JS
   ```

4. Configure Google OAuth
   - Update `google-auth.js` with your client ID and secret
   ```javascript
   this.clientId = 'your-client-id';
   this.clientSecret = 'your-client-secret';
   ```

5. Start the services
   - Start Eureka Server first
   - Then start other microservices
   - Open the frontend in a browser

## Usage
1. Register or login using your Google account
2. Fill out the loan application form
3. Upload required documents
4. Calculate EMI using the EMI calculator
5. Submit the application
6. Track application status in the dashboard
7. Admin users can review and process applications in the admin panel

## Contributing
Contributions are welcome! Please feel free to submit a Pull Request.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

© 2025 Home Loan Application. All rights reserved.