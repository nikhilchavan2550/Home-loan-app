// Validation Test Script
// Run this in browser console to test the API endpoint

async function testValidation() {
    const testCases = [
        {
            name: "Valid Data",
            data: {
                firstName: "John",
                lastName: "Doe",
                aadharNo: "123456789012",
                panNo: "ABCDE1234F",
                phoneNumber: "9876543210",
                dob: "1990-01-01",
                gender: "MALE",
                nationality: "Indian",
                email: "john.doe@example.com",
                address: "123 Main St, City"
            }
        },
        {
            name: "Invalid Aadhar (11 digits)",
            data: {
                firstName: "John",
                lastName: "Doe",
                aadharNo: "12345678901", // 11 digits
                panNo: "ABCDE1234F",
                phoneNumber: "9876543210"
            }
        },
        {
            name: "Invalid PAN Format",
            data: {
                firstName: "John",
                lastName: "Doe",
                aadharNo: "123456789012",
                panNo: "ABC123DEF", // Wrong format
                phoneNumber: "9876543210"
            }
        },
        {
            name: "Invalid Phone Number",
            data: {
                firstName: "John",
                lastName: "Doe",
                aadharNo: "123456789012",
                panNo: "ABCDE1234F",
                phoneNumber: "1234567890" // Starts with 1
            }
        },
        {
            name: "Missing Required Fields",
            data: {
                firstName: "John",
                // Missing lastName, aadharNo, panNo, phoneNumber
            }
        }
    ];

    for (const testCase of testCases) {
        console.log(`\n=== Testing: ${testCase.name} ===`);
        console.log('Request Data:', JSON.stringify(testCase.data, null, 2));
        
        try {
            const response = await fetch("http://localhost:8080/api/personal/add", {
                method: "POST",
                headers: { 
                    "Content-Type": "application/json",
                    "Accept": "application/json"
                },
                body: JSON.stringify(testCase.data)
            });

            const responseData = await response.json();
            console.log('Response Status:', response.status);
            console.log('Response Data:', JSON.stringify(responseData, null, 2));
            
            if (response.ok) {
                console.log('✅ SUCCESS');
            } else {
                console.log('❌ FAILED');
            }
        } catch (error) {
            console.log('❌ ERROR:', error.message);
        }
    }
}

// Run the test
// testValidation(); 