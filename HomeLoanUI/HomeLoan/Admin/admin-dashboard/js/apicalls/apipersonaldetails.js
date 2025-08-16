const API_BASE_URL = 'http://localhost:8083/api/personal';
//const API_BASE_URL = 'http://localhost:8080/api/personal/id'; // admin service



export const fetchPersonalDetails = async (personalId) => {
    try {
        const response = await fetch(`${API_BASE_URL}/${personalId}`);
        if (!response.ok) throw new Error('Personal details not found');
        return await response.json();
    } catch (error) {
        console.error('Error fetching personal details:', error);
        throw error;
    }
};