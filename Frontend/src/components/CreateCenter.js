import React, { useState, useRef } from 'react';
import { InputText } from 'primereact/inputtext';
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import { Dialog } from 'primereact/dialog';
import { Button } from 'primereact/button';
import { RadioButton } from 'primereact/radiobutton';
import { useNavigate } from 'react-router-dom';
import useAuth from "../hooks/useAuth";


export const CreateCenter = () => {
    const { auth } = useAuth();
    const [city, setCity] = useState('');
    const [address, setAddress] = useState('');
    const [phoneNumber, setphoneNumber] = useState('');
    const [capacity, setCapacity] = useState(20);
    const [petNumber, setPetNumber] = useState(0);
    const axiosPrivate = useAxiosPrivate();
    //const [services, setServices] = useState([]); 


    const [isCityOk, setIsCityOk] = useState(true);
    const [isAddressOk, setIsAddressOk] = useState(true);
    const [isphoneNumberOk, setIsphoneNumberOk] = useState(true);
    const [isCapacityOk, setIsCapacityOk] = useState(true);
    const [ispetNumberOk, setIsPetNumberOk] = useState(true);


    const [displayConfirmation, setDisplayConfirmation] = useState(false);
    const [displayConfirmation2, setDisplayConfirmation2] = useState(false);
    const [err, setErr] = useState(false);
    const [checkboxValue, setCheckboxValue] = useState([]);
    const [radioValue, setRadioValue] = useState('dev');
    const [errorMsg, setErrorMsg] = useState('')

    const form = useRef(null);
    const navigate = useNavigate();


    const servicesValues = [
        { name: 'CLIENT', code: 'cl' },
        { name: 'SERVICE', code: 'sd' },
        { name: 'ADMIN', code: 'admin' },
        { name: 'DEV', code: 'dev' },
        { name: 'APPROVER', code: 'app' }
    ];


    function openAllCenters(event) {
        setDisplayConfirmation2(false)
        navigate('/all-centers');
    };

    const confirmationDialogFooter = (
        <>
            <Button type="button" label="No" icon="pi pi-times" onClick={() => setDisplayConfirmation(false)} className="p-button-text" autoFocus />
            <Button type="button" label="Yes" icon="pi pi-check" onClick={handleCancelConfirmation} className="p-button-text" />
        </>
    );


    const confirmationDialogFooter2 = (
        <>
          <div className="grid formgrid">
            <Button type="button" label="All centers" icon="pi pi-list" onClick={openAllCenters} className="p-button-text" />
            <Button type="button" label="New center" icon="pi pi-plus" onClick={() => setDisplayConfirmation2(false)} className="p-button-text" />
          </div>
        </>
      );



    const styles = {

        marginLeft: '33%',
        marginRight: 'auto',
    };


    /* const onCheckboxChange = (e) => {
       let selectedValue = [...checkboxValue];
       if (e.checked) {
           selectedValue.push(e.value);
           services.push(e.value)
       }
       else {
           selectedValue.splice(selectedValue.indexOf(e.value), 1);
           services.splice(services.indexOf(e.value), 1);
       }
   
       setCheckboxValue(selectedValue);
     };
   
     function handleRegistration(event) {
       event.preventDefault();
       history.push('/registration');
     };*/

    function handleCancelConfirmation(event) {
        event.preventDefault();
        setCity('');
        setAddress('');
        setphoneNumber('');
        setCapacity(20);
        setPetNumber(0);
        setIsCityOk(true);
        setIsAddressOk(true);
        setIsphoneNumberOk(true);
        setIsCapacityOk(true);
        setIsPetNumberOk(true);
        //form.current.reset(); //this will reset all the inputs in the form
        setDisplayConfirmation(false);
        // navigate('/centers');

    }

    function handleCancelConfirmation2() {
        setCity('');
        setAddress('');
        setphoneNumber('');
        setCapacity(20);
        setPetNumber(0);
        setIsCityOk(true);
        setIsAddressOk(true);
        setIsphoneNumberOk(true);
        setIsCapacityOk(true);
        setIsPetNumberOk(true);
        //form.current.reset(); //this will reset all the inputs in the form
        setDisplayConfirmation2(true);
    }

    const changeCity = (e) => {
        e.preventDefault();
        setCity(e.target.value);
        if (e.target.value.trim().length === 0) setIsCityOk(false);
        else setIsCityOk(true);
        setErr(false);
    }

    const changeAddress = (e) => {
        e.preventDefault();
        setAddress(e.target.value);
        if (e.target.value.trim().length === 0) setIsAddressOk(false);
        else setIsAddressOk(true);
        setErr(false);
    }

    const changePhoneNumber = (e) => {
        e.preventDefault();
        let phoneFormat = /^\+(?:[0-9] ?){6,14}[0-9]$/;
        if (e.target.value.match(phoneFormat)) setIsphoneNumberOk(true);
        else setIsphoneNumberOk(false);
        setphoneNumber(e.target.value);
        setErr(false);
    }

    const changeCapacity = (e) => {
        e.preventDefault();
        if (e.target.value < 0 || e.target.value > 200) setIsCapacityOk(false);
        else setIsCapacityOk(true);
        setCapacity(e.target.value);
        setErr(false);
    }

    const changePetNumber = (e) => {
        e.preventDefault();
        if (e.target.value < 0 || e.target.value > 200) setIsPetNumberOk(false);
        else setIsPetNumberOk(true);
        setPetNumber(e.target.value);
        setErr(false);
    }


    async function handleSubmit(event) {
        event.preventDefault();
        var ok = true;
        if (city.trim().length === 0) {
            setIsCityOk(false);
            ok = false;
        }
        if (address.trim().length === 0) {
            setIsAddressOk(false);
            ok = false;
        }
        if (phoneNumber.trim().length === 0) {
            setIsphoneNumberOk(false);
            ok = false;
        }
        if (!phoneNumber.match(/^\+(?:[0-9] ?){6,14}[0-9]$/)) {
            setIsphoneNumberOk(false);
            ok = false;
        }
        if (!ok) return;
        setErr(false);
        var url = 'http://localhost:8090/main/api/center';
        await axiosPrivate.post(url, {
            city: city,
            address: address,
            phoneNumber: phoneNumber,
            capacity: capacity,
            petNo: petNumber
        }, {
            headers: {
                Authorization: 'Bearer ' + auth?.accessToken
            }
        })
            .then(res => {
                console.log(res);
                console.log(res.data);
                handleCancelConfirmation2();
            }).catch(function (error) {
                if (error.response) {
                    setErr(true);
                    setErrorMsg(error.response.data.message)
                    console.log(error.response.data);
                    console.log(error.response.status);
                    console.log(error.response.headers);
                } else if (error.request) {
                    // The request was made but no response was received
                    console.log(error.request);
                } else {
                    // Something happened in setting up the request that triggered an Error
                    console.log('Error', error.message);
                }

            });

    }


    return (

        <div className="grid p-fluid" style={styles}>
            <div className="col-12 lg:col-6">
                <h2 style={{ "textAlign": "center" }}>New center</h2>
                <div className="card">
                    <h5>City*</h5>
                    <form id="change_form" ref={form} onSubmit={handleSubmit}>

                        <InputText id="city" type="text" placeholder='City' name="city" value={city} onChange={changeCity} maxLength={57} className={isCityOk ? "" : "p-invalid"} aria-describedby="subject-help" />
                        {isCityOk ? ("") : (<small id="subject-help" className="p-error">City is mandatory!</small>)}
                        <h5>Address*</h5>
                        <InputText id="address" type="text" placeholder='Address' value={address} name="address" onChange={changeAddress} maxLength={57} className={isAddressOk ? "" : "p-invalid"} aria-describedby="subject-help" />
                        {isAddressOk ? ("") : (<small id="subject-help" className="p-error">Address is mandatory!</small>)}

                        <h5>Phone number*</h5>
                        <InputText id="phoneNumber" type="text" placeholder='e.g. +387 xx xxx xxx' name="phoneNumber" value={phoneNumber} onChange={changePhoneNumber} maxLength={57} className={isphoneNumberOk ? "" : "p-invalid"} aria-describedby="subject-help" />
                        {isphoneNumberOk ? ("") : (<small id="subject-help" className="p-error">Invalid phone number!</small>)}
                        <h5>Capacity*</h5>
                        <InputText id="capacity" type="number" pattern="[0-9]*" placeholder='Capacity' name="capacity" value={capacity} onChange={changeCapacity} maxLength={57} className={isCapacityOk ? "" : "p-invalid"} aria-describedby="subject-help" />
                        {isCapacityOk ? ("") : (<small id="subject-help" className="p-error">Capacity must be between 0 and 200!</small>)}
                        <h5></h5>
                        <div style={{ display: "flex" }}>
                            <Button label="Create" type='submit' className="mr-2 mb-2"></Button>

                            <Dialog header="Center is saved!" visible={displayConfirmation2} onHide={() => setDisplayConfirmation2(false)} style={{ width: '350px' }} modal footer={confirmationDialogFooter2}>
                                <div className="flex align-items-center justify-content-center">
                                    <i className="pi pi-check-circle mr-3" style={{ fontSize: '2rem' }} />
                                    <span>Do you want to see all centers or create another one?</span>
                                </div>
                            </Dialog>
                            <Button type='button' label="Cancel" className="mr-2 mb-2" onClick={() => setDisplayConfirmation(true)}></Button>
                        </div>
                        <Dialog header="Cancel without saving?" visible={displayConfirmation} onHide={() => setDisplayConfirmation(false)} style={{ width: '350px' }} modal footer={confirmationDialogFooter}>
                            <div className="flex align-items-center justify-content-center">
                                <i className="pi pi-exclamation-triangle mr-3" style={{ fontSize: '2rem' }} />
                                <span>All unsaved information will be lost.</span>
                            </div>
                        </Dialog>
                    </form>
                </div>
            </div>
        </div>

    )
}

const comparisonFn = function (prevProps, nextProps) {
    return (prevProps.location.pathname === nextProps.location.pathname) && (prevProps.colorMode === nextProps.colorMode);
};

export default React.memo(CreateCenter, comparisonFn);
