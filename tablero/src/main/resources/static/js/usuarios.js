async function cargarUsuarios() {
    try {
        const [resUsers, resAdmins] = await Promise.all([
            fetch('http://localhost:8080/users'),
            fetch('http://localhost:8080/admin-users/ids')
        ]);

        const users = await resUsers.json();
        const admins = await resAdmins.json();

        dibujarUsuarios(users, admins);

    } catch (error) {
        console.error('Error:', error);
    }
}


function dibujarUsuarios(users, admins) {
    const tabla = document.getElementById('tablaUsuarios');
    tabla.innerHTML = '';

    users.forEach(user => {

        const esAdmin = admins.includes(user.id);

        const fila = `
            <tr>
                <td class="p-3">${user.nombre}</td>

                <td class="p-3">
                    <span class="badge ${esAdmin ? 'bg-dark' : 'bg-secondary'}">
                        ${esAdmin ? 'ADMIN' : 'USER'}
                    </span>
                </td>

                <td class="p-3 text-center">

                    <button onclick="cambiarRol(${user.id}, ${esAdmin})"
                        class="btn btn-profile-edit btn-sm">
                        CAMBIAR ROL
                    </button>

                    <button onclick="eliminarUsuario(${user.id})"
                        class="btn btn-danger btn-sm ms-2">
                        ELIMINAR
                    </button>

                </td>
            </tr>
        `;

        tabla.innerHTML += fila;
    });
}


function eliminarUsuario(id) {
    if (!confirm("¿Seguro que deseas eliminar este usuario?")) return;

    fetch(`http://localhost:8080/users/${id}`, {
        method: "DELETE"
    })
    .then(res => {
        if (!res.ok) throw new Error("Error al eliminar usuario");
        cargarUsuarios(); // recarga la tabla
    })
    .catch(err => console.error(err));
}