import React, { useState, useEffect, useRef } from 'react';
import classNames from 'classnames';
import { Route, Routes, useLocation } from 'react-router-dom';
import { CSSTransition } from 'react-transition-group';
import { AppTopbar } from './AppTopbar';
import { AppMenu } from './AppMenu';
import { AppConfig } from './AppConfig';
import Layout from './components/Layout';
import RequireAuth from './components/RequireAuth';
import Unauthorized from './components/Unauthorized';
import UserHome from './components/UserHome';
import EmployeeHome from './components/EmployeeHome';
import AdminHome from './components/AdminHome';
import Missing from './components/Missing';
import PersistLogin from './components/PersistLogin';
import Login from './components/Login';
import PrimeReact from 'primereact/api';
import { Tooltip } from 'primereact/tooltip';
import 'primereact/resources/primereact.css';
import 'primeicons/primeicons.css';
import 'primeflex/primeflex.css';
import 'prismjs/themes/prism-coy.css';
import './assets/demo/flags/flags.css';
import './assets/demo/Demos.scss';
import './assets/layout/layout.scss';
import './App.scss';
import AllPets from './components/AllPets';
import AddPet from './components/AddPet';
import Register from './components/Register';
import Users from './components/Users';
import AddEmployee from './components/AddEmployee';
import EditEmployee from './components/EditEmployee';
import AddService from './components/AddService';
import useAuth from "./hooks/useAuth";
import CreateCenter from './components/CreateCenter';
import EditCenter from './components/EditCenter';
import Centers from './components/Centers';
import DesiredPet from './components/DesiredPet';
import EditPet from './components/EditPet';
import AbandonedPets from './components/AbandonedPets';
import ViewPet from './components/ViewPet';
import AllReservations from './components/AllReservations';
import Reservation from './components/Reservation';
import ReportPet from './components/ReportPet';
import ReportActivity from './components/ReportActivity';
import ActiveReports from './components/ActiveReports';
import AllServices from './components/AllServices';

const App = (props) => {
    const [layoutMode, setLayoutMode] = useState('static');
    const [layoutColorMode, setLayoutColorMode] = useState('light')
    const [inputStyle, setInputStyle] = useState('outlined');
    const [ripple, setRipple] = useState(true);
    const [staticMenuInactive, setStaticMenuInactive] = useState(false);
    const [overlayMenuActive, setOverlayMenuActive] = useState(false);
    const [mobileMenuActive, setMobileMenuActive] = useState(false);
    const [mobileTopbarMenuActive, setMobileTopbarMenuActive] = useState(false);
    const copyTooltipRef = useRef();
    const location = useLocation();
    const { auth } = useAuth();
  
    const [hideMenu, setHideMenu] = useState(true);

    PrimeReact.ripple = true;

    let menuClick = false;
    let mobileTopbarMenuClick = false;

    useEffect(() => {
        if (mobileMenuActive) {
            addClass(document.body, "body-overflow-hidden");
        } else {
            removeClass(document.body, "body-overflow-hidden");
        }
    }, [mobileMenuActive]);

    useEffect(() => {
        copyTooltipRef && copyTooltipRef.current && copyTooltipRef.current.updateTargetEvents();
    }, [location]);

    const onInputStyleChange = (inputStyle) => {
        setInputStyle(inputStyle);
    }

    const onRipple = (e) => {
        PrimeReact.ripple = e.value;
        setRipple(e.value)
    }

    const onLayoutModeChange = (mode) => {
        setLayoutMode(mode)
    }

    const onColorModeChange = (mode) => {
        setLayoutColorMode(mode)
    }

    const onWrapperClick = (event) => {
        if (!menuClick) {
            setOverlayMenuActive(false);
            setMobileMenuActive(false);
        }

        if (!mobileTopbarMenuClick) {
            setMobileTopbarMenuActive(false);
        }

        mobileTopbarMenuClick = false;
        menuClick = false;
    }

    const onToggleMenuClick = (event) => {
        menuClick = true;

        if (isDesktop()) {
            if (layoutMode === 'overlay') {
                if (mobileMenuActive === true) {
                    setOverlayMenuActive(true);
                }

                setOverlayMenuActive((prevState) => !prevState);
                setMobileMenuActive(false);
            }
            else if (layoutMode === 'static') {
                setStaticMenuInactive((prevState) => !prevState);
            }
        }
        else {
            setMobileMenuActive((prevState) => !prevState);
        }

        event.preventDefault();
    }

    const onSidebarClick = () => {
        menuClick = true;
    }

    const onMobileTopbarMenuClick = (event) => {
        mobileTopbarMenuClick = true;

        setMobileTopbarMenuActive((prevState) => !prevState);
        event.preventDefault();
    }

    const onMobileSubTopbarMenuClick = (event) => {
        mobileTopbarMenuClick = true;

        event.preventDefault();
    }

    const onMenuItemClick = (event) => {
        if (!event.item.items) {
            setOverlayMenuActive(false);
            setMobileMenuActive(false);
        }
    }
    const isDesktop = () => {
        return window.innerWidth >= 992;
    }

    const menuItemsAdmin = [
        {
            label: 'Users',
            items: [
                {
                    label: 'New',
                    icon: 'pi pi-fw pi-plus',
                    to: '/add-employee'
                },
                {
                    label: 'Show',
                    icon: 'pi pi-fw pi-list',
                    to: '/all-users'
                }
            ]
        },
        {
            label: 'Centers',
            items: [
                {
                    label: 'New',
                    icon: 'pi pi-fw pi-plus',
                    to: '/add-center'
                },
                {
                    label: 'Show',
                    icon: 'pi pi-fw pi-list',
                    to: '/all-centers'
                }
            ]
        },
        {
            label: 'Services',
            items: [
                {
                    label: 'New',
                    icon: 'pi pi-fw pi-plus',
                    to: '/add-service'
                },
                {
                    label: 'Show',
                    icon: 'pi pi-fw pi-list',
                    to: '/all-services'
                }
            ]
        }
    ];

    const menuItemsEmployee = [
        {
            label: 'Pets',
            items: [
                {
                    label: 'New',
                    icon: 'pi pi-fw pi-plus',
                    to: '/add-pet'
                },
                {
                    label: 'Show',
                    icon: 'pi pi-fw pi-list',
                    to: '/all-pets'
                }
            ]
        },
        {
            label: 'Reservations',
            items: [
                {
                    label: 'Show',
                    icon: 'pi pi-fw pi-list',
                    to: '/all-reservations'
                }
            ]
        },
        {
            label: 'Reports',
            items: [
                {
                    label: 'Show',
                    icon: 'pi pi-fw pi-list',
                    to: '/reports'
                }
            ]
        }
    ];

    const menuItemsUser = [
        {
            label: 'Pets',
            items: [
                {
                    label: 'Show',
                    icon: 'pi pi-fw pi-list',
                    to: '/abandoned-pets'
                }
            ]
        },
        {
            label: 'Reservations',
            items: [
                {
                    label: 'New',
                    icon: 'pi pi-fw pi-plus',
                    to: '/add-reservation'
                }
            ]
        },
        {
            label: 'Report',
            items: [
                {
                    label: 'New',
                    icon: 'pi pi-fw pi-plus',
                    to: '/add-report'
                }
            ]
        },
        {
            label: 'Desired pet',
            items: [
                {
                    label: 'New',
                    icon: 'pi pi-fw pi-plus',
                    to: '/add-desired-pet'
                }
            ]
        }
    ];

    const menuItemsUnauthorized = [
        {

            label: 'Report',
            items: [
                {
                    label: 'New',
                    icon: 'pi pi-fw pi-plus',
                    to: '/add-report'
                }
            ]
        },
        {
            label: 'Report activity',
            items: [
                {
                    label: 'Show',
                    icon: 'pi pi-fw pi-list',
                    to: '/report'
                }
            ]
        }
    ];


    const addClass = (element, className) => {
        if (element.classList)
            element.classList.add(className);
        else
            element.className += ' ' + className;
    }

    const removeClass = (element, className) => {
        if (element.classList)
            element.classList.remove(className);
        else
            element.className = element.className.replace(new RegExp('(^|\\b)' + className.split(' ').join('|') + '(\\b|$)', 'gi'), ' ');
    }

    const wrapperClass = classNames('layout-wrapper', {
        'layout-overlay': layoutMode === 'overlay',
        'layout-static': layoutMode === 'static',
        'layout-static-sidebar-inactive': staticMenuInactive && layoutMode === 'static',
        'layout-overlay-sidebar-active': overlayMenuActive && layoutMode === 'overlay',
        'layout-mobile-sidebar-active': mobileMenuActive,
        'p-input-filled': inputStyle === 'filled',
        'p-ripple-disabled': ripple === false,
        'layout-theme-light': layoutColorMode === 'light'
    });

    return (

      <div className={wrapperClass} onClick={onWrapperClick}>
       {(location.pathname !== '/login' && location.pathname !== '/' && location.pathname !== '/register' && location.pathname !== '/add-report') && <div>
      <Tooltip ref={copyTooltipRef} target=".block-action-copy" position="bottom" content="Copied to clipboard" event="focus" />

     <AppTopbar  onToggleMenuClick={onToggleMenuClick} layoutColorMode={layoutColorMode}
          mobileTopbarMenuActive={mobileTopbarMenuActive} onMobileTopbarMenuClick={onMobileTopbarMenuClick} onMobileSubTopbarMenuClick={onMobileSubTopbarMenuClick} />
     {hideMenu &&  <div className="layout-sidebar" onClick={onSidebarClick}>
        {auth?.roles == 'ROLE_ADMIN' && <AppMenu model={menuItemsAdmin} onMenuItemClick={onMenuItemClick} layoutColorMode={layoutColorMode}  />}
        {auth?.roles == 'ROLE_EMPLOYEE' && <AppMenu model={menuItemsEmployee} onMenuItemClick={onMenuItemClick} layoutColorMode={layoutColorMode}  />}
        {auth?.roles == 'ROLE_USER' && <AppMenu model={menuItemsUser} onMenuItemClick={onMenuItemClick} layoutColorMode={layoutColorMode}  />}
        {auth?.roles == null && <AppMenu model={menuItemsUnauthorized} onMenuItemClick={onMenuItemClick} layoutColorMode={layoutColorMode}  />}
      
      </div>}
      </div>
    }
            

        <div className="layout-main-container">
            <div className="layout-main">
                <Routes>
                    <Route path = "/" element = {<Layout />}>
                    <Route path="login" element={<Login />} />
                    <Route path="unauthorized" element={<Unauthorized />} />
                    <Route path="register" element={<Register />} />
                    <Route path="/add-report" element={<ReportPet/>}/>
                    <Route path="/report" element={<ReportActivity/>}/>

                <Route element={<PersistLogin />}>
                {/*protected Routes USER*/}
                <Route element={<RequireAuth allowedRoles={["ROLE_USER"]} />}>
                    <Route path="/user" element={<UserHome />} />
                    <Route path="/add-desired-pet" element={<DesiredPet />} />
                    <Route path="/abandoned-pets" element={<AbandonedPets />} />
                    <Route path="/view-pet" element={<ViewPet />} />
                    <Route path="/add-reservation" element={<Reservation />} />
                </Route>

                {/*protected Routes EMPLOYEE*/}
                <Route element={<RequireAuth allowedRoles={["ROLE_EMPLOYEE"]} />}>
                <Route path="/employee" element={<EmployeeHome />} />
                <Route path="/all-pets" element={<AllPets />} />
                <Route path="/add-pet" element={<AddPet />} /> 
                <Route path="/edit-pet" element={<EditPet />} /> 
                <Route path="/all-reservations" element={<AllReservations />} />
                <Route path="/reports" element={<ActiveReports/>}/>
                </Route>

                {/*protected Routes ADMIN*/}
                <Route element={<RequireAuth allowedRoles={["ROLE_ADMIN"]} />}>
                    <Route path="/admin" element={<AdminHome />} />
                    <Route path="/all-users" element={<Users />} />
                    <Route path="/add-employee" element={<AddEmployee />} />
                    <Route path="/edit-user" element={<EditEmployee/>}/>
                    <Route path="/add-service" element={<AddService/>}/>
                    <Route path="/add-center" element={<CreateCenter/>}/>
                    <Route path="/edit-center" element={<EditCenter/>}/>
                    <Route path="/all-centers" element={<Centers/>}/>
                    <Route path="/all-services" element={<AllServices/>}/>
                </Route>

                </Route>
                {/* catch all */}
                <Route path="*" element={<Missing />} />
            </Route>
      </Routes>
                </div>
            </div>

            <AppConfig rippleEffect={ripple} onRippleEffect={onRipple} inputStyle={inputStyle} onInputStyleChange={onInputStyleChange}
                layoutMode={layoutMode} onLayoutModeChange={onLayoutModeChange} layoutColorMode={layoutColorMode} onColorModeChange={onColorModeChange} />

            <CSSTransition classNames="layout-mask" timeout={{ enter: 200, exit: 200 }} in={mobileMenuActive} unmountOnExit>
                <div className="layout-mask p-component-overlay"></div>
            </CSSTransition>

        </div>

        
    );

}

export default App;