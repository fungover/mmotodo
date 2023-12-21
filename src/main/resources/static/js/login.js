let config  = {
    headers: {
        'X-CSRF-TOKEN': '',
    }
}

const csrf = async function (func = null) {
    try {
        const res = await fetch("/csrf");
        if (!res.ok) {
            throw new Error(`Failed to fetch CSRF token: ${res.status} ${res.statusText}`);
        }
        const data = await res.json();
        config = {
            headers: {
                'X-CSRF-TOKEN': data.token,
            }
        };
        if (func) func();
    } catch (error) {
        console.error(error);
    }
};

const userdata = async function () {
    try {
        await csrf();
        const res = await fetch('/api/user', {...config, method: "POST"});
        // Authenticated
        if (res.ok) {
            const data = await res.json();
            document.querySelector('#username').textContent = data.name;
            document.querySelector('.unauthenticated').style.display = 'none';
            document.querySelector('.authenticated').style.display = 'block';
            // Unauthenticated
        } else if (res.status === 401) {
            document.querySelector('#username').textContent = '';
            document.querySelector('.unauthenticated').style.display = 'block';
            document.querySelector('.authenticated').style.display = 'none';
        } else {
            console.error('Error fetching user data:', res.statusText);
        }
    } catch (error) {
        console.error('Error fetching user data:', error);
    }
};

csrf(userdata);

const logout = async function () {
    try {
        const response = await fetch('/logout', {...config, method: "POST"});

        if (response.ok) {
            document.querySelector('.authenticated').style.display = 'none';
            document.querySelector('.unauthenticated').style.display = 'block';
            await csrf();
        } else {
            console.error('Logout failed:', response.statusText);
        }
    } catch (error) {
        console.error('Error during logout:', error);
    }
};

document.getElementById('logout-button').addEventListener('click', logout);