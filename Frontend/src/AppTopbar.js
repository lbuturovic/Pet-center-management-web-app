import React, { useContext }  from 'react';
import { Link, useNavigate } from 'react-router-dom';
import classNames from 'classnames';
import useLogout from './hooks/useLogout';
import AuthContext from './context/AuthProvider';

export const AppTopbar = (props) => {

    const logout = useLogout();
    const {setAuth} = useContext(AuthContext);
    const navigate = useNavigate();

    const logoutClick = (event) => {
        logout()
        navigate('/login')
    }

    return (
        <div className="layout-topbar">
            <button type="button" className="p-link  layout-menu-button layout-topbar-button" onClick={props.onToggleMenuClick}>
                <i className="pi pi-bars"/>
            </button>

            <button type="button" className="p-link layout-topbar-menu-button layout-topbar-button" onClick={props.onMobileTopbarMenuClick}>
                <i className="pi pi-ellipsis-v" />
            </button>

                <ul className={classNames("layout-topbar-menu lg:flex origin-top", {'layout-topbar-menu-mobile-active': props.mobileTopbarMenuActive })}>
                    <li>
                        <button className="p-link layout-topbar-button" onClick={logoutClick}>
                            
                            Logout
                        </button>
                    </li>
                </ul>
        </div>
    );
}
