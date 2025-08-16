 //const API_BASE_URL = 'http://localhost:8080/api/admin/loan/all';
// Updated API client with proper error handling
const API_BASE_URL = 'http://localhost:8083/api/admin/loan/all';

export const fetchAllApplications = async () => {
    try {
        const response = await fetch(API_BASE_URL, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            credentials: 'same-origin' // Changed from 'include' to avoid CORS preflight
        });

        if (!response.ok) {
            // Try to parse error response
            let errorMsg = 'Failed to fetch applications';
            try {
                const errorData = await response.json();
                errorMsg = errorData.message || errorMsg;
            } catch (e) {
                errorMsg = `HTTP error! status: ${response.status}`;
            }
            throw new Error(errorMsg);
        }

        return await response.json();
    } catch (error) {
        console.error('API Error:', error);
        throw error; // Re-throw for UI to handle
    }
};