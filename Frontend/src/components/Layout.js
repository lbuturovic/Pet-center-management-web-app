import React from 'react';
import { useLocation, Navigate, Outlet } from "react-router-dom";
const Layout = () => {
    return (
        <main>
            <Outlet/>
        </main>
    )
}

export default Layout