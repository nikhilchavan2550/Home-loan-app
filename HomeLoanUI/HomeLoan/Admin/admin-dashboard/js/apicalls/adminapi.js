export async function fetchCurrentAdmin() {
    try {
        const response = await fetch('http://localhost:8083/admin/current');
        if (!response.ok) throw new Error('Failed to fetch admin info');
        return await response.json();
    } catch (error) {
        console.error('Error fetching admin info:', error);
        return { adminName: 'Admin', adminId: null };
    }
}

