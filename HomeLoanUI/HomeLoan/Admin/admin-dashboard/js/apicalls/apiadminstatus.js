export async function updateAdminStatus({ applicationId, newStatus, adminId }) {
    try {
        const response = await fetch('http://localhost:8083/admin/status/update', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                applicationId,
                newStatus,
                adminId
            })
        });
        if (!response.ok) {
            const errorData = await response.text();
            throw new Error(errorData || 'Failed to update status');
        }
        return await response.text();
    } catch (error) {
        console.error('Error updating admin status:', error);
        throw error;
    }
}