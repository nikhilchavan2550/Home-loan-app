const API_BASE_URL = 'http://localhost:8083/api/loan';

//const API_BASE_URL = 'http://localhost:8080/api/loan'; // admin service

/**
 * Fetch application details by ID
 */
export const fetchApplicationById = async (applicationId) => {
    try {
        const response = await fetch(`${API_BASE_URL}/${applicationId}`);
        if (!response.ok) {
            throw new Error('Failed to fetch application');
        }
        return await response.json();
    } catch (error) {
        console.error('Error fetching application:', error);
        throw error;
    }
};

/**
 * Update application status (approve/reject)
 * 
 * 
 */
// apicheckapplication.js
const ADMIN_API_BASE_URL = 'http://localhost:8080/api/admin/loan/status';
const STATUS_TRACKING_API_URL = 'http://localhost:8082/api/application-status/update';

/**
 * Updates the loan application's status in both admin and tracking systems.
 * 
 * @param {number} applicationId - ID of the application
 * @param {string} loanStatus - New status (e.g., "APPROVED", "REJECTED")
 * @param {string|null} rejectionReason - Optional rejection reason
 * @param {number} adminId - ID of the admin performing the update
 */
export const updateApplicationStatus = async (applicationId, loanStatus, rejectionReason = null, adminId) => {
    const formattedStatus = loanStatus.toUpperCase();

    try {
        // 1. Update in the main admin loan system
        const adminResponse = await fetch(`${ADMIN_API_BASE_URL}/${applicationId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                loanStatus: formattedStatus,
                rejectionReason: formattedStatus === 'REJECTED' ? rejectionReason : null
            })
        });

        if (!adminResponse.ok) {
            const errorData = await adminResponse.json();
            console.error('Admin API validation errors:', errorData);
            throw new Error(errorData.message || 'Failed to update status in admin system');
        }

        // 2. Update in the status tracking system

        const trackingResponse = await fetch(STATUS_TRACKING_API_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                applicationId,
                newStatus: formattedStatus,
                adminId
            })
        });

        if (!trackingResponse.ok) {
            const errorData = await trackingResponse.json();
            console.error('Tracking API validation errors:', errorData);
            throw new Error(errorData.message || 'Failed to update status in tracking system');
        }

        return await trackingResponse.json();
    } catch (error) {
        console.error('Error updating application status:', error);
        throw error;
    }
};



