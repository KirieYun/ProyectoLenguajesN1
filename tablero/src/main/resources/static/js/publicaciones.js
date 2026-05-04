function formatoFecha(fechaString) {
    if (!fechaString) return "";
    const fecha = new Date(fechaString);
    return fecha.toLocaleString('es-ES', {
        day: '2-digit',
        month: 'short',
        year: 'numeric',
        hour: '2-digit',
        minute: '2-digit',
        hour12: true
    });
}

function cargarPublicaciones() {
    fetch('/publicaciones')
        .then(response => {
            if (!response.ok) throw new Error("Error al cargar publicaciones");
            return response.json();
        })
        .then(data => {
            const list = document.getElementById('listaPublicaciones');
            list.innerHTML = '';
            dibujarPublicaciones(data);
        })
        .catch(error => console.error('Error:', error));
}

function cargarPublicacionesCategoria(id) {
    fetch(`/publicaciones/category/${id}`)
        .then(response => {
            if (!response.ok) throw new Error("Error");
            return response.json();
        })
        .then(data => {
            const list = document.getElementById('listaPublicaciones');
            list.innerHTML = '';
            dibujarPublicaciones(data);
        })
        .catch(error => console.error('Error:', error));
}

function dibujarPublicaciones(publicaciones) {
    const list = document.getElementById('listaPublicaciones');
    list.innerHTML = '';

    publicaciones.forEach(noticia => {

        const categoria = noticia.category ? noticia.category.nombre : "Sin categoría";
        const usuario = noticia.user ? noticia.user.nombre : "Anónimo";
        const inicial = noticia.user?.nombre?.substring(0, 1).toUpperCase() || "U";

        const fecha = formatoFecha(noticia.fechaPublicacion);

        const div = document.createElement('div');
        div.classList.add('publisUbication');

        const contenidoCard = `
        <div class="card border-0 shadow-sm rounded-4 overflow-hidden card-hover">
            
            ${noticia.imgUrl ? `
                <div class="position-relative">
                    <img src="${noticia.imgUrl}" class="card-img-top">
                    <span class="position-absolute top-0 end-0 m-3 badge bg-white text-dark rounded-pill shadow-sm">
                        ${categoria}
                    </span>
                </div>` : `
                <div class="p-3 pb-0">
                    <span class="badge bg-light text-dark rounded-pill">
                        ${categoria}
                    </span>
                </div>`
        }

            <div class="card-body p-4 d-flex flex-column">
                <h5 class="fw-bold mb-2">${noticia.titulo}</h5>
                <p class="text-secondary small mb-3" style="max-height: 60px; overflow: hidden;">
                    ${noticia.contenido}
                </p>

                <div class="d-flex align-items-center justify-content-between mt-auto pt-3 border-top">
                    <div class="d-flex align-items-center">
                        <div class="avatar-circle me-2" style="width:35px;height:35px;border-radius:50%;display:flex;align-items:center;justify-content:center;background:#6c757d;color:white;">
                            <span>${inicial}</span>
                        </div>
                        <div class="d-flex flex-column">
                            <small class="fw-bold text-dark">${usuario}</small>
                            <small class="text-muted">${fecha}</small>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        `;

        div.innerHTML = contenidoCard;
        list.appendChild(div);
    });
}