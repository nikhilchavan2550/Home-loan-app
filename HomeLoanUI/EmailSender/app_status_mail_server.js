const express = require('express');
const bodyParser = require('body-parser');
const nodemailer = require('nodemailer');
const axios = require('axios');
require('dotenv').config();

const app = express();
const PORT = process.env.PORT || 3000;

app.use(bodyParser.json());

const transporter = nodemailer.createTransport({
    service: 'gmail',
    auth: {
        user: process.env.EMAIL_USER,
        pass: process.env.EMAIL_PASS
    }
});

app.post('/send-email', async (req, res) => {
    const { subject, message, applicationId } = req.body;

    if (!subject || !message || !applicationId) {
        return res.status(400).json({
            error: 'Missing required fields (subject, message, applicationId)'
        });
    }

    try {
        // 1. Get Loan Application by ID
        const loanResponse = await axios.get(`http://localhost:8080/api/loan/${applicationId}`);
        const loanApplication = loanResponse.data;

        if (!loanApplication || !loanApplication.custId) {
            return res.status(404).json({ error: 'Loan application or custId not found' });
        }

        const custId = loanApplication.custId;


        // 2. Get Customer details using custId
        const customerResponse = await axios.get(`http://localhost:8085/applicant/customer/${custId}`);
        const customer = customerResponse.data;

        if (!customer || !customer.email) {
            return res.status(404).json({ error: 'Customer not found or missing email' });
        }

        const customerEmail = customer.email;

        // 3. Prepare the email
        const mailOptions = {
            from: process.env.EMAIL_USER,
            to: customerEmail,
            subject: subject,
            text: `Dear ${customer.firstName} ${customer.lastName},\n\n` +
                  `${message}\n\n` +
                  `Application ID: ${applicationId}\nCustomer ID: ${custId}`
        };

        // 4. Send the email
        await transporter.sendMail(mailOptions);
        console.log(`Email sent to ${customerEmail} for application ID: ${applicationId}`);

        res.status(200).json({ success: true, message: 'Email sent' });

    } catch (error) {
        console.error('Error:', error.message || error);
        res.status(500).json({ success: false, error: 'Failed to fetch data or send email' });
    }
});

app.listen(PORT, () => {
    console.log(`Server running at http://localhost:${PORT}`);
});
