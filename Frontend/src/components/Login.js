import React, { useState, useRef, useEffect } from 'react';
import useAuth from '../hooks/useAuth';
import { useLocation, useNavigate } from 'react-router-dom';
import { Button } from 'primereact/button';
import { InputText } from 'primereact/inputtext';
import classNames from 'classnames';
import PrimeReact from 'primereact/api';
import { Tooltip } from 'primereact/tooltip';

import axios from "../api/axios";

import 'primereact/resources/primereact.css';
import 'primeicons/primeicons.css';
import 'primeflex/primeflex.css';
import 'prismjs/themes/prism-coy.css';
import '../assets/demo/flags/flags.css';
import '../assets/demo/Demos.scss';
import '../assets/layout/layout.scss';
import '../App.scss';
import useLogout from '../hooks/useLogout';

const LOGIN_URL = '/user/login'




const Login = () => {

    const [layoutMode] = useState('static');
    const [layoutColorMode] = useState('light')
    const [inputStyle] = useState('outlined');
    const [ripple] = useState(true);
    const copyTooltipRef = useRef();
    const logout = useLogout();


    PrimeReact.ripple = true;
    const wrapperClass = classNames('layout-wrapper', {
        'layout-overlay': layoutMode === 'overlay',
        'layout-static': layoutMode === 'static',
        'p-input-filled': inputStyle === 'filled',
        'p-ripple-disabled': ripple === false,
        'layout-theme-light': layoutColorMode === 'light'
    });

    const { setAuth } = useAuth();

    const navigate = useNavigate();
    const location = useLocation();


    const userRef = useRef();
    const errRef = useRef();

    const [user, setUser] = useState('');
    const [pwd, setPwd] = useState('');
    const [errMsg, setErrMsg] = useState('');

    useEffect(() => {
        userRef.current.focus();
    }, [])

    useEffect(() => {
        setErrMsg('');
    }, [])

    const handleSubmit = async (e) => {
        e.preventDefault();
        //logout();
        try {
            const response = await axios.post(LOGIN_URL,
                JSON.stringify({ username: user, password: pwd }),
                {
                    headers: { 'Content-Type': 'application/json' }
                }
            );
            const token = JSON.stringify(response?.data);
            var base64Url = token.split('.')[1];
            var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
            var jsonPayload = decodeURIComponent(atob(base64).split('').map(function (c) {
                return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
            }).join(''));
            const accessToken = response?.data?.accessToken;
            const uuid = response?.data?.uuid;
            const roles = JSON.parse(jsonPayload).role
            localStorage.setItem('refreshToken', response?.data?.refreshToken)
            setAuth({ user, pwd, roles, accessToken, uuid });
            setUser('');
            setPwd('');
            let route = "";
            console.log(roles[0])
            if (roles && roles[0] === "ROLE_ADMIN")
                route = "admin"
            else if (roles && roles[0] === "ROLE_EMPLOYEE")
                route = "employee"
            else if (roles && roles[0] === "ROLE_USER")
                route = "user"
            let from = "/" + route;

            navigate(from, { replace: true });
        } catch (err) {
            if (!err?.response) {
                setErrMsg('No Server Response!');
            } else if (err.response?.status === 400) {
                setErrMsg('Missing Username or Password!');
            } else if (err.response?.status === 401) {
                setErrMsg('Unauthorized');
            } else {
                setErrMsg('Login Failed');
            }
            errRef.current.focus();
        }
    };



    const styles = {

        marginLeft: '15%',
        marginRight: 'auto',
        marginTop: '10%'
    };

    const styles2 = {

        marginLeft: '10%',
        marginRight: '10%'
    };

    const styles3 = {
        marginLeft: '40%',
        marginRight: 'auto'
    };

    const renderForm = (
        <div className="layout-main-container">
            <div className="layout-main">
                <div className="surface-card p-4 shadow-2 border-round w-50 lg:w-4" style={styles}>
                    <div className="text-center mb-5">
                        <h1 >Login</h1>
                        <span className="text-600 font-medium line-height-3">Don't have an account?</span>
                        <a href="/register" className="font-medium no-underline ml-2 text-blue-500 cursor-pointer">Register</a>
                    </div>
                    <div style={styles2}>
                        <label htmlFor="username" className="block text-xl font-medium mb-2">Username</label>
                        <InputText
                            id="username"
                            type="text"
                            name="username"
                            className="w-full mb-4"
                            ref={userRef}
                            autoComplete="off"
                            onChange={(e) => setUser(e.target.value)}
                            value={user}
                            required
                        />
                        <label htmlFor="password" className="block text-xl font-medium mb-2">Password</label>
                        <InputText
                            id="password"
                            type="password"
                            name="pass"
                            className="w-full mb-3"
                            autoComplete='off'
                            onChange={(e) => setPwd(e.target.value)}
                            value={pwd}
                            required
                        />
                        <a href="/add-report" className="font-medium no-underline ml-2 text-blue-500 cursor-pointer">Continue without an account.</a>
                        <div
                            ref={errRef}
                            className={errMsg ? "error" : "offscreen"}
                            aria-live="assertive">
                            {errMsg}
                        </div>
                        <div className="flex align-items-center justify-content-between mb-6">
                            <div className="flex align-items-center">
                            </div>
                        </div>
                        <Button label="Sign In" icon="pi pi-user" style={styles3} onClick={handleSubmit} />
                    </div>
                </div>
            </div>
        </div>)

    return (
        <div className={wrapperClass}>
            <Tooltip ref={copyTooltipRef} target=".block-action-copy" position="bottom" content="Copied to clipboard" event="focus" />
            {renderForm}
        </div>
    );
}

const comparisonFn = function (prevProps, nextProps) {
    return prevProps.location.pathname === nextProps.location.pathname;
};

export default React.memo(Login, comparisonFn);