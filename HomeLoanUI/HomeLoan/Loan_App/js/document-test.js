// Document Module Test Script
// Run this in browser console to test document functionality

async function testDocumentModule() {
    console.log('🧪 Testing Document Module...');
    
    try {
        // Test 1: Get document types
        console.log('\n📋 Test 1: Getting document types...');
        const typesResponse = await fetch('http://localhost:8080/api/document/types');
        const types = await typesResponse.json();
        console.log('✅ Available document types:', types);
        
        // Test 2: Upload a document
        console.log('\n📤 Test 2: Uploading a document...');
        const uploadData = {
            personalId: 1,
            documentType: "Pan Card",
            documentName: "Test Pan Card",
            documentUrl: "https://example.com/pan-card.pdf",
            uploadedDate: "2024-01-15"
        };
        
        const uploadResponse = await fetch('http://localhost:8080/api/document/upload', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(uploadData)
        });
        
        if (uploadResponse.ok) {
            const uploadedDoc = await uploadResponse.json();
            console.log('✅ Document uploaded successfully:', uploadedDoc);
            
            // Test 3: Get all documents
            console.log('\n📋 Test 3: Getting all documents...');
            const allResponse = await fetch('http://localhost:8080/api/document/all');
            const allDocs = await allResponse.json();
            console.log('✅ All documents:', allDocs);
            
            // Test 4: Update the document
            if (uploadedDoc.id) {
                console.log('\n✏️ Test 4: Updating document...');
                const updateData = {
                    personalId: 1,
                    documentType: "Aadhaar Card",
                    documentName: "Updated Aadhaar Card",
                    documentUrl: "https://example.com/aadhaar-card.pdf",
                    uploadedDate: "2024-01-16"
                };
                
                const updateResponse = await fetch(`http://localhost:8080/api/document/update/${uploadedDoc.id}`, {
                    method: 'PUT',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(updateData)
                });
                
                if (updateResponse.ok) {
                    const updatedDoc = await updateResponse.json();
                    console.log('✅ Document updated successfully:', updatedDoc);
                } else {
                    const errorText = await updateResponse.text();
                    console.error('❌ Update failed:', errorText);
                }
            }
            
        } else {
            const errorText = await uploadResponse.text();
            console.error('❌ Upload failed:', errorText);
        }
        
    } catch (error) {
        console.error('❌ Test failed:', error);
    }
}

// Test with invalid document type
async function testInvalidDocumentType() {
    console.log('\n🚫 Testing invalid document type...');
    
    const invalidData = {
        personalId: 1,
        documentType: "INVALID_TYPE",
        documentName: "Test Document",
        documentUrl: "https://example.com/test.pdf",
        uploadedDate: "2024-01-15"
    };
    
    try {
        const response = await fetch('http://localhost:8080/api/document/upload', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(invalidData)
        });
        
        if (!response.ok) {
            console.log('✅ Invalid document type correctly rejected');
        } else {
            console.log('❌ Invalid document type was accepted (should be rejected)');
        }
    } catch (error) {
        console.error('❌ Test error:', error);
    }
}

// Test with existing document types from database
async function testExistingDocumentTypes() {
    console.log('\n📋 Testing existing document types from database...');
    
    const existingTypes = ["Pan Card", "LOA", "Photo"];
    
    for (const docType of existingTypes) {
        console.log(`\n🧪 Testing document type: ${docType}`);
        
        const testData = {
            personalId: 1,
            documentType: docType,
            documentName: `Test ${docType}`,
            documentUrl: `https://example.com/${docType.toLowerCase().replace(' ', '-')}.pdf`,
            uploadedDate: "2024-01-15"
        };
        
        try {
            const response = await fetch('http://localhost:8080/api/document/upload', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(testData)
            });
            
            if (response.ok) {
                console.log(`✅ ${docType} uploaded successfully`);
            } else {
                const errorText = await response.text();
                console.error(`❌ ${docType} failed:`, errorText);
            }
        } catch (error) {
            console.error(`❌ ${docType} error:`, error);
        }
    }
}

// Run tests
console.log('🚀 Document Module Test Suite');
console.log('Run testDocumentModule() to test all functionality');
console.log('Run testInvalidDocumentType() to test validation');
console.log('Run testExistingDocumentTypes() to test database-compatible types'); 