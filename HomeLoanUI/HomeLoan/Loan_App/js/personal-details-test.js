// Personal Details Test Script
// Run this in browser console to test personal details functionality

async function testPersonalDetailsForm() {
    console.log('🧪 Testing Personal Details Form...');
    
    try {
        // Test 1: Submit personal details
        console.log('\n📝 Test 1: Submitting personal details...');
        const formData = {
            firstName: "John",
            middleName: "Michael",
            lastName: "Doe",
            dob: "1990-01-15",
            gender: "MALE",
            aadharNo: "123456789012",
            panNo: "ABCDE1234F",
            phoneNumber: "9876543210",
            nationality: "Indian",
            email: "john.doe@example.com",
            address: "123 Main Street, City, State 12345"
        };
        
        const response = await fetch('http://localhost:8080/api/personal/add', {
            method: 'POST',
            headers: { 
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify(formData)
        });

        // Read response only once
        let responseText = await response.text();
        let responseData;
        
        try {
            responseData = JSON.parse(responseText);
        } catch (e) {
            console.error('❌ Invalid JSON response:', responseText);
            return;
        }
        
        if (response.ok) {
            console.log('✅ Personal details submitted successfully:', responseData);
        } else {
            console.error('❌ Submission failed:', responseData);
        }
        
    } catch (error) {
        console.error('❌ Test failed:', error);
    }
}

// Test with validation errors
async function testPersonalDetailsValidation() {
    console.log('\n🚫 Testing validation errors...');
    
    try {
        const invalidData = {
            firstName: "", // Empty - should fail validation
            lastName: "Doe",
            aadharNo: "123", // Invalid - should fail validation
            panNo: "INVALID", // Invalid - should fail validation
            phoneNumber: "123", // Invalid - should fail validation
            dob: "2025-01-15" // Future date - should fail validation
        };
        
        const response = await fetch('http://localhost:8080/api/personal/add', {
            method: 'POST',
            headers: { 
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify(invalidData)
        });

        // Read response only once
        let responseText = await response.text();
        let responseData;
        
        try {
            responseData = JSON.parse(responseText);
        } catch (e) {
            console.error('❌ Invalid JSON response:', responseText);
            return;
        }
        
        if (!response.ok) {
            console.log('✅ Validation correctly rejected invalid data:', responseData);
        } else {
            console.log('❌ Validation should have failed but didn\'t');
        }
        
    } catch (error) {
        console.error('❌ Test error:', error);
    }
}

// Test duplicate submission (should handle response stream correctly)
async function testDuplicateSubmission() {
    console.log('\n🔄 Testing duplicate submission handling...');
    
    try {
        const formData = {
            firstName: "Jane",
            lastName: "Smith",
            aadharNo: "987654321098",
            panNo: "FGHIJ5678K",
            phoneNumber: "8765432109",
            dob: "1985-06-20",
            gender: "FEMALE",
            nationality: "Indian",
            email: "jane.smith@example.com",
            address: "456 Oak Avenue, Town, State 54321"
        };
        
        // First submission
        console.log('📝 First submission...');
        const response1 = await fetch('http://localhost:8080/api/personal/add', {
            method: 'POST',
            headers: { 
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify(formData)
        });

        let responseText1 = await response1.text();
        let responseData1 = JSON.parse(responseText1);
        
        if (response1.ok) {
            console.log('✅ First submission successful:', responseData1);
        } else {
            console.log('❌ First submission failed:', responseData1);
        }
        
        // Second submission (duplicate)
        console.log('📝 Second submission (duplicate)...');
        const response2 = await fetch('http://localhost:8080/api/personal/add', {
            method: 'POST',
            headers: { 
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify(formData)
        });

        let responseText2 = await response2.text();
        let responseData2 = JSON.parse(responseText2);
        
        if (response2.ok) {
            console.log('✅ Second submission successful:', responseData2);
        } else {
            console.log('✅ Second submission correctly handled:', responseData2);
        }
        
    } catch (error) {
        console.error('❌ Test error:', error);
    }
}

// Test get all personal details
async function testGetAllPersonalDetails() {
    console.log('\n📋 Testing get all personal details...');
    
    try {
        const response = await fetch('http://localhost:8080/api/personal/all');
        
        if (response.ok) {
            const data = await response.json();
            console.log('✅ Retrieved personal details:', data);
        } else {
            console.error('❌ Failed to retrieve personal details');
        }
        
    } catch (error) {
        console.error('❌ Test error:', error);
    }
}

// Run tests
console.log('🚀 Personal Details Test Suite');
console.log('Run testPersonalDetailsForm() to test form submission');
console.log('Run testPersonalDetailsValidation() to test validation');
console.log('Run testDuplicateSubmission() to test duplicate handling');
console.log('Run testGetAllPersonalDetails() to test data retrieval'); 