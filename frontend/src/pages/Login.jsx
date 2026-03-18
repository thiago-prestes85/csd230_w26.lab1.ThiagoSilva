import { useState } from "react";
import axios from "axios";

function Login() {

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const handleLogin = () => {
        axios.post("http://localhost:8080/auth/login", {
            username,
            password
        })
            .then(res => {
                console.log(res.data);

                // salva token
                localStorage.setItem("token", res.data.token);

                alert("Login successful!");

                window.location.href = "/";
            })
            .catch(err => {
                console.error(err);
                alert("Login failed");
            });
    };

    return (
        <div>
            <h2>Login</h2>

            <input
                type="text"
                placeholder="Username"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
            />

            <input
                type="password"
                placeholder="Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
            />

            <button onClick={handleLogin}>
                Login
            </button>
        </div>
    );
}

export default Login;