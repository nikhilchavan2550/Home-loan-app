 let currentDeleteId = null;
document.addEventListener('DOMContentLoaded', function () {
    loadPropertyDetails();
    checkAndDisableFormIfDataExists();
});

       function populateAndDisableForm(data) {
    const propertyName = document.getElementById("propertyName");
    const propertyLocation = document.getElementById("propertyLocation");
    const city = document.getElementById("city");
    const state = document.getElementById("state");
    const propertyArea = document.getElementById("propertyArea");
    const estimatedAmount = document.getElementById("estimatedAmount");
    const constructionCompletionDate = document.getElementById("constructionCompletionDate");

    propertyName.value = data.propertyName || '';
    propertyLocation.value = data.propertyLocation || '';
    city.value = data.city || '';
    state.value = data.state || '';
    propertyArea.value = data.propertyArea || '';
    estimatedAmount.value = data.estimatedAmount || '';
    constructionCompletionDate.value = data.constructionCompletionDate || '';

    // Disable all form inputs
    const inputs = document.querySelectorAll("#propertyForm input");
    inputs.forEach(input => input.disabled = true);
}


async function checkAndDisableFormIfDataExists() {
    const customerData = JSON.parse(localStorage.getItem("customerData"));
    const response = await fetch(`http://localhost:8080/api/property/byPersonalId/${customerData.customerId}`);
    const propertyData = await response.json();

    localStorage.setItem("propertyid", propertyData.id);

    console.log("Property ID is "+propertyData.id);

    if (propertyData.id >= 0) {
        populateAndDisableForm(propertyData);
    }


}



        document.getElementById("propertyForm").addEventListener("submit", async function(e) {
            e.preventDefault();
            const responseDiv = document.getElementById("responseMessage");
            const submitBtn = this.querySelector('button[type="submit"]');
            const submitText = document.getElementById("submitText");
            const submitSpinner = document.getElementById("submitSpinner");
            
            
            submitBtn.disabled = true;
            submitText.textContent = "Submitting...";
            submitSpinner.style.display = "inline-block";
            responseDiv.style.display = "none";
            
            const customerData = JSON.parse(localStorage.getItem("customerData"));

            const formData = {
                personalId: customerData.customerId,
                propertyName: document.getElementById("propertyName").value.trim(),
                propertyLocation: document.getElementById("propertyLocation").value.trim(),
                city: document.getElementById("city").value.trim(),
                state: document.getElementById("state").value.trim(),
                propertyArea: document.getElementById("propertyArea").value,
                estimatedAmount: parseFloat(document.getElementById("estimatedAmount").value),
                constructionCompletionDate: document.getElementById("constructionCompletionDate").value,
                createdDate: null
            };

            try {
                const res = await fetch("http://localhost:8080/api/property/add", {
                    method: "POST",
                    headers: { 
                        "Content-Type": "application/json",
                        "Accept": "application/json"
                    },
                    body: JSON.stringify(formData)
                });

                let responseData;
                try {
                    responseData = await res.json();
                } catch (e) {
                    throw new Error(`Invalid JSON response: ${await res.text()}`);
                }
                
                if (!res.ok) {
                    let errorMessage = 'Failed to submit form';
                    try {
                        const errorData = await res.json();
                        errorMessage = errorData.message || errorData.error || JSON.stringify(errorData);
                    } catch (e) {
                        errorMessage = await res.text() || 'Unknown error occurred';
                    }
                    throw new Error(errorMessage);
                }

                responseDiv.className = "alert alert-success";
                responseDiv.innerHTML = `
                    <i class="bi bi-check-circle me-2"></i>
                    Property details submitted successfully! Your application has been saved.
                `;
                responseDiv.style.display = "block";
                
                // Reset form on success
                // this.reset();
                loadPropertyDetails();
                checkAndDisableFormIfDataExists();
                
            } catch (err) {
                console.error('Submission error:', err);
                responseDiv.className = "alert alert-danger";
                responseDiv.innerHTML = `
                    <i class="bi bi-exclamation-triangle me-2"></i>
                    Error: ${err.message}
                `;
                responseDiv.style.display = "block";
            } finally {
                resetSubmitButton();
            }
        });

        // Load Property Details Records
        async function loadPropertyDetails() {
            const tableBody = document.getElementById("propertyDetailsTableBody");
            const messageDiv = document.getElementById("recordsMessage");
            
            try {
               const customerData = JSON.parse(localStorage.getItem("customerData"));
               const response = await fetch(`http://localhost:8080/api/property/byPersonalId/${customerData.customerId}`);
               const data = await response.json();
                
                if (data.length === 0) {
                    tableBody.innerHTML = `
                        <tr>
                            <td colspan="11" class="text-center text-muted py-4">
                                <i class="bi bi-inbox fs-1"></i>
                                <p class="mt-2">No property details records found.</p>
                            </td>
                        </tr>
                    `;
                    return;
                }
                    // <td>${data.id}</td>
                    //     <td>${data.personalId || 'N/A'}</td>
                tableBody.innerHTML =`
                    <tr>
                    
                        <td>${data.propertyName || 'N/A'}</td>
                        <td>${data.propertyLocation || 'N/A'}</td>
                        <td>${data.city || 'N/A'}</td>
                        <td>${data.state || 'N/A'}</td>
                        <td>${data.propertyArea ? data.propertyArea.toLocaleString() : 'N/A'}</td>
                        <td>₹${data.estimatedAmount ? data.estimatedAmount.toLocaleString() : 'N/A'}</td>
                        <td>${data.constructionCompletionDate || 'N/A'}</td>
                        <td>${data.createdDate || 'N/A'}</td>
                        <td>
                            <div class="action-buttons">
                                <button class="btn btn-warning btn-sm" onclick="editPropertyDetails(${data.id})">
                                    <i class="bi bi-pencil"></i>
                                </button>
                                <button class="btn btn-danger btn-sm" onclick="deletePropertyDetails(${data.id})">
                                    <i class="bi bi-trash"></i>
                                </button>
                            </div>
                        </td>
                    </tr>
                `;
                
            } catch (error) {
                console.error('Error loading property details:', error);
                messageDiv.className = "alert alert-danger";
                messageDiv.innerHTML = `
                    <i class="bi bi-exclamation-triangle me-2"></i>
                    Error loading records: ${error.message}
                `;
                messageDiv.style.display = "block";
            }
        }

        // Edit Property Details
        async function editPropertyDetails(id) {
            try {
                const response = await fetch(`http://localhost:8080/api/property/${id}`);
                const data = await response.json();
                
                // Populate edit form
                document.getElementById("editId").value = data.id;
                document.getElementById("editPersonalId").value = data.personalId || '';
                document.getElementById("editPropertyName").value = data.propertyName || '';
                document.getElementById("editPropertyLocation").value = data.propertyLocation || '';
                document.getElementById("editCity").value = data.city || '';
                document.getElementById("editState").value = data.state || '';
                document.getElementById("editPropertyArea").value = data.propertyArea || '';
                document.getElementById("editEstimatedAmount").value = data.estimatedAmount || '';
                document.getElementById("editConstructionCompletionDate").value = data.constructionCompletionDate || '';
                document.getElementById("editCreatedDate").value = data.createdDate || '';
                
                // Show modal
                const modal = new bootstrap.Modal(document.getElementById('editModal'));
                modal.show();
                
            } catch (error) {
                console.error('Error loading property details for edit:', error);
                alert('Error loading property details: ' + error.message);
            }
        }

        // Update Property Details
        async function updatePropertyDetails() {
            const id = document.getElementById("editId").value;
            const formData = {
                personalId: parseInt(document.getElementById("editPersonalId").value),
                propertyName: document.getElementById("editPropertyName").value.trim(),
                propertyLocation: document.getElementById("editPropertyLocation").value.trim(),
                city: document.getElementById("editCity").value.trim(),
                state: document.getElementById("editState").value.trim(),
                propertyArea: document.getElementById("editPropertyArea").value,
                estimatedAmount: parseFloat(document.getElementById("editEstimatedAmount").value),
                constructionCompletionDate: document.getElementById("editConstructionCompletionDate").value,
                createdDate: document.getElementById("editCreatedDate").value
            };

            try {
                const response = await fetch(`http://localhost:8080/api/property/update/${id}`, {
                    method: "PUT",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(formData)
                });

                if (response.ok) {
                    // Close modal
                    const modal = bootstrap.Modal.getInstance(document.getElementById('editModal'));
                    modal.hide();
                    
                    // Reload data
                    loadPropertyDetails();
                    
                    // Show success message
                    const messageDiv = document.getElementById("recordsMessage");
                    messageDiv.className = "alert alert-success";
                    messageDiv.innerHTML = `
                        <i class="bi bi-check-circle me-2"></i>
                        Property details updated successfully!
                    `;
                    messageDiv.style.display = "block";
                    
                    // Hide message after 3 seconds
                    setTimeout(() => {
                        messageDiv.style.display = "none";
                    }, 3000);
                } else {
                    throw new Error('Failed to update property details');
                }
            } catch (error) {
                console.error('Error updating property details:', error);
                alert('Error updating property details: ' + error.message);
            }
        }

        // Delete Property Details
        function deletePropertyDetails(id) {
            currentDeleteId = id;
            const modal = new bootstrap.Modal(document.getElementById('deleteModal'));
            modal.show();
        }

        // Confirm Delete
        async function confirmDelete() {
            if (!currentDeleteId) return;
            
            try {
                const response = await fetch(`http://localhost:8080/api/property/delete/${currentDeleteId}`, {
                    method: "DELETE"
                });

                if (response.ok) {
                    // Close modal
                    const modal = bootstrap.Modal.getInstance(document.getElementById('deleteModal'));
                    modal.hide();
                    
                    // Reload data
                    loadPropertyDetails();
                    
                    // Show success message
                    const messageDiv = document.getElementById("recordsMessage");
                    messageDiv.className = "alert alert-success";
                    messageDiv.innerHTML = `
                        <i class="bi bi-check-circle me-2"></i>
                        Property details deleted successfully!
                    `;
                    messageDiv.style.display = "block";
                    
                    // Hide message after 3 seconds
                    setTimeout(() => {
                        messageDiv.style.display = "none";
                    }, 3000);
                } else {
                    throw new Error('Failed to delete property details');
                }
            } catch (error) {
                console.error('Error deleting property details:', error);
                alert('Error deleting property details: ' + error.message);
            }
        }

        function resetSubmitButton() {
            const submitBtn = document.querySelector('button[type="submit"]');
            const submitText = document.getElementById("submitText");
            const submitSpinner = document.getElementById("submitSpinner");
            
            submitBtn.disabled = false;
            submitText.textContent = "Submit Property Details";
            submitSpinner.style.display = "none";
        }