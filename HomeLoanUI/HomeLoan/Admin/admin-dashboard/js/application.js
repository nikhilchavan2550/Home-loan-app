import { fetchAllApplications } from './apicalls/application.js';

// Store applications globally
let allApplications = [];

document.addEventListener('DOMContentLoaded', async () => {
    try {
        allApplications = await fetchAllApplications();
        renderApplications(allApplications);
        setupFilterButtons();
    } catch (error) {
        showError('Failed to load applications. Please try again later.');
    }
});

function setupFilterButtons() {
    const filterButtons = document.querySelectorAll('.filter-btn');
    
    filterButtons.forEach(button => {
        button.addEventListener('click', () => {
            // Update active state
            filterButtons.forEach(btn => btn.classList.remove('bg-blue-100', 'text-blue-700'));
            button.classList.add('bg-blue-100', 'text-blue-700');
            
            // Filter applications
            const status = button.dataset.status;
            if (status === 'all') {
                renderApplications(allApplications);
            } else {
                const filteredApps = allApplications.filter(app => 
                    app.applicationStatus.toLowerCase() === status
                );
                renderApplications(filteredApps);
            }
        });
    });
    
    // Set 'All Applications' as active by default
    document.querySelector('.filter-btn[data-status="all"]').classList.add('bg-blue-100', 'text-blue-700');
}

// ... rest of your existing JavaScript code remains the same ...

function renderApplications(applications) {
    const container = document.getElementById('applications-container');
    container.innerHTML = '';

    if (applications.length === 0) {
        container.innerHTML = `
            <div class="col-span-full text-center py-12">
                <i class="bi bi-folder-x text-4xl text-gray-400 mb-4"></i>
                <p class="text-gray-500">No applications found</p>
            </div>
        `;
        return;
    }

    applications.forEach(app => {
        const card = createApplicationCard(app);
        container.appendChild(card);
    });
}

function createApplicationCard(application) {
    const card = document.createElement('div');
    card.className = 'application-card bg-white rounded-lg shadow-md overflow-hidden hover:shadow-lg';
    
    const statusClass = getStatusClass(application.applicationStatus);
    const formattedDate = formatDate(application.applicationDate);
    const formattedAmount = formatCurrency(application.requestedLoanAmount);
    
    card.innerHTML = `
        <div class="p-6">
            <div class="flex justify-between items-start mb-4">
                <div>
                    <h3 class="text-lg font-semibold text-gray-800">Application #${application.id}</h3>
                    <p class="text-sm text-gray-500">${formattedDate}</p>
                </div>
                <span class="${statusClass}">${application.applicationStatus}</span>
            </div>
            
            <div class="mb-4">
                <p class="text-gray-600 mb-1"><i class="bi bi-person mr-2"></i>Applicant ID: ${application.id}</p>
                <p class="text-gray-600 mb-1"><i class="bi bi-house mr-2"></i>Property ID: ${application.propertyId}</p>
                <p class="text-gray-600"><i class="bi bi-cash-stack mr-2"></i>Loan Amount: ${formattedAmount}</p>
            </div>
            
            <div class="flex justify-between items-center mt-6">
                <div>
                    <p class="text-sm text-gray-500">Tenure: ${application.loanTenureYears} years</p>
                    <p class="text-sm text-gray-500">Rate: ${application.interestRate}%</p>
                </div>
                <a href="checkapplication.html?id=${application.id}" 
   class="view-btn bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-md transition-colors">
   View Application
</a>

            </div>
        </div>
    `;
    
    // Add click event to the button
    card.querySelector('.view-btn').addEventListener('click', () => {
        viewApplicationDetails(application.id);
    });
    
    return card;
}

function getStatusClass(status) {
    switch (status.toUpperCase()) {
        case 'PENDING':
            return 'status-badge status-pending';
        case 'APPROVED':
            return 'status-badge status-approved';
        case 'REJECTED':
            return 'status-badge status-rejected';
        default:
            return 'status-badge bg-gray-200 text-gray-800';
    }
}

function formatDate(dateString) {
    const options = { day: 'numeric', month: 'short', year: 'numeric' };
    return new Date(dateString).toLocaleDateString('en-GB', options);
}

function formatCurrency(amount) {
    return new Intl.NumberFormat('en-IN', {
        style: 'currency',
        currency: 'INR',
        maximumFractionDigits: 0
    }).format(amount);
}

function viewApplicationDetails(applicationId) {
    // Implement navigation to application details page
    console.log(`Viewing application ${applicationId}`);
    // window.location.href = `application-details.html?id=${applicationId}`;
}

function showError(message) {
    const container = document.getElementById('applications-container');
    container.innerHTML = `
        <div class="col-span-full text-center py-12">
            <i class="bi bi-exclamation-triangle text-4xl text-red-500 mb-4"></i>
            <p class="text-red-500">${message}</p>
            <button class="mt-4 px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700"
                    onclick="window.location.reload()">
                Retry
            </button>
        </div>
    `;
}

document.addEventListener('DOMContentLoaded', async () => {
    try {
        allApplications = await fetchAllApplications();

        allApplications.sort((a, b) => b.id - a.id);

        renderApplications(allApplications);
        setupFilterButtons();
    } catch (error) {
        showError('Failed to load applications. Please try again later.');
    }
});

