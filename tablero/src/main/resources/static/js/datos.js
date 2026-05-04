async function cargarDatosPerfil() {
    const token = localStorage.getItem('token');
    if (!token) return;

    try {
        const response = await fetch('/auth/me', {
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + token,
                'Content-Type': 'application/json'
            }
        });

        if (response.ok) {
            const usuario = await response.json();
            actualizarInterfaz(usuario);
        }
    } catch (error) {
        console.error("Error al obtener datos del usuario:", error);
    }
}

async function verificarAdmin() {
    const token = localStorage.getItem('token');

    try {
        const response = await fetch('/auth/is-admin', {
            headers: { 'Authorization': `Bearer ${token}` }
        });

        const isAdmin = await response.json();

        if (isAdmin) {
            document.getElementById('seccionAdmin').style.display = 'block';
            console.log("Acceso de administrador confirmado");
        }
    } catch (error) {
        console.error("Error al verificar rol:", error);
    }
}

