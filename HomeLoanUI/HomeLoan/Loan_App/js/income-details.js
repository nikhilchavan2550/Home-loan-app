        let currentDeleteId = null;
        document.addEventListener('DOMContentLoaded', function () {
    loadIncomeDetails();
    checkAndDisableFormIfDataExists();
});

        // Add New Form Submission
        document.getElementById("incomeForm").addEventListener("submit", async function(e) {
            e.preventDefault();
            const responseDiv = document.getElementById("responseMessage");
            const submitBtn = this.querySelector('button[type="submit"]');
            const submitText = document.getElementById("submitText");
            const submitSpinner = document.getElementById("submitSpinner");
            
            // Show loading state
            submitBtn.disabled = true;
            submitText.textContent = "Submitting...";
            submitSpinner.style.display = "inline-block";
            responseDiv.style.display = "none";
            

            const customerData = JSON.parse(localStorage.getItem("customerData"));

            const formData = {
                id: customerData.customerId,
                personalId: customerData.customerId,
                employmentType: document.getElementById("employmentType").value,
                retirementAge: parseInt(document.getElementById("retirementAge").value),
                organizationType: document.getElementById("organizationType").value,
                employerName: document.getElementById("employerName").value.trim(),
                monthlyIncome: parseFloat(document.getElementById("monthlyIncome").value),
                createdDate: null
            };

            try {
                const res = await fetch("http://localhost:8080/api/income/add", {
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
                    console.error("Failed to parse JSON:", e);
                    throw new Error("Invalid JSON response received from server.");
                }

                if (!res.ok) {
                    let errorMessage = 'Failed to submit form';

                    // Use the already-parsed `responseData` for error message
                    if (responseData && typeof responseData === 'object') {
                        errorMessage = responseData.message || responseData.error || JSON.stringify(responseData);
                    }

                    throw new Error(errorMessage);
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
                    Income details submitted successfully! Your application has been saved.
                `;
                responseDiv.style.display = "block";
                 loadIncomeDetails();
                checkAndDisableFormIfDataExists();
                // this.reset();

                
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

        function populateAndDisableForm(data) {
    const employmentType = document.getElementById("employmentType");
    const retirementAge = document.getElementById("retirementAge");
    const organizationType = document.getElementById("organizationType");
    const employerName = document.getElementById("employerName");
    const monthlyIncome = document.getElementById("monthlyIncome");

    employmentType.value = data.employmentType || '';
    retirementAge.value = data.retirementAge || '';
    organizationType.value = data.organizationType || '';
    employerName.value = data.employerName || '';
    monthlyIncome.value = data.monthlyIncome || '';

    // Disable inputs
    employmentType.disabled = true;
    retirementAge.disabled = true;
    organizationType.disabled = true;
    employerName.disabled = true;
    monthlyIncome.disabled = true;
}

async function checkAndDisableFormIfDataExists() {
    const customerData = JSON.parse(localStorage.getItem("customerData"));
    const response = await fetch(`http://localhost:8080/api/income/byPersonalId/${customerData.customerId}`);
    const incomeData = await response.json();

    localStorage.setItem("incomeid", incomeData.id);

    console.log("Income ID is "+incomeData.id);


    if (incomeData.id >= 0) {
        populateAndDisableForm(incomeData);
    }


}


        // Load Income Details Records
        async function loadIncomeDetails() {
            const tableBody = document.getElementById("incomeDetailsTableBody");
            const messageDiv = document.getElementById("recordsMessage");
            
            try {
                const customerData = JSON.parse(localStorage.getItem("customerData"));
    const response = await fetch(`http://localhost:8080/api/income/byPersonalId/${customerData.customerId}`);
    const data = await response.json();
                
                if (data.length === 0) {
                    tableBody.innerHTML = `
                        <tr>
                            <td colspan="9" class="text-center text-muted py-4">
                                <i class="bi bi-inbox fs-1"></i>
                                <p class="mt-2">No income details records found.</p>
                            </td>
                        </tr>
                    `;
                    return;
                }
                 // <td>${data.id}</td>
                        // <td>${data.personalId || 'N/A'}</td>
                tableBody.innerHTML = `
                    <tr>
                       
                        <td>${data.employmentType || 'N/A'}</td>
                        <td>${data.organizationType || 'N/A'}</td>
                        <td>${data.employerName || 'N/A'}</td>
                        <td>₹${data.monthlyIncome ? data.monthlyIncome.toLocaleString() : 'N/A'}</td>
                        <td>${data.retirementAge || 'N/A'}</td>
                        <td>${data.createdDate || 'N/A'}</td>
                        <td>
                            <div class="action-buttons">
                                <button class="btn btn-warning btn-sm" onclick="editIncomeDetails(${data.id})">
                                    <i class="bi bi-pencil"></i>
                                </button>
                                <button class="btn btn-danger btn-sm" onclick="deleteIncomeDetails(${data.id})">
                                    <i class="bi bi-trash"></i>
                                </button>
                            </div>
                        </td>
                    </tr>
                `;
                
            } catch (error) {
                console.error('Error loading income details:', error);
                messageDiv.className = "alert alert-danger";
                messageDiv.innerHTML = `
                    <i class="bi bi-exclamation-triangle me-2"></i>
                    Error loading records: ${error.message}
                `;
                messageDiv.style.display = "block";
            }
        }

        // Edit Income Details
        async function editIncomeDetails(id) {
            try {
                const response = await fetch(`http://localhost:8080/api/income/${id}`);
                const data = await response.json();
                
                // Populate edit form
                document.getElementById("editId").value = data.id;
                document.getElementById("editPersonalId").value = data.personalId || '';
                document.getElementById("editEmploymentType").value = data.employmentType || '';
                document.getElementById("editOrganizationType").value = data.organizationType || '';
                document.getElementById("editEmployerName").value = data.employerName || '';
                document.getElementById("editMonthlyIncome").value = data.monthlyIncome || '';
                document.getElementById("editRetirementAge").value = data.retirementAge || '';
                document.getElementById("editCreatedDate").value = data.createdDate || '';
                
                // Show modal
                const modal = new bootstrap.Modal(document.getElementById('editModal'));
                modal.show();
                
            } catch (error) {
                console.error('Error loading income details for edit:', error);
                alert('Error loading income details: ' + error.message);
            }
        }

        // Update Income Details
        async function updateIncomeDetails() {
            const id = document.getElementById("editId").value;
            const formData = {
                personalId: parseInt(document.getElementById("editPersonalId").value),
                employmentType: document.getElementById("editEmploymentType").value,
                retirementAge: parseInt(document.getElementById("editRetirementAge").value),
                organizationType: document.getElementById("editOrganizationType").value,
                employerName: document.getElementById("editEmployerName").value.trim(),
                monthlyIncome: parseFloat(document.getElementById("editMonthlyIncome").value),
                createdDate: document.getElementById("editCreatedDate").value
            };

            try {
                const response = await fetch(`http://localhost:8080/api/income/update/${id}`, {
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
                    loadIncomeDetails();
                    
                    // Show success message
                    const messageDiv = document.getElementById("recordsMessage");
                    messageDiv.className = "alert alert-success";
                    messageDiv.innerHTML = `
                        <i class="bi bi-check-circle me-2"></i>
                        Income details updated successfully!
                    `;
                    messageDiv.style.display = "block";
                    
                    // Hide message after 3 seconds
                    setTimeout(() => {
                        messageDiv.style.display = "none";
                    }, 3000);
                } else {
                    throw new Error('Failed to update income details');
                }
            } catch (error) {
                console.error('Error updating income details:', error);
                alert('Error updating income details: ' + error.message);
            }
        }

        // Delete Income Details
        function deleteIncomeDetails(id) {
            currentDeleteId = id;
            const modal = new bootstrap.Modal(document.getElementById('deleteModal'));
            modal.show();
        }

        // Confirm Delete
        async function confirmDelete() {
            if (!currentDeleteId) return;
            
            try {
                const response = await fetch(`http://localhost:8080/api/income/delete/${currentDeleteId}`, {
                    method: "DELETE"
                });

                if (response.ok) {
                    // Close modal
                    const modal = bootstrap.Modal.getInstance(document.getElementById('deleteModal'));
                    modal.hide();
                    
                    // Reload data
                    loadIncomeDetails();
                    
                    // Show success message
                    const messageDiv = document.getElementById("recordsMessage");
                    messageDiv.className = "alert alert-success";
                    messageDiv.innerHTML = `
                        <i class="bi bi-check-circle me-2"></i>
                        Income details deleted successfully!
                    `;
                    messageDiv.style.display = "block";
                    
                    // Hide message after 3 seconds
                    setTimeout(() => {
                        messageDiv.style.display = "none";
                    }, 3000);
                } else {
                    throw new Error('Failed to delete income details');
                }
            } catch (error) {
                console.error('Error deleting income details:', error);
                alert('Error deleting income details: ' + error.message);
            }
        }

        function resetSubmitButton() {
            const submitBtn = document.querySelector('button[type="submit"]');
            const submitText = document.getElementById("submitText");
            const submitSpinner = document.getElementById("submitSpinner");
            
            submitBtn.disabled = false;
            submitText.textContent = "Submit Income Details";
            submitSpinner.style.display = "none";
        }