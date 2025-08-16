const API_BASE_URL = 'http://localhost:8083/api/property';
//const API_BASE_URL = 'http://localhost:8080/api/property'; // admin service
export const fetchPropertyDetails = async (propertyId) => {
    try {
        const response = await fetch(`${API_BASE_URL}/${propertyId}`);
        if (!response.ok) throw new Error('Property details not found');
        return await response.json();
    } catch (error) {
        console.error('Error fetching property details:', error);
        throw error;
    }
};