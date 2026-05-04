function formatoFecha(fechaString) {
    if (!fechaString) return "";
    const fecha = new Date(fechaString);
    return fecha.toLocaleString('es-ES', { 
        day: '2-digit', month: 'short', year: 'numeric', 
        hour: '2-digit', minute: '2-digit', hour12: true 
    });
}

async function cargarMisPublicaciones() {
    const token = localStorage.getItem('token');
    if (!token) return;

    try {
        // 1. Obtener mi usuario (para saber mi ID)
        const resUser = await fetch('/auth/me', {
            headers: { 'Authorization': 'Bearer ' + token }
        });
        if (!resUser.ok) throw new Error('Error al obtener perfil');
        const usuarioActual = await resUser.json();

        // 2. Obtener TODAS las publicaciones (usando tu endpoint funcional)
        const resPubs = await fetch('http://localhost:8080/publicaciones', {
            headers: { 'Authorization': 'Bearer ' + token }
        });
        if (!resPubs.ok) throw new Error('Error al cargar publicaciones');
        const todasLasPublicaciones = await resPubs.json();

        // 3. Filtrar: solo las que mi ID coincida con el ID del usuario de la noticia
        const misPublicaciones = todasLasPublicaciones.filter(p => p.user && p.user.id === usuarioActual.id);

        dibujarMisPublicaciones(misPublicaciones);

    } catch (error) {
        console.error('Error en el proceso:', error);
    }
}

function dibujarMisPublicaciones(publicaciones) {
    const list = document.getElementById('listaMisPublicaciones');
    list.innerHTML = '';

    if (publicaciones.length === 0) {
        list.innerHTML = '<div class="col-12 text-center"><p class="text-muted">No tienes publicaciones aún.</p></div>';
        return;
    }

    publicaciones.forEach(noticia => {
        const categoria = noticia.category ? noticia.category.nombre : "Sin categoría";
        const usuario = noticia.user ? noticia.user.nombre : "Anónimo";
        const inicial = noticia.user?.nombre?.substring(0, 1).toUpperCase() || "U";
        const fecha = formatoFecha(noticia.fechaPublicacion);

        const div = document.createElement('div');
        
        div.classList.add('col-12', 'col-md-6', 'col-lg-4', 'mb-4'); 

        div.innerHTML = `
            <div class="card border-0 shadow-sm rounded-4 overflow-hidden h-100 card-hover">
                ${noticia.imgUrl ? `
                <div class="position-relative">
                    <img src="${noticia.imgUrl}" class="card-img-top" style="height: 180px; object-fit: cover;">
                    <span class="position-absolute top-0 end-0 m-3 badge bg-white text-dark rounded-pill shadow-sm">
                        ${categoria}
                    </span>
                </div>` : `
                <div class="p-3 pb-0">
                    <span class="badge bg-light text-dark rounded-pill">${categoria}</span>
                </div>`}
                
                <div class="card-body p-4 d-flex flex-column">
                    <div class="d-flex justify-content-between align-items-start mb-2">
                        <h5 class="fw-bold mb-0" style="font-size: 1.1rem; line-height: 1.2;">${noticia.titulo}</h5>
                        
                        <div class="dropdown">
                            <i class="bi bi-three-dots-vertical" data-bs-toggle="dropdown" style="cursor: pointer;"></i>
                            <ul class="dropdown-menu dropdown-menu-end shadow border-0 rounded-3">
                                <li>
                                    <a class="dropdown-item py-2" href="/publicaciones/editar/${noticia.id}">
                                        <i class="bi bi-pencil me-2"></i> Editar
                                    </a>
                                </li>
                                <li>
                                    <a class="dropdown-item py-2 text-danger" href="#" onclick="eliminarPublicacion(event, ${noticia.id})">
                                        <i class="bi bi-trash me-2"></i> Eliminar
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>

                    <p class="text-secondary small mb-3" style="display: -webkit-box; -webkit-line-clamp: 3; -webkit-box-orient: vertical; overflow: hidden; height: 60px;">
                        ${noticia.contenido}
                    </p>

                    <div class="d-flex align-items-center justify-content-between mt-auto pt-3 border-top">
                        <div class="d-flex align-items-center">
                            <div class="avatar-circle me-2" style="width:30px;height:30px;border-radius:50%;display:flex;align-items:center;justify-content:center;background:#6c757d;color:white;font-size:0.8rem;">
                                <span>${inicial}</span>
                            </div>
                            <div class="d-flex flex-column">
                                <small class="fw-bold text-dark" style="font-size: 0.8rem;">${usuario}</small>
                                <small class="text-muted" style="font-size: 0.7rem;">${fecha}</small>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        `;
        list.appendChild(div);
    });
}

