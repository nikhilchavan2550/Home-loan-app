        let currentDeleteId = null;
const API_BASE = "http://localhost:8086/api/documents";

        // Upload New Form Submission
        document.getElementById("documentForm").addEventListener("submit", async function(e) {
            e.preventDefault();
            const responseAlert = document.getElementById("responseAlert");
            const submitBtn = this.querySelector('button[type="submit"]');
            const submitText = document.getElementById("submitText");
            const submitSpinner = document.getElementById("submitSpinner");
            
            // Show loading state
            submitBtn.disabled = true;
            submitText.textContent = "Uploading...";
            submitSpinner.style.display = "inline-block";
            responseAlert.style.display = "none";

            // Validate required fields
            const personalId = document.getElementById("personalId").value.trim();
            const documentType = document.getElementById("documentType").value;
            const documentName = document.getElementById("documentName").value.trim();
            const documentUrl = document.getElementById("documentUrl").value.trim();
            const uploadedDate = document.getElementById("uploadedDate").value;
            
            if (!personalId || !documentType || !documentName || !documentUrl || !uploadedDate) {
                showError('Please fill in all required fields marked with *');
                resetSubmitButton();
                return;
            }

            const formData = {
                personalId: parseInt(personalId),
                documentType: documentType,
                documentName: documentName,
                documentUrl: documentUrl,
                uploadedDate: uploadedDate
            };

            try {
                const response = await fetch("http://localhost:8080/api/document/upload", {
                    method: "POST",
                    headers: { 
                        "Content-Type": "application/json",
                        "Accept": "application/json"
                    },
                    body: JSON.stringify(formData)
                });

                let responseData;
                let responseText = await response.text();
                
                try {
                    responseData = JSON.parse(responseText);
                } catch (e) {
                    throw new Error(`Invalid JSON response: ${responseText}`);
                }
                
                if (!response.ok) {
                    let errorMessage = 'Failed to upload document';
                    if (responseData && responseData.message) {
                        errorMessage = responseData.message;
                    } else if (responseData && responseData.error) {
                        errorMessage = responseData.error;
                    } else {
                        errorMessage = responseText || 'Unknown error occurred';
                    }
                    throw new Error(errorMessage);
                }

                if (responseData && responseData.message) {
                    let errorMsg = responseData.message;
                    if (responseData.errors) {
                        errorMsg = Object.values(responseData.errors).flat().join(' ');
                    }
                    throw new Error(errorMsg);
                }

                responseAlert.className = "alert alert-success";
                responseAlert.innerHTML = `
                    <i class="bi bi-check-circle me-2"></i>
                    Document uploaded successfully! Your document has been saved.
                `;
                responseAlert.style.display = "block";
                
                // Reset form on success
                this.reset();
                updateProgress(0);
                
            } catch (err) {
                console.error('Upload error:', err);
                responseAlert.className = "alert alert-danger";
                responseAlert.innerHTML = `
                    <i class="bi bi-exclamation-triangle me-2"></i>
                    Error: ${err.message}
                `;
                responseAlert.style.display = "block";
            } finally {
                resetSubmitButton();
            }
        });

        // Load Documents Records
        async function loadDocuments() {
            const tableBody = document.getElementById("documentsTableBody");
            const messageDiv = document.getElementById("recordsMessage");
            
            try {
                const response = await fetch("http://localhost:8080/api/document/all");
                
                if (!response.ok) {
                    throw new Error(`Failed to load documents: ${response.status} ${response.statusText}`);
                }
                
                const data = await response.json();
                
                if (data.length === 0) {
                    tableBody.innerHTML = `
                        <tr>
                            <td colspan="7" class="text-center text-muted py-4">
                                <i class="bi bi-inbox fs-1"></i>
                                <p class="mt-2">No document records found.</p>
                            </td>
                        </tr>
                    `;
                    return;
                }
                
                tableBody.innerHTML = data.map(record => `
                    <tr>
                        <td>${record.id}</td>
                        <td>${record.personalId || 'N/A'}</td>
                        <td>${record.documentType || 'N/A'}</td>
                        <td>${record.documentName || 'N/A'}</td>
                        <td>
                            <a href="${record.documentUrl || '#'}" target="_blank" class="text-decoration-none">
                                ${record.documentUrl ? 'View Document' : 'N/A'}
                            </a>
                        </td>
                        <td>${record.uploadedDate || 'N/A'}</td>
                        <td>
                            <div class="action-buttons">
                                <button class="btn btn-warning btn-sm" onclick="editDocument(${record.id})">
                                    <i class="bi bi-pencil"></i>
                                </button>
                                <button class="btn btn-danger btn-sm" onclick="deleteDocument(${record.id})">
                                    <i class="bi bi-trash"></i>
                                </button>
                            </div>
                        </td>
                    </tr>
                `).join('');
                
            } catch (error) {
                console.error('Error loading documents:', error);
                messageDiv.className = "alert alert-danger";
                messageDiv.innerHTML = `
                    <i class="bi bi-exclamation-triangle me-2"></i>
                    Error loading records: ${error.message}
                `;
                messageDiv.style.display = "block";
            }
        }

        // Edit Document
        async function editDocument(id) {
            try {
                const response = await fetch(`http://localhost:8080/api/document/${id}`);
                
                if (!response.ok) {
                    throw new Error(`Failed to load document: ${response.status} ${response.statusText}`);
                }
                
                const data = await response.json();
                
                // Populate edit form
                document.getElementById("editId").value = data.id;
                document.getElementById("editPersonalId").value = data.personalId || '';
                
                // Convert display name back to enum name for the select dropdown
                const documentTypeEnum = getDocumentTypeEnumFromDisplayName(data.documentType);
                document.getElementById("editDocumentType").value = documentTypeEnum || '';
                
                document.getElementById("editDocumentName").value = data.documentName || '';
                document.getElementById("editDocumentUrl").value = data.documentUrl || '';
                document.getElementById("editUploadedDate").value = data.uploadedDate || '';
                
                // Show modal
                const modal = new bootstrap.Modal(document.getElementById('editModal'));
                modal.show();
                
            } catch (error) {
                console.error('Error loading document for edit:', error);
                alert('Error loading document: ' + error.message);
            }
        }

        // Update Document
        async function updateDocument() {
            const id = document.getElementById("editId").value;
            const formData = {
                personalId: parseInt(document.getElementById("editPersonalId").value),
                documentType: document.getElementById("editDocumentType").value,
                documentName: document.getElementById("editDocumentName").value.trim(),
                documentUrl: document.getElementById("editDocumentUrl").value.trim(),
                uploadedDate: document.getElementById("editUploadedDate").value
            };

            try {
                const response = await fetch(`http://localhost:8080/api/document/update/${id}`, {
                    method: "PUT",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(formData)
                });

                let responseText = await response.text();
                let responseData;
                
                try {
                    responseData = JSON.parse(responseText);
                } catch (e) {
                    // If not JSON, treat as plain text
                    responseData = null;
                }

                if (response.ok) {
                    // Close modal
                    const modal = bootstrap.Modal.getInstance(document.getElementById('editModal'));
                    modal.hide();
                    
                    // Reload data
                    loadDocuments();
                    
                    // Show success message
                    const messageDiv = document.getElementById("recordsMessage");
                    messageDiv.className = "alert alert-success";
                    messageDiv.innerHTML = `
                        <i class="bi bi-check-circle me-2"></i>
                        Document updated successfully!
                    `;
                    messageDiv.style.display = "block";
                    
                    // Hide message after 3 seconds
                    setTimeout(() => {
                        messageDiv.style.display = "none";
                    }, 3000);
                } else {
                    let errorMessage = 'Failed to update document';
                    if (responseData && responseData.message) {
                        errorMessage = responseData.message;
                    } else if (responseData && responseData.error) {
                        errorMessage = responseData.error;
                    } else {
                        errorMessage = responseText || 'Unknown error occurred';
                    }
                    throw new Error(errorMessage);
                }
            } catch (error) {
                console.error('Error updating document:', error);
                alert('Error updating document: ' + error.message);
            }
        }

        // Delete Document
        function deleteDocument(id) {
            currentDeleteId = id;
            const modal = new bootstrap.Modal(document.getElementById('deleteModal'));
            modal.show();
        }

        // Confirm Delete
        async function confirmDelete() {
            if (!currentDeleteId) return;
            
            try {
                const response = await fetch(`http://localhost:8080/api/document/delete/${currentDeleteId}`, {
                    method: "DELETE"
                });

                if (response.ok) {
                    // Close modal
                    const modal = bootstrap.Modal.getInstance(document.getElementById('deleteModal'));
                    modal.hide();
                    
                    // Reload data
                    loadDocuments();
                    
                    // Show success message
                    const messageDiv = document.getElementById("recordsMessage");
                    messageDiv.className = "alert alert-success";
                    messageDiv.innerHTML = `
                        <i class="bi bi-check-circle me-2"></i>
                        Document deleted successfully!
                    `;
                    messageDiv.style.display = "block";
                    
                    // Hide message after 3 seconds
                    setTimeout(() => {
                        messageDiv.style.display = "none";
                    }, 3000);
                } else {
                    throw new Error('Failed to delete document');
                }
            } catch (error) {
                console.error('Error deleting document:', error);
                alert('Error deleting document: ' + error.message);
            }
        }

        function resetSubmitButton() {
            const submitBtn = document.querySelector('button[type="submit"]');
            const submitText = document.getElementById("submitText");
            const submitSpinner = document.getElementById("submitSpinner");
            
            submitBtn.disabled = false;
            submitText.textContent = "Upload Document";
            submitSpinner.style.display = "none";
        }

        function showError(message) {
            const responseAlert = document.getElementById("responseAlert");
            responseAlert.className = "alert alert-danger";
            responseAlert.innerHTML = `
                <i class="bi bi-exclamation-triangle me-2"></i>
                ${message}
            `;
            responseAlert.style.display = "block";
        }

        function updateProgress(percentage) {
            const progressFill = document.getElementById("progressFill");
            const progressText = document.getElementById("progressText");
            
            progressFill.style.width = `${percentage}%`;
            progressText.textContent = `${percentage}%`;
        }

        // File upload handling
        document.getElementById('fileInput').addEventListener('change', function(e) {
            const file = e.target.files[0];
            if (file) {
                // For demo purposes, we'll just show the file name
                // In a real application, you'd upload to a server and get a URL
                const fileName = file.name;
                document.getElementById('documentName').value = fileName;
                document.getElementById('documentUrl').value = `https://example.com/uploads/${fileName}`;
            }
        });

        // Drag and drop functionality
        const uploadArea = document.querySelector('.file-upload-area');
        
        uploadArea.addEventListener('dragover', function(e) {
            e.preventDefault();
            this.classList.add('dragover');
        });
        
        uploadArea.addEventListener('dragleave', function(e) {
            e.preventDefault();
            this.classList.remove('dragover');
        });
        
        uploadArea.addEventListener('drop', function(e) {
            e.preventDefault();
            this.classList.remove('dragover');
            
            const files = e.dataTransfer.files;
            if (files.length > 0) {
                const file = files[0];
                document.getElementById('fileInput').files = files;
                document.getElementById('documentName').value = file.name;
                document.getElementById('documentUrl').value = `https://example.com/uploads/${file.name}`;
            }
        });

        // Helper function to convert display name back to enum name
        function getDocumentTypeEnumFromDisplayName(displayName) {
            const mapping = {
                'Pan Card': 'PAN_CARD',
                'Voter id': 'VOTER_ID',
                'Salary Slip': 'SALARY_SLIP',
                'LOA': 'LOA',
                'NOC from Builder': 'NOC_FROM_BUILDER',
                'Agreement to Sale': 'AGREEMENT_TO_SALE',
                'Aadhar Card': 'AADHAR_CARD',
                'Passport': 'PASSPORT',
                'Driving License': 'DRIVING_LICENSE',
                'Bank Statement': 'BANK_STATEMENT',
                'Property Documents': 'PROPERTY_DOCUMENTS',
                'Other': 'OTHER',
                'Aadhaar Card': 'AADHAAR',
                'Income Proof': 'INCOME_PROOF',
                'Property Proof': 'PROPERTY_PROOF',
                'Photo': 'PHOTO'
            };
            return mapping[displayName] || '';
        }

        // Real-time form validation and progress tracking
        document.addEventListener('DOMContentLoaded', function() {
            const requiredFields = document.querySelectorAll('[required]');
            const allFields = document.querySelectorAll('input, select, textarea');
            
            function calculateProgress() {
                let filledFields = 0;
                let totalFields = allFields.length;
                
                allFields.forEach(field => {
                    if (field.value.trim() !== '') {
                        filledFields++;
                    }
                });
                
                const percentage = Math.round((filledFields / totalFields) * 100);
                updateProgress(percentage);
            }
            
            allFields.forEach(field => {
                field.addEventListener('input', calculateProgress);
                field.addEventListener('change', calculateProgress);
            });
            
            // Initial progress calculation
            calculateProgress();
        });