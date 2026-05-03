document.getElementById('loginForm').addEventListener('submit', function(e) {
    e.preventDefault();

    const correo = document.getElementById('correo').value;
    const password = document.getElementById('password').value;

    fetch('/auth/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ correo, password })
    })
    .then(res => {
        if (!res.ok) throw new Error("Login falló");
        return res.json();
    })
    .then(data => {
        localStorage.setItem('token', data.token);
        window.location.href = '/';
    })
    .catch(err => console.error(err));
});


document.getElementById("registerForm").addEventListener("submit", async function(e) {
    e.preventDefault();

    const data = {
        username: document.getElementById("username").value,
        correo: document.getElementById("correo").value,
        password: document.getElementById("password").value
    };

    const response = await fetch("/auth/register", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    });

    if (response.ok) {
        alert("Usuario creado correctamente");
        window.location.href = "/login";
    } else {
        alert("Error al registrar usuario");
    }
});