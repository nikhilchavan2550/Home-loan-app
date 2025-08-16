// Validation Debug Script
// Run this in browser console to debug validation errors

async function debugValidationError() {
    console.log('🔍 Debugging Validation Error...');
    
    // Test with valid data first
    console.log('\n✅ Testing with valid data...');
    const validData = {
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
    
    await testValidation(validData, "Valid Data");
    
    // Test individual field validations
    console.log('\n🧪 Testing individual field validations...');
    
    // Test 1: Invalid Aadhar Number
    const invalidAadhar = { ...validData, aadharNo: "12345678901" }; // 11 digits
    await testValidation(invalidAadhar, "Invalid Aadhar (11 digits)");
    
    // Test 2: Invalid PAN Number
    const invalidPan = { ...validData, panNo: "ABC123DEF" }; // Wrong format
    await testValidation(invalidPan, "Invalid PAN Format");
    
    // Test 3: Invalid Phone Number
    const invalidPhone = { ...validData, phoneNumber: "1234567890" }; // Starts with 1
    await testValidation(invalidPhone, "Invalid Phone (starts with 1)");
    
    // Test 4: Future Date of Birth
    const futureDob = { ...validData, dob: "2025-01-15" };
    await testValidation(futureDob, "Future Date of Birth");
    
    // Test 5: Invalid Email
    const invalidEmail = { ...validData, email: "invalid-email" };
    await testValidation(invalidEmail, "Invalid Email");
    
    // Test 6: Empty Required Fields
    const emptyFields = { ...validData, firstName: "", lastName: "" };
    await testValidation(emptyFields, "Empty Required Fields");
    
    // Test 7: Invalid Gender
    const invalidGender = { ...validData, gender: "INVALID" };
    await testValidation(invalidGender, "Invalid Gender");
    
    // Test 8: Too Long Fields
    const longFields = { 
        ...validData, 
        firstName: "A".repeat(51), // 51 characters
        address: "A".repeat(501)   // 501 characters
    };
    await testValidation(longFields, "Too Long Fields");
}

async function testValidation(data, testName) {
    console.log(`\n📝 ${testName}:`);
    console.log('Data:', data);
    
    try {
        const response = await fetch('http://localhost:8080/api/personal/add', {
            method: 'POST',
            headers: { 
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify(data)
        });

        let responseText = await response.text();
        let responseData;
        
        try {
            responseData = JSON.parse(responseText);
        } catch (e) {
            console.error('❌ Invalid JSON response:', responseText);
            return;
        }
        
        if (response.ok) {
            console.log('✅ Success:', responseData);
        } else {
            console.log('❌ Validation Failed:');
            console.log('Status:', response.status);
            console.log('Response:', responseData);
            
            if (responseData.errors) {
                console.log('🔍 Field Errors:');
                Object.entries(responseData.errors).forEach(([field, error]) => {
                    console.log(`  - ${field}: ${error}`);
                });
            }
        }
        
    } catch (error) {
        console.error('❌ Request failed:', error);
    }
}

// Test current form data
async function testCurrentFormData() {
    console.log('🔍 Testing current form data...');
    
    // Get form data from the current page
    const formData = {
        firstName: document.getElementById("firstName")?.value || "",
        middleName: document.getElementById("middleName")?.value || "",
        lastName: document.getElementById("lastName")?.value || "",
        dob: document.getElementById("dob")?.value || "",
        gender: document.getElementById("gender")?.value || "",
        aadharNo: document.getElementById("aadharNo")?.value || "",
        panNo: document.getElementById("panNo")?.value || "",
        phoneNumber: document.getElementById("phoneNumber")?.value || "",
        nationality: document.getElementById("nationality")?.value || "",
        email: document.getElementById("email")?.value || "",
        address: document.getElementById("address")?.value || ""
    };
    
    console.log('📋 Current form data:', formData);
    await testValidation(formData, "Current Form Data");
}

// Test with minimal valid data
async function testMinimalValidData() {
    console.log('🧪 Testing minimal valid data...');
    
    const minimalData = {
        firstName: "John",
        lastName: "Doe",
        aadharNo: "123456789012",
        panNo: "ABCDE1234F",
        phoneNumber: "9876543210"
    };
    
    await testValidation(minimalData, "Minimal Valid Data");
}

// Test with your specific data
async function testYourData() {
    console.log('🧪 Testing your specific data...');
    
    // Replace this with your actual data
    const yourData = {
        firstName: "YourFirstName",
        lastName: "YourLastName",
        aadharNo: "123456789012",
        panNo: "ABCDE1234F",
        phoneNumber: "9876543210",
        dob: "1990-01-01",
        gender: "MALE",
        nationality: "Indian",
        email: "your.email@example.com",
        address: "Your Address"
    };
    
    console.log('📋 Your data:', yourData);
    await testValidation(yourData, "Your Data");
}

// Run tests
console.log('🚀 Validation Debug Suite');
console.log('Run debugValidationError() to test all validation scenarios');
console.log('Run testCurrentFormData() to test current form data');
console.log('Run testMinimalValidData() to test minimal valid data');
console.log('Run testYourData() to test your specific data');
console.log('');
console.log('💡 Tip: Check the console output for specific field errors'); 