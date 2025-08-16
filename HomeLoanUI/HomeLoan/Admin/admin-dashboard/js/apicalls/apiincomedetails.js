const API_BASE_URL = 'http://localhost:8083/api/income';


//const API_BASE_URL='http://localhost:8080/api/income'; // nnchavan
export const fetchIncomeDetails = async (incomeId) => {
    try {
        const response = await fetch(`${API_BASE_URL}/${incomeId}`);
        if (!response.ok) throw new Error('Income details not found');
        return await response.json();
    } catch (error) {
        console.error('Error fetching income details:', error);
        throw error;
    }
};