import React, {useEffect, useState} from 'react'
import "./LoginForm.css";
import axios from 'axios';
import Cookies from "js-cookie";
import {useNavigate} from 'react-router-dom';

const LoginForm = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const navigate = useNavigate();

    const [userLoggedIn, setUserLoggedIn] = useState(() => {
        const savedUserLoggedIn = localStorage.getItem("userLoggedIn");
        return savedUserLoggedIn !== null ? JSON.parse(savedUserLoggedIn) : false;
    });

    useEffect(() => {
        localStorage.setItem("userLoggedIn", JSON.stringify(userLoggedIn));
    }, [userLoggedIn]);


    const handleUsernameChange = (event) => {
        setUsername(event.target.value);
    }

    const handlePasswordChange = (event) => {
        setPassword(event.target.value);
    }

    const handleSubmit = async (event) => {
        event.preventDefault();

        const data = new URLSearchParams({
            client_id: "spring-cloud-client",
            client_secret: "3lTTXUx3PyEJNw7VBY3WpbJ01dxfL1ad",
            username: username,
            password: password,
            grant_type: 'password'
        });

        try {
            const response = await axios.post('http://localhost:8181/realms/spring-boot-microservices-realm/protocol/openid-connect/token', data.toString(), {
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            });
            Cookies.set("access_token", response.data.access_token);
            navigate(`/`);

        } catch (error) {
            console.error('Error:', error);
            setUserLoggedIn(false);
        }

    }


    return (
        <div className="test-background">
            <div className="login-form-container">
                <form className="login-form">
                    <div className="login-form-title">
                        <h2>User Login</h2>
                    </div>

                    <div className="login-form-labels">
                        <label>
                            <h5>Username</h5>
                            <input
                                type="text"
                                name="username"
                                onChange={handleUsernameChange}
                            />
                        </label>
                        <label>
                            <h5>Password</h5>
                            <input
                                type="password"
                                name="password"
                                onChange={handlePasswordChange}
                            />
                        </label>
                    </div>

                    <div className="login-form-button">
                        <button
                            type="submit"
                            onClick={(event) => {
                                handleSubmit(event);
                                setUserLoggedIn(true);
                            }}
                        >
                            Submit
                        </button>
                    </div>

                </form>
            </div>
        </div>
    )
}

export default LoginForm;