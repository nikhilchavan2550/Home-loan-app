// Main JavaScript for the Loan Application Dashboard

// API Base URL - Update this to match your backend API URL
const API_BASE_URL = '/api';

// DOM Elements
const sidebar = document.getElementById('sidebar');
const content = document.getElementById('content');
const sidebarToggle = document.getElementById('sidebarCollapse');

// Toggle sidebar on mobile
if (sidebarToggle) {
    sidebarToggle.addEventListener('click', () => {
        sidebar.classList.toggle('active');
        content.classList.toggle('active');
    });
}

// Close sidebar when clicking outside on mobile
document.addEventListener('click', (e) => {
    if (window.innerWidth <= 768 && !sidebar.contains(e.target) && e.target !== sidebarToggle) {
        sidebar.classList.remove('active');
        content.classList.remove('active');
    }
});

// Helper function to make API requests
async function fetchData(endpoint, options = {}) {
    try {
        const response = await fetch(`${API_BASE_URL}${endpoint}`, {
            headers: {
                'Content-Type': 'application/json',
                ...options.headers
            },
            ...options
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        return await response.json();
    } catch (error) {
        console.error('Error fetching data:', error);
        showAlert('error', 'Failed to fetch data. Please try again later.');
        throw error;
    }
}

// Show alert message
function showAlert(type, message) {
    const alertDiv = document.createElement('div');
    alertDiv.className = `alert alert-${type} alert-dismissible fade show`;
    alertDiv.role = 'alert';
    alertDiv.innerHTML = `
        ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    `;
    
    const container = document.querySelector('.container-fluid');
    if (container) {
        container.insertBefore(alertDiv, container.firstChild);
        
        // Auto-remove alert after 5 seconds
        setTimeout(() => {
            const bsAlert = new bootstrap.Alert(alertDiv);
            bsAlert.close();
        }, 5000);
    }
}

function formatDate(dateString) {
    if (!dateString) return '';
    const date = new Date(dateString);
    return date.toISOString().split('T')[0];
}

// Format currency
function formatCurrency(amount) {
    return new Intl.NumberFormat('en-US', {
        style: 'currency',
        currency: 'USD',
        minimumFractionDigits: 0,
        maximumFractionDigits: 0
    }).format(amount);
}

// Get status badge class
function getStatusBadgeClass(status) {
    const statusClasses = {
        'APPROVED': 'success',
        'PENDING': 'warning',
        'IN_REVIEW': 'info',
        'REJECTED': 'danger',
        'SUBMITTED': 'primary',
        'PROCESSING': 'info',
        'COMPLETED': 'success',
        'CANCELLED': 'secondary'
    };
    
    return statusClasses[status] || 'secondary';
}

// Initialize dashboard
async function initDashboard() {
    try {
        // Fetch dashboard stats
        // const stats = await fetchData('/dashboard/stats');
        // document.getElementById('totalApplications').textContent = stats.totalApplications || 0;
        // document.getElementById('approvedLoans').textContent = stats.approvedLoans || 0;
        // document.getElementById('pendingDocuments').textContent = stats.pendingDocuments || 0;
        
        // For now, using dummy data
        document.getElementById('totalApplications').textContent = '5';
        document.getElementById('approvedLoans').textContent = '2';
        document.getElementById('pendingDocuments').textContent = '3';
        
        // Load recent applications
        await loadRecentApplications();
        
        // Initialize any charts if needed
        initCharts();
        
    } catch (error) {
        console.error('Error initializing dashboard:', error);
    }
}

// Load recent applications
async function loadRecentApplications() {
    try {
        // const applications = await fetchData('/applications/recent');
        
        // Dummy data - replace with actual API call
        const applications = [
            { id: 'LA-1001', amount: 250000, status: 'APPROVED', applicationDate: '2025-07-28' },
            { id: 'LA-1002', amount: 180000, status: 'PENDING', applicationDate: '2025-07-29' },
            { id: 'LA-1003', amount: 320000, status: 'IN_REVIEW', applicationDate: '2025-07-30' },
            { id: 'LA-1004', amount: 150000, status: 'REJECTED', applicationDate: '2025-07-25' },
            { id: 'LA-1005', amount: 275000, status: 'APPROVED', applicationDate: '2025-07-20' }
        ];
        
        const tbody = document.querySelector('#recentApplicationsTable tbody');
        if (!tbody) return;
        
        tbody.innerHTML = ''; // Clear existing rows
        
        applications.forEach(app => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${app.id}</td>
                <td>${formatCurrency(app.amount)}</td>
                <td><span class="badge bg-${getStatusBadgeClass(app.status)}">${app.status.replace('_', ' ')}</span></td>
                <td>${formatDate(app.applicationDate)}</td>
                <td>
                    <a href="pages/loan-application.html?id=${app.id}" class="btn btn-sm btn-outline-primary">View</a>
                </td>
            `;
            tbody.appendChild(row);
        });
        
    } catch (error) {
        console.error('Error loading recent applications:', error);
        showAlert('error', 'Failed to load recent applications.');
    }
}

// Initialize charts
function initCharts() {
    // Example chart - you can add more charts as needed
    const ctx = document.getElementById('applicationsChart');
    if (ctx) {
        new Chart(ctx, {
            type: 'bar',
            data: {
                labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun'],
                datasets: [{
                    label: 'Loan Applications',
                    data: [12, 19, 3, 5, 2, 3],
                    backgroundColor: 'rgba(78, 115, 223, 0.8)',
                    borderColor: 'rgba(78, 115, 223, 1)',
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        display: false
                    },
                    title: {
                        display: true,
                        text: 'Monthly Loan Applications',
                        font: {
                            size: 16
                        }
                    }
                },
                scales: {
                    y: {
                        beginAtZero: true,
                        ticks: {
                            precision: 0
                        }
                    }
                }
            }
        });
    }
}

// Initialize the dashboard when the DOM is fully loaded
document.addEventListener('DOMContentLoaded', () => {
    // Initialize tooltips
    const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });
    
    // Initialize popovers
    const popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'));
    popoverTriggerList.map(function (popoverTriggerEl) {
        return new bootstrap.Popover(popoverTriggerEl);
    });
    
    // Initialize dashboard if on dashboard page
    if (document.getElementById('recentApplicationsTable')) {
        initDashboard();
    }
});
