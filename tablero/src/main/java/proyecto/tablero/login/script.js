document.getElementById('loginForm').addEventListener('submit', function(e) {
    e.preventDefault();
    
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    fetch('/api/auth/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ username, password })
    })
    .then(response => response.json())
    .then(data => {
        if (data.token) {
            // Guardar el token en localStorage
            localStorage.setItem('token', data.token);
            alert('Login exitoso');
            window.location.href = '/dashboard';
        }
    })
    .catch(error => console.error('Error:', error));
});