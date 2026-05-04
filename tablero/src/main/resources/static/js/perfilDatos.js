document.addEventListener('DOMContentLoaded', async () => {
    const token = localStorage.getItem('token');
    if (!token) return window.location.replace('/login');

    try {
        const response = await fetch('/auth/me', {
            headers: { 'Authorization': `Bearer ${token}` }
        });

        if (response.ok) {
            const user = await response.json();
            
            document.getElementById('nombreUsuario').textContent = user.nombre;

            const avatarDiv = document.getElementById('userAvatar');
            if (user.imgUrl) {
                avatarDiv.innerHTML = `<img src="${user.imgUrl}" style="width:100%; height:100%; object-fit:cover;">`;
            } else {
                document.getElementById('inicialUsuario').textContent = user.nombre.charAt(0).toUpperCase();
            }

            // cargarPublicacionesUsuario(user.id);

        } else {
            window.location.replace('/login');
        }
    } catch (error) {
        console.error("Error cargando perfil:", error);
    }
});
