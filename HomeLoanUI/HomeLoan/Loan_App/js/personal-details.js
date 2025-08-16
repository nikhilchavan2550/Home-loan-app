// Personal Details JavaScript
let currentDeleteId = null;

document.addEventListener('DOMContentLoaded', function () {
    loadAndPrefillFormIfExists();
    loadPersonalDetails();
});

async function loadAndPrefillFormIfExists() {
    try {
        const customerData = JSON.parse(localStorage.getItem("customerData"));
        const response = await fetch(`http://localhost:8080/api/personal/id/${customerData.customerId}`);
        if (response.ok) {
            const data = await response.json();

            if (data && data.firstName) {
                document.getElementById("firstName").value = data.firstName || '';
                document.getElementById("middleName").value = data.middleName || '';
                document.getElementById("lastName").value = data.lastName || '';
                document.getElementById("dob").value = data.dob || '';
                document.getElementById("gender").value = data.gender || '';
                document.getElementById("nationality").value = data.nationality || '';
                document.getElementById("aadharNo").value = data.aadharNo || '';
                // document.getElementById("panNo").value = data.panNo || '';
                // document.getElementById("userEmail").value = data.email || '';
                document.getElementById("phoneNumber").value = data.phoneNumber || '';
                document.getElementById("address").value = data.address || '';

                const inputs = document.querySelectorAll("#personalDetailsForm input, #personalDetailsForm select, #personalDetailsForm textarea");
                inputs.forEach(input => input.setAttribute("disabled", "disabled"));

                document.querySelector("#personalDetailsForm button[type='submit']").style.display = "none";

                updateProgress(100);

                const responseDiv = document.getElementById("responseMessage");
                responseDiv.className = "alert alert-info";
                responseDiv.innerHTML = `<i class="bi bi-info-circle me-2"></i>Your personal details are already submitted and saved.`;
                responseDiv.style.display = "block";
            }
        }
    } catch (error) {
        console.error("Error fetching existing personal details:", error);
    }

    const customerData = JSON.parse(localStorage.getItem("customerData")) || {};

    if (customerData.email) {
        const userEmailField = document.getElementById("userEmail");
        userEmailField.value = customerData.email;
        userEmailField.disabled = true;
    }

    if (customerData.pan) {
        const panField = document.getElementById("panNo");
        panField.value = customerData.pan;
        panField.disabled = true;
    }

    if (customerData.email) {
        const editEmailField = document.getElementById("editEmail");
        editEmailField.value = customerData.email;
        editEmailField.disabled = true;
    }

    if (customerData.pan) {
        const editPanField = document.getElementById("editPanNo");
        editPanField.value = customerData.pan;
        editPanField.disabled = true;
    }


}


function validateAadhar(input) {
    const value = input.value.trim();
    const isValid = /^\d{12}$/.test(value);
    input.classList.toggle('is-valid', isValid && value.length > 0);
    input.classList.toggle('is-invalid', value.length > 0 && !isValid);
    return isValid;
}

function validatePAN(input) {
    const value = input.value.trim().toUpperCase();
    input.value = value; // Auto-uppercase
    const isValid = /^[A-Z]{5}[0-9]{4}[A-Z]{1}$/.test(value);
    input.classList.toggle('is-valid', isValid && value.length > 0);
    input.classList.toggle('is-invalid', value.length > 0 && !isValid);
    return isValid;
}

function validatePhone(input) {
    const value = input.value.trim();
    const isValid = /^[6-9]\d{9}$/.test(value);
    input.classList.toggle('is-valid', isValid && value.length > 0);
    input.classList.toggle('is-invalid', value.length > 0 && !isValid);
    return isValid;
}

function validateEmail(input) {
    const value = input.value.trim();
    if (value === '') return true; // Optional field
    const isValid = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value);
    input.classList.toggle('is-valid', isValid);
    input.classList.toggle('is-invalid', !isValid);
    return isValid;
}

document.addEventListener('DOMContentLoaded', function () {
    const aadharInput = document.getElementById("aadharNo");
    const panInput = document.getElementById("panNo");
    const phoneInput = document.getElementById("phoneNumber");
    const emailInput = document.getElementById("userEmail");

    if (aadharInput) {
        aadharInput.addEventListener('input', function () {
            validateAadhar(this);
        });
    }

    if (panInput) {
        panInput.addEventListener('input', function () {
            validatePAN(this);
        });
    }

    if (phoneInput) {
        phoneInput.addEventListener('input', function () {
            validatePhone(this);
        });
    }

    if (emailInput) {
        emailInput.addEventListener('input', function () {
            validateEmail(this);
        });
    }

    initializeProgressTracking();
});

document.getElementById("personalDetailsForm").addEventListener("submit", async function (e) {
    e.preventDefault();
    const customerData = JSON.parse(localStorage.getItem("customerData"));


    const responseDiv = document.getElementById("responseMessage");
    const submitBtn = this.querySelector('button[type="submit"]');
    const submitText = document.getElementById("submitText");
    const submitSpinner = document.getElementById("submitSpinner");

    submitBtn.disabled = true;
    submitText.textContent = "Submitting...";
    submitSpinner.style.display = "inline-block";
    responseDiv.style.display = "none";

    const formData = {
        id: customerData.customerId,
        firstName: document.getElementById("firstName").value.trim(),
        middleName: document.getElementById("middleName").value.trim(),
        lastName: document.getElementById("lastName").value.trim(),
        dob: document.getElementById("dob").value || null,
        gender: document.getElementById("gender").value || null,
        aadharNo: document.getElementById("aadharNo").value.trim(),
        panNo: document.getElementById("panNo").value.trim().toUpperCase(),
        phoneNumber: document.getElementById("phoneNumber").value.trim(),
        nationality: document.getElementById("nationality").value.trim() || 'Indian',
        email: document.getElementById("userEmail").value.trim() || null,
        address: document.getElementById("address").value.trim()
    };

    const validationErrors = [];

    if (!formData.firstName || formData.firstName.length < 2) {
        validationErrors.push('First name must be at least 2 characters');
    }

    if (!formData.lastName || formData.lastName.length < 2) {
        validationErrors.push('Last name must be at least 2 characters');
    }

    if (!formData.aadharNo) {
        validationErrors.push('Aadhar number is required');
    } else if (!/^\d{12}$/.test(formData.aadharNo)) {
        validationErrors.push('Aadhar number must be exactly 12 digits');
    }

    if (!formData.panNo) {
        validationErrors.push('PAN number is required');
    } else if (!/^[A-Z]{5}[0-9]{4}[A-Z]{1}$/.test(formData.panNo)) {
        validationErrors.push('PAN number must be in format: ABCDE1234F');
    }

    if (!formData.phoneNumber) {
        validationErrors.push('Phone number is required');
    } else if (!/^[6-9]\d{9}$/.test(formData.phoneNumber)) {
        validationErrors.push('Phone number must be 10 digits starting with 6, 7, 8, or 9');
    }

    // if (formData.email && !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(formData.email)) {
    //     validationErrors.push('Please enter a valid email address');
    // }

    if (formData.dob) {
        const dobDate = new Date(formData.dob);
        const today = new Date();
        if (dobDate >= today) {
            validationErrors.push('Date of birth must be in the past');
        }
    }

    if (validationErrors.length > 0) {
        showError('Please fix the following errors:\n• ' + validationErrors.join('\n• '));
        resetSubmitButton();
        return;
    }

    try {
        console.log('📤 Sending form data:', formData);

        const response = await fetch("http://localhost:8080/api/personal/add", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            body: JSON.stringify(formData)
        });

        const responseText = await response.text();
        console.log('📥 Raw response:', responseText);
        console.log('📊 Response status:', response.status);

        let responseData;
        try {
            responseData = JSON.parse(responseText);
            console.log('📋 Parsed response data:', responseData);
        } catch (e) {
            throw new Error(`Invalid JSON response: ${responseText}`);
        }

        if (response.ok) {
            responseDiv.className = "alert alert-success";
            responseDiv.innerHTML = `
                <i class="bi bi-check-circle me-2"></i>
                Personal details submitted successfully! Your application has been saved.
            `;
            responseDiv.style.display = "block";


            this.reset();
            updateProgress(0);
            loadPersonalDetails();
            loadAndPrefillFormIfExists();

        } else {
            let errorMessage = 'Failed to submit form';
            if (responseData && responseData.message) {
                errorMessage = responseData.message;
                if (responseData.errors) {
                    const errorDetails = Object.entries(responseData.errors)
                        .map(([field, error]) => `${field}: ${error}`)
                        .join(', ');
                    errorMessage = `Validation failed: ${errorDetails}`;
                }
            }
            throw new Error(errorMessage);
        }

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

async function loadPersonalDetails() {
    const tableBody = document.getElementById("personalDetailsTableBody");
    const messageDiv = document.getElementById("recordsMessage");

    try {
        const customerData = JSON.parse(localStorage.getItem("customerData"));
        const response = await fetch(`http://localhost:8080/api/personal/id/${customerData.customerId}`);
        const data = await response.json();

        if (data.length === 0) {
            tableBody.innerHTML = `
            <tr>
                <td colspan="6" class="text-center text-muted py-4">
                    <i class="bi bi-inbox fs-1"></i>
                    <p class="mt-2">No personal details records found.</p>
                </td>
            </tr>
        `;
            return;
        }

        tableBody.innerHTML = `
        <tr>
            <td>${data.firstName} ${data.middleName || ''} ${data.lastName}</td>
            <td>${data.phoneNumber || 'N/A'}</td>
            <td>${data.panNo || 'N/A'}</td>
            <td>${data.createdDate || 'N/A'}</td>
            <td>
                <div class="action-buttons">
                    <button class="btn btn-warning btn-sm" onclick="editPersonalDetails(${data.id})">
                        <i class="bi bi-pencil"></i>
                    </button>
                    <button class="btn btn-danger btn-sm" onclick="deletePersonalDetails(${data.id})">
                        <i class="bi bi-trash"></i>
                    </button>
                </div>
            </td>
        </tr>
    `;
    }
    catch (error) {
        console.error('Error loading personal details:', error);
        messageDiv.className = "alert alert-danger";
        messageDiv.innerHTML = `
            <i class="bi bi-exclamation-triangle me-2"></i>
            Error loading records: ${error.message}
        `;
        messageDiv.style.display = "block";
    }
}

async function editPersonalDetails(id) {
    try {
        const response = await fetch(`http://localhost:8080/api/personal/id/${id}`);
        const data = await response.json();

        document.getElementById("editId").value = data.id;
        document.getElementById("editFirstName").value = data.firstName || '';
        document.getElementById("editMiddleName").value = data.middleName || '';
        document.getElementById("editLastName").value = data.lastName || '';
        document.getElementById("editDob").value = data.dob || '';
        document.getElementById("editGender").value = data.gender || '';
        document.getElementById("editNationality").value = data.nationality || '';
        document.getElementById("editAadharNo").value = data.aadharNo || '';
        document.getElementById("editPanNo").value = data.panNo || '';
        document.getElementById("editEmail").value = data.email || '';
        document.getElementById("editPhoneNumber").value = data.phoneNumber || '';
        document.getElementById("editAddress").value = data.address || '';

        const modal = new bootstrap.Modal(document.getElementById('editModal'));
        modal.show();

    } catch (error) {
        console.error('Error loading personal details for edit:', error);
        alert('Error loading personal details: ' + error.message);
    }
}

async function updatePersonalDetails() {
    const customerData = JSON.parse(localStorage.getItem("customerData"));

    const id = document.getElementById("editId").value;
    const formData = {
        id: customerData.customerId,
        firstName: document.getElementById("editFirstName").value.trim(),
        middleName: document.getElementById("editMiddleName").value.trim(),
        lastName: document.getElementById("editLastName").value.trim(),
        dob: document.getElementById("editDob").value,
        gender: document.getElementById("editGender").value || null,
        aadharNo: document.getElementById("editAadharNo").value.trim(),
        panNo: document.getElementById("editPanNo").value.trim().toUpperCase(),
        phoneNumber: document.getElementById("editPhoneNumber").value.trim(),
        nationality: document.getElementById("editNationality").value.trim() || 'Indian',
        email: document.getElementById("editEmail").value.trim() || null,
        address: document.getElementById("editAddress").value.trim()
    };

    try {
        const response = await fetch(`http://localhost:8080/api/personal/update/${id}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(formData)
        });

        if (response.ok) {

            const modal = bootstrap.Modal.getInstance(document.getElementById('editModal'));
            modal.hide();


            loadPersonalDetails();
            const messageDiv = document.getElementById("recordsMessage");
            messageDiv.className = "alert alert-success";
            messageDiv.innerHTML = `
                <i class="bi bi-check-circle me-2"></i>
                Personal details updated successfully!
            `;
            messageDiv.style.display = "block";

            setTimeout(() => {
                messageDiv.style.display = "none";
            }, 3000);
        } else {
            throw new Error('Failed to update personal details');
        }
    } catch (error) {
        console.error('Error updating personal details:', error);
        alert('Error updating personal details: ' + error.message);
    }
}

function deletePersonalDetails(id) {
    currentDeleteId = id;
    const modal = new bootstrap.Modal(document.getElementById('deleteModal'));
    modal.show();
}

async function confirmDelete() {
    if (!currentDeleteId) return;

    try {
        const response = await fetch(`http://localhost:8080/api/personal/delete/${currentDeleteId}`, {
            method: "DELETE"
        });

        if (response.ok) {
            const modal = bootstrap.Modal.getInstance(document.getElementById('deleteModal'));
            modal.hide();

            loadPersonalDetails();

            const messageDiv = document.getElementById("recordsMessage");
            messageDiv.className = "alert alert-success";
            messageDiv.innerHTML = `
                <i class="bi bi-check-circle me-2"></i>
                Personal details deleted successfully!
            `;
            messageDiv.style.display = "block";

            setTimeout(() => {
                messageDiv.style.display = "none";
            }, 3000);
        } else {
            throw new Error('Failed to delete personal details');
        }
    } catch (error) {
        console.error('Error deleting personal details:', error);
        alert('Error deleting personal details: ' + error.message);
    }
}

function resetSubmitButton() {
    const submitBtn = document.querySelector('button[type="submit"]');
    const submitText = document.getElementById("submitText");
    const submitSpinner = document.getElementById("submitSpinner");

    submitBtn.disabled = false;
    submitText.textContent = "Submit Personal Details";
    submitSpinner.style.display = "none";
}

function showError(message) {
    const responseDiv = document.getElementById("responseMessage");
    responseDiv.className = "alert alert-danger";
    responseDiv.innerHTML = `
        <i class="bi bi-exclamation-triangle me-2"></i>
        ${message}
    `;
    responseDiv.style.display = "block";
}

function updateProgress(percentage) {
    const progressFill = document.getElementById("progressFill");
    const progressText = document.getElementById("progressText");

    if (progressFill && progressText) {
        progressFill.style.width = `${percentage}%`;
        progressText.textContent = `${percentage}%`;
    }
}

function initializeProgressTracking() {
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

    calculateProgress();
} 