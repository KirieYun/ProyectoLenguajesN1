

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
            fetch('http://localhost:8080/publicaciones')
                .then(response => response.json())
                .then(data => {
                    console.log("DATA:", data);
                    const list = document.getElementById('listaPublicaciones');
                    list.innerHTML = '';
                    dibujarPublicaciones(data);
                })
                .catch(error => console.error('Error al cargar publicaciones:', error));
        }

        function cargarPublicacionesCategoria(id) {
            fetch(`http://localhost:8080/publicaciones/category/${id}`)
                .then(response => response.json())
                .then(data => {
                    console.log("DATA por categoría:", data);
                    const list = document.getElementById('listaPublicaciones');
                    list.innerHTML = '';
                    dibujarPublicaciones(data);
                })
                .catch(error => console.error('Error al cargar publicaciones por categoría:', error));
        }

        function dibujarPublicaciones(publicaciones) {
            const list = document.getElementById('listaPublicaciones');
            list.innerHTML = '';
            publicaciones.forEach(noticia => {
                        const div = document.createElement('div');
                        div.classList.add('publisUbication');

                        const fecha = formatoFecha(noticia.fechaPublicacion);
                        const contenidoCard = `
                        <div class="card border-0 shadow-sm rounded-4 overflow-hidden card-hover">
                            ${noticia.imgUrl ? `
                                <div class="position-relative">
                                    <img src="${noticia.imgUrl}" class="card-img-top" >
                                    <span class="position-absolute top-0 end-0 m-3 badge bg-white text-dark rounded-pill shadow-sm">
                                        ${noticia.category.nombre}
                                    </span>
                                </div>` : `
                                <div class="p-3 pb-0">
                                    <span class="badge bg-light text-dark rounded-pill">
                                        ${noticia.category.nombre}
                                    </span>
                                </div>`
                                            }
                            <div class="card-body p-4 d-flex flex-column">
                                <h5 class="fw-bold mb-2">${noticia.titulo}</h5>
                                <p class="text-secondary small mb-3" style="max-height: 60px; overflow: hidden;">
                                    ${noticia.contenido}
                                </p>
                                <a href="#" class="ver-mas text-primary small fw-bold mb-4">Leer más</a>
                                
                                <div class="d-flex align-items-center justify-content-between mt-auto pt-3 border-top">
                                    <div class="d-flex align-items-center">
                                        <div class="avatar-circle me-2" style="width: 35px; height: 35px; border-radius: 50%; overflow: hidden; display: flex; align-items: center; justify-content: center; background: #6c757d; color: white;">
                                            ${noticia.user.imgUrl ?
                                                `<img src="${noticia.user.imgUrl}" style="width: 100%; height: 100%; object-fit: cover;">` :
                                                `<span>${noticia.user.nombre.substring(0, 1).toUpperCase()}</span>`
                                            }
                                        </div>
                                        <div class="d-flex flex-column">
                                            <small class="fw-bold text-dark" style="line-height: 1.2;">${noticia.user.nombre}</small>
                                            <small class="text-muted" style="font-size: 0.7rem;">${fecha}</small>
                                        </div>
                                    </div>
                                    <i class="bi bi-bookmark-plus fs-5 text-muted" style="cursor: pointer;"></i>
                                </div>
                            </div>
                        </div>
                     `;

                        div.innerHTML = contenidoCard;
                        list.appendChild(div);
                    });
        }

        