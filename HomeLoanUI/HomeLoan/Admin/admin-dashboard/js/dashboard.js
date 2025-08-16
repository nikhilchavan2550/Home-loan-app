document.addEventListener("DOMContentLoaded", async () => {
    const applicationsApi = "http://localhost:8083/api/admin/loan/all";  
    const personalApiBase = "http://localhost:8083/api/personal/";  

    const totalApplicationsEl = document.getElementById("totalApplications");
    const pendingApplicationsEl = document.getElementById("pendingApplications");
    const approvedApplicationsEl = document.getElementById("approvedApplications");
    const rejectedApplicationsEl = document.getElementById("rejectedApplications");
    const tableBody = document.getElementById("recentApplications");

    // Show loading state
    tableBody.innerHTML = `<tr><td colspan="4" class="text-center">Loading...</td></tr>`;

    try {
        const res = await fetch(applicationsApi);
        if (!res.ok) throw new Error(`Failed to fetch applications (${res.status})`);

        const applications = await res.json();
        
        if (!applications?.length) {
            tableBody.innerHTML = `<tr><td colspan="4" class="text-center text-muted">No applications found</td></tr>`;
            return;
        }

        // Update Stats
        const totalApplications = applications.length;
        const pendingApplications = applications.filter(app => app.applicationStatus?.trim().toLowerCase() === "pending").length;
        const approvedApplications = applications.filter(app => app.applicationStatus?.trim().toLowerCase() === "approved").length;
        const rejectedApplications = applications.filter(app => app.applicationStatus?.trim().toLowerCase() === "rejected").length;

        totalApplicationsEl.innerText = totalApplications;
        pendingApplicationsEl.innerText = pendingApplications;
        approvedApplicationsEl.innerText = approvedApplications;
        rejectedApplicationsEl.innerText = rejectedApplications;

        // Create charts
        createCharts(totalApplications, pendingApplications, approvedApplications, rejectedApplications);

        // Fetch personal details
        const enrichedApplications = await Promise.all(applications.map(async (app) => {
            try {
                const personalRes = await fetch(`${personalApiBase}${app.personalId}`);
                if (!personalRes.ok) throw new Error(`Personal API failed for ID ${app.personalId}`);
                const personalData = await personalRes.json();
                return {
                    ...app,
                    fullName: `${personalData.firstName || ""} ${personalData.lastName || ""}`.trim()
                };
            } catch (err) {
                console.error(`Error fetching personal data for ${app.personalId}:`, err);
                return { ...app, fullName: "Unknown" };
            }
        }));

        // Populate table
        tableBody.innerHTML = "";
        enrichedApplications.slice(0, 10).forEach(app => {
            const status = app.applicationStatus?.trim().toLowerCase();
            const statusBadgeClass =
                status === "approved" ? "bg-success" :
                status === "pending" ? "bg-warning" :
                status === "rejected" ? "bg-danger" :
                "bg-info";

            const row = document.createElement("tr");
            row.innerHTML = `
                <td>${app.id}</td>
                <td>${app.fullName}</td>
                <td>
                    <span class="badge ${statusBadgeClass}">
                        ${app.applicationStatus || "Unknown"}
                    </span>
                </td>
                <td>${app.applicationDate ? new Date(app.applicationDate).toLocaleDateString() : "N/A"}</td>
            `;

            row.style.cursor = "pointer";
            row.addEventListener("click", () => {
                document.getElementById("modalApplicantName").innerText = app.fullName;
                document.getElementById("modalAppId").innerText = app.id;
                document.getElementById("modalStatus").innerText = app.applicationStatus || "Unknown";
                document.getElementById("modalDate").innerText = app.applicationDate ? new Date(app.applicationDate).toLocaleDateString() : "N/A";
                document.getElementById("modalDocs").innerText = app.documentId || "N/A";
                document.getElementById("modalProperty").innerText = app.propertyId || "N/A";

                new bootstrap.Modal(document.getElementById("applicationModal")).show();
            });

            tableBody.appendChild(row);
        });

    } catch (err) {
        console.error("Error loading dashboard data:", err);
        tableBody.innerHTML = `<tr><td colspan="4" class="text-center text-danger">Error loading data</td></tr>`;
    }
});

function createCharts(total, pending, approved, rejected) {
    // Status Doughnut Chart
    const statusCtx = document.getElementById("statusChart").getContext("2d");
    new Chart(statusCtx, {
        type: "doughnut",
        data: {
            labels: ["Approved", "Pending", "Rejected"],
            datasets: [{
                label: "Loan Status",
                data: [approved, pending, rejected],
                backgroundColor: ["#22c55e", "#facc15", "#ef4444"],
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    position: "bottom",
                },
                title: {
                    display: true,
                    text: "Application Status Distribution",
                    font: {
                        size: 16
                    }
                }
            }
        }
    });

    // Applications Bar Chart
    const appsCtx = document.getElementById("applicationsChart").getContext("2d");
    new Chart(appsCtx, {
        type: "bar",
        data: {
            labels: ["Total", "Approved", "Pending", "Rejected"],
            datasets: [{
                label: "Applications",
                data: [total, approved, pending, rejected],
                backgroundColor: ["#3b82f6", "#22c55e", "#facc15", "#ef4444"],
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    display: false
                },
                title: {
                    display: true,
                    text: "Applications Overview",
                    font: {
                        size: 16
                    }
                }
            },
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
}