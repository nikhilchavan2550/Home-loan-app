import { fetchIncomeDetails } from './apicalls/apiincomedetails.js';

export const renderIncomeDetails = async (incomeId, container) => {
    if (!container) return;

    try {
        // Show loading state
        container.innerHTML = `
            <div class="flex justify-center items-center py-8">
                <div class="animate-spin rounded-full h-8 w-8 border-b-2 border-blue-500"></div>
                <span class="ml-3">Loading income details...</span>
            </div>
        `;

        const incomeDetails = await fetchIncomeDetails(incomeId);
        
        container.innerHTML = `
            <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                <div class="income-detail-card bg-gray-50 p-6 rounded-lg">
                    <div class="flex items-center mb-4">
                        <i class="bi bi-briefcase text-2xl text-blue-600 mr-3"></i>
                        <h3 class="text-lg font-semibold">Employment Information</h3>
                    </div>
                    <div class="space-y-3">
                        <div>
                            <p class="text-sm text-gray-500">Employment Type</p>
                            <p class="font-medium">${incomeDetails.employmentType || 'N/A'}</p>
                        </div>
                        <div>
                            <p class="text-sm text-gray-500">Employer Name</p>
                            <p class="font-medium">${incomeDetails.employerName || 'N/A'}</p>
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
                            <p class="font-medium">${formatCurrency(incomeDetails.monthlyIncome)}</p>
                        </div>
                        <div>
                            <p class="text-sm text-gray-500">Annual Income</p>
                            <p class="font-medium">${formatCurrency(incomeDetails.monthlyIncome * 12)}</p>
                        </div>
                    </div>
                </div>
            </div>
        `;
    } catch (error) {
        container.innerHTML = `
            <div class="text-center py-8 text-red-500">
                <i class="bi bi-exclamation-triangle text-2xl"></i>
                <p class="mt-2">Failed to load income details</p>
                <button onclick="window.location.reload()" 
                        class="mt-3 px-3 py-1 bg-gray-100 rounded text-sm">
                    Retry
                </button>
            </div>
        `;
    }
};

function formatCurrency(amount) {
    return new Intl.NumberFormat('en-IN', {
        style: 'currency',
        currency: 'INR',
        maximumFractionDigits: 0
    }).format(amount);
}