//orgginal code
import {
    fetchApplicationById,
    updateApplicationStatus
} from './apicalls/apicheckapplication.js';
import { fetchIncomeDetails } from './apicalls/apiincomedetails.js';
import { fetchPropertyDetails } from './apicalls/apipropertydetails.js';
import { fetchPersonalDetails } from './apicalls/apipersonaldetails.js';
import { fetchCurrentAdmin } from './apicalls/adminapi.js';
import { updateAdminStatus } from './apicalls/apiadminstatus.js';
document.addEventListener('DOMContentLoaded', async () => {
    // DOM Elements
    const appIdModal = document.getElementById('appIdModal');
    const applicationContainer = document.getElementById('applicationContainer');
    const applicationIdInput = document.getElementById('applicationIdInput');
    const clearBtn = document.getElementById('clearBtn');
    const submitBtn = document.getElementById('submitBtn');
    const appIdBadge = document.getElementById('appIdBadge');
    const remarksInput = document.getElementById('remarksInput');
    const approveBtn = document.getElementById('approveBtn');
    const revertBtn = document.getElementById('revertBtn');
    const updateRemarkBtn = document.getElementById('updateRemarkBtn');
    
    const rejectBtn = document.getElementById('rejectBtn');
    const emailBtn = document.getElementById('emailBtn');
    const downloadBtn = document.getElementById('downloadBtn');
    const applicationDetails = document.getElementById('applicationDetails');
    let custid = 0;

    let currentApplication = null;

    const urlParams = new URLSearchParams(window.location.search);
    const applicationId = urlParams.get('id');

    if (applicationId) {
        appIdModal.classList.add('hidden');
        console.log('Loaded application with ID:', applicationId);
        const appId = applicationId;
            if (!appId) {
                alert('Please enter an Application ID');
                return;
            }
            try {
                showLoading(true);
                const application = await fetchApplicationById(appId);
                console.log('API Response:', application); // Debug log
                if (!application) {
                    throw new Error('Application data not found');
                }
                currentApplication = application;

                custid = currentApplication.personalId;

                renderApplication(application);
                if (appIdModal) appIdModal.classList.add('hidden');
                if (applicationContainer) applicationContainer.classList.remove('hidden');
                setupTabListeners();
            } catch (error) {
                console.error('Error loading application:', error);
                showError(error.message || 'Failed to load application. Please try again.');
            } finally {
                showLoading(false);
            }
    } else {
    
    // Show modal on page load
    if (appIdModal) appIdModal.classList.remove('hidden');
    // Clear button handler
    if (clearBtn) {
        clearBtn.addEventListener('click', () => {
            applicationIdInput.value = '';
        });
    }
    // Submit button handler
    if (submitBtn) {
        submitBtn.addEventListener('click', async () => {
            const appId = applicationIdInput.value.trim();
            if (!appId) {
                alert('Please enter an Application ID');
                return;
            }
            try {
                showLoading(true);
                const application = await fetchApplicationById(appId);
                console.log('API Response:', application); // Debug log
                if (!application) {
                    throw new Error('Application data not found');
                }
                currentApplication = application;

                custid = currentApplication.personalId;

                renderApplication(application);
                if (appIdModal) appIdModal.classList.add('hidden');
                if (applicationContainer) applicationContainer.classList.remove('hidden');
                setupTabListeners();
            } catch (error) {
                console.error('Error loading application:', error);
                showError(error.message || 'Failed to load application. Please try again.');
            } finally {
                showLoading(false);
            }
        });
    }
    }
    // Action buttons
    if (approveBtn) approveBtn.addEventListener('click', () => updateStatus('APPROVED'));
    if (rejectBtn) rejectBtn.addEventListener('click', () => updateStatus('REJECTED'));
    if (revertBtn) revertBtn.addEventListener('click', () => revertStatus());
    if (updateRemarkBtn) updateRemarkBtn.addEventListener('click', () => giveReason());
    
    if (emailBtn) emailBtn.addEventListener('click', sendEmail);
    if (downloadBtn) downloadBtn.addEventListener('click', downloadApplication);
    // Setup tab listeners
    function setupTabListeners() {
        document.querySelectorAll('.tab-btn').forEach(button => {
            button.addEventListener('click', async function () {
                // Remove active class from all buttons and contents
                document.querySelectorAll('.tab-btn').forEach(btn => btn.classList.remove('active'));
                document.querySelectorAll('.tab-content').forEach(tab => tab.classList.add('hidden'));

                // Add active class to clicked button
                this.classList.add('active');

                // Show corresponding content
                const tabId = this.getAttribute('data-tab');
                const tabContent = document.getElementById(tabId);
                if (tabContent) {
                    tabContent.classList.remove('hidden');

                    // Load data if needed
                    if (currentApplication) {
                        switch (tabId) {
                            case 'personal':
                                await renderPersonalTab(currentApplication.personalId);
                                break;
                            case 'income':
                                await renderIncomeTab(currentApplication.incomeId);
                                break;
                            case 'property':
                                await renderPropertyTab(currentApplication.propertyId);
                                break;
                            case 'documents':
                                await renderDocumentTab(currentApplication.personalId);
                                break;
                        }
                    }
                }
            });
        });
    }

    //------------------------------------>Personal
    // Render personal tab content
    async function renderPersonalTab(personalId) {
        const personalContainer = document.getElementById('personalDetailsContainer');
        if (!personalContainer) return;
        try {
            // Show loading state
            personalContainer.innerHTML = `
                <div class="flex justify-center items-center py-8">
                    <div class="animate-spin rounded-full h-8 w-8 border-b-2 border-blue-500"></div>
                    <span class="ml-3">Loading personal details...</span>
                </div>
            `;
            const personalDetails = await fetchPersonalDetails(personalId);
            if (!personalDetails) {
                throw new Error('Personal details not found');
            }

            personalContainer.innerHTML = `
                <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                    <div class="personal-detail-card bg-gray-50 p-6 rounded-lg">
                        <div class="flex items-center mb-4">
                            <i class="bi bi-person text-2xl text-blue-600 mr-3"></i>
                            <h3 class="text-lg font-semibold">Basic Information</h3>
                        </div>
                        <div class="space-y-3">
                            <div>
                                <p class="text-sm text-gray-500">Full Name</p>
                                <p class="font-medium">${formatName(personalDetails)}</p>
                            </div>
                            <div>
                                <p class="text-sm text-gray-500">Date of Birth</p>
                                <p class="font-medium">${formatDate(personalDetails?.dob)}</p>
                            </div>
                            <div>
                                <p class="text-sm text-gray-500">Gender</p>
                                <p class="font-medium">${personalDetails?.gender || 'N/A'}</p>
                            </div>
                        </div>
                    </div>
                    <div class="personal-detail-card bg-gray-50 p-6 rounded-lg">
                        <div class="flex items-center mb-4">
                            <i class="bi bi-card-text text-2xl text-green-600 mr-3"></i>
                            <h3 class="text-lg font-semibold">Identity Documents</h3>
                        </div>
                        <div class="space-y-3">
                            <div>
                                <p class="text-sm text-gray-500">Aadhar Number</p>
                                <p class="font-medium">${personalDetails?.aadharNo || 'N/A'}</p>
                            </div>
                            <div>
                                <p class="text-sm text-gray-500">PAN Number</p>
                                <p class="font-medium">${personalDetails?.panNo || 'N/A'}</p>
                            </div>
                            <div>
                                <p class="text-sm text-gray-500">Nationality</p>
                                <p class="font-medium">${personalDetails?.nationality || 'N/A'}</p>
                            </div>
                        </div>
                    </div>
                    <div class="personal-detail-card bg-gray-50 p-6 rounded-lg md:col-span-2">
                        <div class="flex items-center mb-4">
                            <i class="bi bi-telephone text-2xl text-purple-600 mr-3"></i>
                            <h3 class="text-lg font-semibold">Contact Information</h3>
                        </div>
                        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                            <div>
                                <p class="text-sm text-gray-500">Phone Number</p>
                                <p class="font-medium">${personalDetails?.phoneNumber || 'N/A'}</p>
                            </div>
                            <div>
                                <p class="text-sm text-gray-500">Record Created</p>
                                <p class="font-medium">${formatDate(personalDetails?.createdDate)}</p>
                            </div>
                        </div>
                    </div>
                </div>
            `;
        } catch (error) {
            personalContainer.innerHTML = `
                <div class="text-center py-8 text-red-500">
                    <i class="bi bi-exclamation-triangle text-2xl"></i>
                    <p class="mt-2">Failed to load personal details</p>
                    <button onclick="location.reload()" 
                            class="mt-3 px-3 py-1 bg-gray-100 rounded text-sm">
                        Retry
                    </button>
                </div>
            `;
            console.error('Error loading personal details:', error);
        }
    }



    //------------------------------------>Income
    // Render income tab content
    async function renderIncomeTab(incomeId) {
        const incomeContainer = document.getElementById('incomeDetailsContainer');
        if (!incomeContainer) return;
        try {
            // Show loading state
            incomeContainer.innerHTML = `
                <div class="flex justify-center items-center py-8">
                    <div class="animate-spin rounded-full h-8 w-8 border-b-2 border-blue-500"></div>
                    <span class="ml-3">Loading income details...</span>
                </div>
            `;
            const incomeDetails = await fetchIncomeDetails(incomeId);
            if (!incomeDetails) {
                throw new Error('Income details not found');
            }

            incomeContainer.innerHTML = `
                <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                    <div class="income-detail-card bg-gray-50 p-6 rounded-lg">
                        <div class="flex items-center mb-4">
                            <i class="bi bi-briefcase text-2xl text-blue-600 mr-3"></i>
                            <h3 class="text-lg font-semibold">Employment Information</h3>
                        </div>
                        <div class="space-y-3">
                            <div>
                                <p class="text-sm text-gray-500">Employment Type</p>
                                <p class="font-medium">${incomeDetails?.employmentType || 'N/A'}</p>
                            </div>
                            <div>
                                <p class="text-sm text-gray-500">Organization Type</p>
                                <p class="font-medium">${incomeDetails?.organizationType || 'N/A'}</p>
                            </div>
                            <div>
                                <p class="text-sm text-gray-500">Employer Name</p>
                                <p class="font-medium">${incomeDetails?.employerName || 'N/A'}</p>
                            </div>
                        </div>
                    </div>
                    <div class="income-detail-card bg-gray-50 p-6 rounded-lg">
                        <div class="flex items-center mb-4">
                            <i class="bi bi-cash-stack text-2xl text-green-600 mr-3"></i>
                            <h3 class="text-lg font-semibold">Income Details</h3>
                        </div>
                        <div class="space-y-3">
                            <div>
                                <p class="text-sm text-gray-500">Monthly Income</p>
                                <p class="font-medium">${formatCurrency(incomeDetails?.monthlyIncome)}</p>
                            </div>
                            <div>
                                <p class="text-sm text-gray-500">Annual Income</p>
                                <p class="font-medium">${formatCurrency(incomeDetails?.monthlyIncome ? incomeDetails.monthlyIncome * 12 : 0)}</p>
                            </div>
                            <div>
                                <p class="text-sm text-gray-500">Retirement Age</p>
                                <p class="font-medium">${incomeDetails?.retirementAge || 'N/A'}</p>
                            </div>
                        </div>
                    </div>
                    <div class="income-detail-card bg-gray-50 p-6 rounded-lg md:col-span-2">
                        <div class="flex items-center mb-4">
                            <i class="bi bi-calendar-date text-2xl text-purple-600 mr-3"></i>
                            <h3 class="text-lg font-semibold">Record Information</h3>
                        </div>
                        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                            <div>
                                <p class="text-sm text-gray-500">Created Date</p>
                                <p class="font-medium">${formatDate(incomeDetails?.createdDate)}</p>
                            </div>
                        </div>
                    </div>
                </div>
            `;
        } catch (error) {
            incomeContainer.innerHTML = `
                <div class="text-center py-8 text-red-500">
                    <i class="bi bi-exclamation-triangle text-2xl"></i>
                    <p class="mt-2">Failed to load income details</p>
                    <button onclick="location.reload()" 
                            class="mt-3 px-3 py-1 bg-gray-100 rounded text-sm">
                        Retry
                    </button>
                </div>
            `;
            console.error('Error loading income details:', error);
        }
    }



    //------------------------------------>Property
    // Render property tab content
    async function renderPropertyTab(propertyId) {
        const propertyContainer = document.getElementById('propertyDetailsContainer');
        if (!propertyContainer) return;
        try {
            // Show loading state
            propertyContainer.innerHTML = `
                <div class="flex justify-center items-center py-8">
                    <div class="animate-spin rounded-full h-8 w-8 border-b-2 border-blue-500"></div>
                    <span class="ml-3">Loading property details...</span>
                </div>
            `;
            const propertyDetails = await fetchPropertyDetails(propertyId);
            if (!propertyDetails) {
                throw new Error('Property details not found');
            }

            propertyContainer.innerHTML = `
                <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                    <div class="property-detail-card bg-gray-50 p-6 rounded-lg">
                        <div class="flex items-center mb-4">
                            <i class="bi bi-house text-2xl text-blue-600 mr-3"></i>
                            <h3 class="text-lg font-semibold">Property Information</h3>
                        </div>
                        <div class="space-y-3">
                            <div>
                                <p class="text-sm text-gray-500">Property Name</p>
                                <p class="font-medium">${propertyDetails?.propertyName || 'N/A'}</p>
                            </div>
                            <div>
                                <p class="text-sm text-gray-500">Location</p>
                                <p class="font-medium">${propertyDetails?.propertyLocation || 'N/A'}</p>
                            </div>
                            <div>
                                <p class="text-sm text-gray-500">City/State</p>
                                <p class="font-medium">${propertyDetails?.city || 'N/A'}, ${propertyDetails?.state || 'N/A'}</p>
                            </div>
                        </div>
                    </div>
                    <div class="property-detail-card bg-gray-50 p-6 rounded-lg">
                        <div class="flex items-center mb-4">
                            <i class="bi bi-rulers text-2xl text-green-600 mr-3"></i>
                            <h3 class="text-lg font-semibold">Specifications</h3>
                        </div>
                        <div class="space-y-3">
                            <div>
                                <p class="text-sm text-gray-500">Property Area</p>
                                <p class="font-medium">${propertyDetails?.propertyArea || 'N/A'}</p>
                            </div>
                            <div>
                                <p class="text-sm text-gray-500">Estimated Value</p>
                                <p class="font-medium">${formatCurrency(propertyDetails?.estimatedAmount)}</p>
                            </div>
                            <div>
                                <p class="text-sm text-gray-500">Completion Date</p>
                                <p class="font-medium">${formatDate(propertyDetails?.constructionCompletionDate)}</p>
                            </div>
                        </div>
                    </div>
                    <div class="property-detail-card bg-gray-50 p-6 rounded-lg md:col-span-2">
                        <div class="flex items-center mb-4">
                            <i class="bi bi-calendar-date text-2xl text-purple-600 mr-3"></i>
                            <h3 class="text-lg font-semibold">Record Information</h3>
                        </div>
                        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                            <div>
                                <p class="text-sm text-gray-500">Created Date</p>
                                <p class="font-medium">${formatDate(propertyDetails?.createdDate)}</p>
                            </div>
                        </div>
                    </div>
                </div>
            `;
        } catch (error) {
            propertyContainer.innerHTML = `
                <div class="text-center py-8 text-red-500">
                    <i class="bi bi-exclamation-triangle text-2xl"></i>
                    <p class="mt-2">Failed to load property details</p>
                    <button onclick="location.reload()" 
                            class="mt-3 px-3 py-1 bg-gray-100 rounded text-sm">
                        Retry
                    </button>
                </div>
            `;
            console.error('Error loading property details:', error);
        }
    }



    async function updateDocumentStatus(docId, newStatus) {
        const payload = {
            status: newStatus
        };

        const resp = await fetch(
            `http://localhost:8086/api/documents/update/${docId}`,
            {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            }
        );

        if (!resp.ok) throw new Error('Failed to update document status');
        return resp.json();
    }


    async function fetchDocumentsByCustId(custId) {
        const resp = await fetch(`http://localhost:8086/api/documents/customer/${custId}`);
        if (!resp.ok) throw new Error('Failed to fetch documents');
        return resp.json();
    }

    async function renderDocumentTab(custId) {
        const container = document.getElementById('documentsContainer');
        if (!container) return;

        container.innerHTML = `<div class="flex justify-center py-6">
        <div class="animate-spin rounded-full h-8 w-8 border-b-2 border-blue-500"></div>
        <span class="ml-3">Loading documents...</span>
    </div>`;

        try {
            const docs = await fetchDocumentsByCustId(custId);
            if (!docs.length) {
                container.innerHTML = '<p class="text-gray-500">No documents found.</p>';
                return;
            }

            // Build table
            const rows = docs.map(doc => {
                return `
            <tr data-id="${doc.id}" class="border-b hover:bg-gray-50">
                <td class="px-4 py-2">
                    <a href="http://localhost:8086/api/documents/download/${doc.id}" target="_blank" class="text-blue-600 underline">
                        ${doc.fileName}
                    </a>
                </td>
                <td class="px-4 py-2">${new Date(doc.uploadTime).toLocaleDateString()}</td>
                <td class="px-4 py-2">${doc.fileType}</td>
                <td class="px-4 py-2">
                    <input type="text" value="${doc.status}" class="status-input border rounded px-2 py-1 w-full" />
                </td>
                <td class="px-4 py-2">
                    <button class="update-doc-btn bg-blue-600 text-white px-3 py-1 rounded">
                        Update
                    </button>
                </td>
            </tr>
            `;
            }).join('');

            container.innerHTML = `
        <table class="min-w-full bg-white">
            <thead>
                <tr class="border-b bg-gray-100">
                    <th class="px-4 py-2 text-left">File</th>
                    <th class="px-4 py-2 text-left">Uploaded</th>
                    <th class="px-4 py-2 text-left">Status</th>
                    <th class="px-4 py-2"></th>
                </tr>
            </thead>
            <tbody>
                ${rows}
            </tbody>
        </table>
        `;

            // Attach update handlers
            container.querySelectorAll('.update-doc-btn').forEach(button => {
                button.addEventListener('click', async () => {
                    const tr = button.closest('tr');
                    const docId = tr.getAttribute('data-id');
                    const input = tr.querySelector('.status-input');
                    const newStatus = input.value.trim();
                    if (!newStatus) {
                        alert('Status cannot be empty');
                        return;
                    }
                    button.disabled = true;
                    button.textContent = 'Updating...';
                    try {
                        const updated = await updateDocumentStatus(docId, newStatus);
                        input.value = updated.status;
                        alert('Document status updated');
                    } catch (err) {
                        console.error(err);
                        alert('Failed to update status');
                    } finally {
                        button.disabled = false;
                        button.textContent = 'Update';
                    }
                });
            });

        } catch (error) {
            console.error(error);
            container.innerHTML = `<p class="text-red-500">Error loading documents</p>`;
        }
    }



    //--------------------------------------------> Application Details
    // Render application details
    function renderApplication(application) {
        if (!application) {
            showError('Application data is invalid');
            return;
        }
        if (appIdBadge) appIdBadge.textContent = `#${application?.id || 'N/A'}`;

        if (applicationDetails) {
           

            applicationDetails.innerHTML = `
            <div class="grid grid-cols-1 md:grid-cols-2 gap-4 mb-6">
                <div class="bg-gray-50 p-4 rounded-lg">
                    <h4 class="font-medium text-gray-700">Application Number</h4>
                    <p class="mt-1">${application?.id || 'N/A'}</p>
                </div>
                <div class="bg-gray-50 p-4 rounded-lg">
                    <h4 class="font-medium text-gray-700">Status</h4>
                    <p class="mt-1">
                        <span class="${getStatusClass(application?.applicationStatus)}">
                            ${application?.applicationStatus || 'N/A'}
                        </span>
                    </p>
                </div>
                <div class="bg-gray-50 p-4 rounded-lg">
                    <h4 class="font-medium text-gray-700">Requested Amount</h4>
                    <p class="mt-1">${formatCurrency(application?.requestedLoanAmount)}</p>
                </div>
                <div class="bg-gray-50 p-4 rounded-lg">
                    <h4 class="font-medium text-gray-700">Loan Tenure</h4>
                    <p class="mt-1">${application?.loanTenureYears || 'N/A'} years</p>
                </div>
                <div class="bg-gray-50 p-4 rounded-lg">
                    <h4 class="font-medium text-gray-700">Interest Rate</h4>
                    <p class="mt-1">${application?.interestRate || 'N/A'}%</p>
                </div>
                <div class="bg-gray-50 p-4 rounded-lg">
                    <h4 class="font-medium text-gray-700">Application Date</h4>
                    <p class="mt-1">${formatDate(application?.applicationDate)}</p>
                </div>
                <div class="bg-gray-50 p-4 rounded-lg">
                    <h4 class="font-medium text-gray-700">Approval Date</h4>
                    <p class="mt-1">${formatDate(application?.approvalDate)}</p>
                </div>
                ${application?.rejectionReason ? `
                <div class="bg-gray-50 p-4 rounded-lg md:col-span-2">
                    <h4 class="font-medium text-gray-700">Rejection Reason</h4>
                    <p class="mt-1">${application.rejectionReason}</p>
                </div>
                ` : ''}
            </div>
        `;

            if (application?.applicationStatus === 'REJECTED' && application?.rejectionReason) {
                remarksInput.value = application.rejectionReason;
                remarksInput.placeholder = 'Enter rejection reason';
            } else if (application?.applicationStatus === 'APPROVED') {
                remarksInput.value = '';
                remarksInput.placeholder = 'Approval remarks (optional)';
            }
        }

        // If the application is rejected and has a reason, populate the remarks field
        if (application?.applicationStatus === 'REJECTED' && application?.rejectionReason) {
            remarksInput.value = application.rejectionReason;
        }
    }

    async function giveReason() {
    if (!currentApplication) {
        alert('No application loaded');
        return;
    }

    const remarks = document.getElementById('remarksInput').value.trim();
    if (!remarks) {
        alert('Please enter a rejection reason.');
        return;
    }

    try {
        const response = await fetch(`http://localhost:8080/api/admin/loan/rejection-reason/${currentApplication.id}`, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ rejectionReason: remarks })
        });

        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || 'Failed to update rejection reason');
        }

        const updatedApplication = await response.json();
        console.log('Rejection reason updated:', updatedApplication);
        alert('Remark updated successfully!');
    } catch (error) {
        console.error('Error updating rejection reason:', error);
        alert('Error updating rejection reason: ' + error.message);
    }
}



    async function revertStatus() {
        if (!currentApplication) {
            alert('No application loaded');
            return;
        }
        revertApplicationStatus(currentApplication.id);
    }

    async function revertApplicationStatus(applicationId) {
    const REVERT_STATUS_API_URL = 'http://localhost:8082/api/application-status/revert';

    try {
        const trackingResponse = await fetch(`${REVERT_STATUS_API_URL}/${applicationId}`, {
            method: 'POST',
           
        });

        if (!trackingResponse.ok) {
            const errorData = await trackingResponse.json();
            console.error('Revert API validation errors:', errorData);
            throw new Error(errorData.message || 'Failed to revert status in tracking system');
        }

        updateStatus("PENDING");


    } catch (error) {
        console.error('Error reverting application status:', error);
        throw error;
    }
}


    ///---------------------->// Update application status

    async function updateStatus(newStatus) {
        if (!currentApplication) {
            alert('No application loaded');
            return;
        }

        // Prevent duplicate approve/reject actions
        if (
            (newStatus === 'APPROVED' && currentApplication.applicationStatus === 'APPROVED') ||
            (newStatus === 'REJECTED' && currentApplication.applicationStatus === 'REJECTED')
        ) {
            alert(`Admin already ${newStatus.toLowerCase()}d this application.`);
            return;
        }

        const rejectionReason = remarksInput.value.trim();

        if (newStatus === 'REJECTED' && !rejectionReason) {
            alert('Please provide rejection reason');
            return;
        }

        const actionButton = newStatus === 'APPROVED' ? approveBtn : rejectBtn;

        try {
            showLoading(true, actionButton);

            const confirmAction = confirm(`Are you sure you want to ${newStatus.toLowerCase()} this application?`);
            if (!confirmAction) {
                showLoading(false, actionButton);
                return;
            }

            // Get current admin ID
            const admin = await fetchCurrentAdmin();
            const adminId = admin.adminId;

            // Send status to your API (hardcoded as "APPROVE" or "REJECT")
            const apiStatus = newStatus === 'APPROVED' ? 'APPROVED' : 'REJECTED';
            // await updateAdminStatus({
            //     applicationId: currentApplication.id,
            //     newStatus: apiStatus,
            //     adminId: adminId
            // });

            // Continue with your existing logic
            const updatedApp = await updateApplicationStatus(
                currentApplication.id,
                newStatus,
                newStatus === 'REJECTED' ? rejectionReason : null,
                adminId
            );

            // Update local state
            currentApplication.applicationStatus = updatedApp.applicationStatus;
            currentApplication.rejectionReason = updatedApp.rejectionReason;

            // Set approval date if approved, else set to null
            if (newStatus === 'APPROVED') {
                currentApplication.approvalDate = new Date().toISOString();
            } else if (newStatus === 'REJECTED') {
                currentApplication.approvalDate = null;
            }

            renderApplication(currentApplication);

            alert(`Application ${newStatus.toLowerCase()} successfully!`);

            // Clear remarks for both approval and rejection
            remarksInput.value = '';

        } catch (error) {
            console.error('Error updating status:', error);
            alert(error.message || 'Failed to update status');
        } finally {
            showLoading(false, actionButton);
        }
    }



    //--------------------------------// Email and Download functionality

    // Placeholder functions for future implementation
    function sendEmail() {
        if (!currentApplication) {
            alert('No application loaded');
            return;
        }
        alert(`Email functionality will be implemented for application ${currentApplication.id}`);
    }
    function downloadApplication() {
        if (!currentApplication) {
            alert('No application loaded');
            return;
        }
        alert(`PDF download will be implemented for application ${currentApplication.id}`);
    }
    // Helper functions
    function showLoading(isLoading, button = submitBtn) {
        if (!button) return;

        if (isLoading) {
            button.innerHTML = '<i class="bi bi-arrow-repeat animate-spin"></i> Processing...';
            button.disabled = true;
        } else {
            // Reset to original state based on button type
            if (button === approveBtn) {
                button.innerHTML = '<i class="bi bi-check-circle mr-2"></i> Approve';
            }
            if (button === revertBtn) {
                button.innerHTML = '<i class="bi bi-arrow-repeat animate-spin"></i> Reverting...';
            }
            else if (button === rejectBtn) {
                button.innerHTML = '<i class="bi bi-x-circle mr-2"></i> Reject';
            } else if (button === submitBtn) {
                button.innerHTML = 'Submit';
            }
            button.disabled = false;
        }
    }
    function showError(message) {
        const container = document.getElementById('applicationContainer');
        if (!container) return;

        container.innerHTML = `
            <div class="text-center py-12">
                <i class="bi bi-exclamation-triangle text-4xl text-red-500 mb-4"></i>
                <p class="text-red-500 text-lg">${message}</p>
                <button class="mt-4 px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700"
                        onclick="location.reload()">
                    Retry
                </button>
            </div>
        `;
    }
    function formatName(personalDetails) {
        if (!personalDetails) return 'N/A';
        let name = personalDetails.firstName || '';
        if (personalDetails.middleName) name += ` ${personalDetails.middleName}`;
        if (personalDetails.lastName) name += ` ${personalDetails.lastName}`;
        return name || 'N/A';
    }
    function getStatusClass(status) {
        if (!status) return 'px-2 py-1 rounded text-xs bg-gray-100 text-gray-800';

        switch (status.toUpperCase()) {
            case 'PENDING': return 'px-2 py-1 rounded text-xs bg-yellow-100 text-yellow-800';
            case 'APPROVED': return 'px-2 py-1 rounded text-xs bg-green-100 text-green-800';
            case 'REJECTED': return 'px-2 py-1 rounded text-xs bg-red-100 text-red-800';
            default: return 'px-2 py-1 rounded text-xs bg-gray-100 text-gray-800';
        }
    }
    function formatDate(dateString) {
        if (!dateString) return 'N/A';
        try {
            const date = new Date(dateString);
            if (isNaN(date.getTime())) return 'N/A';
            return date.toLocaleDateString('en-US', {
                year: 'numeric',
                month: 'short',
                day: 'numeric'
            });
        } catch (e) {
            console.error('Invalid date format:', dateString);
            return 'N/A';
        }
    }
    function formatCurrency(amount) {
        if (amount === null || amount === undefined || isNaN(amount)) {
            return '₹0'; // Or 'N/A' if preferred
        }
        return new Intl.NumberFormat('en-IN', {
            style: 'currency',
            currency: 'INR',
            maximumFractionDigits: 0
        }).format(amount);
    }
});
