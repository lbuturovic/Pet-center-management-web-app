import React, { useEffect, useState, useRef } from 'react';
import { InputText } from 'primereact/inputtext';
import { InputTextarea } from 'primereact/inputtextarea';
import axios from "axios";
import { Dialog } from 'primereact/dialog';
import { Button } from 'primereact/button';
import { useNavigate } from 'react-router-dom';
import { Image } from "primereact/image";
import { Dropdown } from 'primereact/dropdown';


export const ReportPet = () => {
    const [descriptionValue, setDescriptionValue] = useState('');
    const [isDescriptionOk, setIsDescriptionOk] = useState(true);
    const [longitudeValue, setLongitudeValue] = useState(null);
    const [typeValue, setTypeValue] = useState('DOG');
    const [isLocationOk, setIsLocationOk] = useState(false);
    const [latitudeValue, setLatitudeValue] = useState(null);
    const [base64TextString, setBase64TextString] = useState('');
    const [image, setImage] = useState('');
    const [code, setCode] = useState('');
    const [isCodeValueOk, setIsCodeValueOk] = useState(true);
    const [codeValue, setCodeValue] = useState('');
    const [isImageOk, SetIsImageOk] = useState(true);
    const [displayConfirmation, setDisplayConfirmation] = useState(false);
    const [displayConfirmation2, setDisplayConfirmation2] = useState(false);
    const urlReport = "http://localhost:8083/api/report";
    const [error, setError] = useState('');
    const [errorMsg, setErrorMsg] = useState('');
    const [displayErrorDialog, setDisplayErrorDialog] = useState(false);
    const toast = useRef(null);
    const form = useRef(null);
    const navigate = useNavigate();

    const confirmationDialogFooter = (
        <>
            <Button type="button" label="No" icon="pi pi-times" onClick={() => setDisplayConfirmation(false)} className="p-button-text" autoFocus />
            <Button type="button" label="Yes" icon="pi pi-check" onClick={handleCancelConfirmation} className="p-button-text" />
        </>
    );

    const confirmationDialogFooter2 = (
        <>
            <div className="grid formgrid">
                <Button type="button" label="Report activity" icon="pi pi-list" onClick={handleShowReportActivity} className="p-button-text" />
            </div>
        </>
    );

    useEffect(() => {
        navigator.geolocation.getCurrentPosition(function (position) {
            console.log("Latitude is :", position.coords.latitude);
            setLatitudeValue(position.coords.latitude);
            setLongitudeValue(position.coords.longitude);
            setIsLocationOk(true);
            setImage("https://dev.virtualearth.net/REST/V1/Imagery/Map/Road/" + position.coords.latitude + "%2C" + position.coords.longitude + "/15?mapSize=400,300&dc=c,64009900,FF009900,2,50;" + position.coords.latitude + "," + position.coords.longitude + "&pp=" + position.coords.latitude + "," + position.coords.longitude + "&format=jpeg&key=Alo3-CHhi5lXLv1oAbr61e7HVKpHbUlyj2BUUe9N7NKSAyQKEkNSjsPR_HLzwg6X");
            console.log("Longitude is :", position.coords.longitude);
        });
    }, []);

    const convertToBase64 = (file) => {
        return new Promise((resolve, reject) => {
            const fileReader = new FileReader();
            fileReader.readAsDataURL(file);
            fileReader.onload = () => {
                resolve(fileReader.result);
            };
            fileReader.onerror = (error) => {
                reject(error);
            };
        });
    };

    const handleFileUpload = async (e) => {
        const file = e.target.files[0];
        console.log(file)
        const base64 = await convertToBase64(file);
        console.log(base64.split(',')[1]);
        setBase64TextString(base64.split(',')[1]);
        console.log(base64);
        SetIsImageOk(true);
    };

    const changeCodeValue = (e) => {
        e.preventDefault();
        setCodeValue(e.target.value);
        let codeFormat = /[0-9a-fA-F]{8}\-[0-9a-fA-F]{4}\-[0-9a-fA-F]{4}\-[0-9a-fA-F]{4}\-[0-9a-fA-F]{12}/;
        if (e.target.value.match(codeFormat)) setIsCodeValueOk(true);
        else setIsCodeValueOk(false);
    }

    const typeValues = [
        { name: 'Dog', code: 'DOG' },
        { name: 'Cat', code: 'CAT' },
        { name: 'Other', code: 'OTHER' }
    ];


    function handleShowReportActivity(event) {
        event.preventDefault();
        let id = "?id=" + code;
        navigate({ pathname: '/report', search: id });
    };

    function handleCancelConfirmation(event) {
        event.preventDefault();
        setDescriptionValue('');
        setTypeValue('DOG');
        setLatitudeValue(null);
        setLongitudeValue(null);
        setBase64TextString(null);
        SetIsImageOk(true);
        setIsDescriptionOk(true);
        setDisplayConfirmation(false);

    }

    async function handleInputCode(event) {
        event.preventDefault();
        let codeFormat = /[0-9a-fA-F]{8}\-[0-9a-fA-F]{4}\-[0-9a-fA-F]{4}\-[0-9a-fA-F]{4}\-[0-9a-fA-F]{12}/;
        if (!codeValue.match(codeFormat)) {
            setIsCodeValueOk(false);
            return;
        }
        await axios.get("http://localhost:8083/api/report/" + codeValue, {

        })
            .then(res => {
                let idCode = "?id=" + codeValue;
                navigate({ pathname: '/report', search: idCode });
            }).catch(function (error) {
                if (error.response) {
                    // Request made and server responded
                    setError(error.response.data.error);
                    setErrorMsg(error.response.data.message);
                    setDisplayErrorDialog(true);
                } else if (error.request) {
                    // The request was made but no response was received
                    setError("Problem");
                    setErrorMsg("There is a problem with server!");
                    setDisplayErrorDialog(true);
                } else {
                    // Something happened in setting up the request that triggered an Error
                    console.log('Error', error.message);
                }

            });

    }

    function handleCancelConfirmation2() {
        setDescriptionValue('');
        setTypeValue('DOG');
        setLatitudeValue(null);
        setLongitudeValue(null);
        setBase64TextString(null);
        SetIsImageOk(true);
        setIsDescriptionOk(true);
        setDisplayConfirmation2(true);
        setCodeValue(code);
    }

    const changeDescription = (e) => {
        e.preventDefault();
        setDescriptionValue(e.target.value);
    }

    async function handleSubmit(event) {
        event.preventDefault();
        var ok = true;
        if (!base64TextString || base64TextString === '') {
            SetIsImageOk(false);
            ok = false;
        }
        if (!isLocationOk) ok = false;
        if (!ok) return;
        await axios.post(urlReport, {
            description: descriptionValue,
            image: base64TextString,
            longitude: longitudeValue,
            latitude: latitudeValue,
            type: typeValue
        })
            .then(res => {
                setCode(res.data.reportID);
                handleCancelConfirmation2();
                //window.location = "/edit-change" //This line of code will redirect you once the submission is succeed
            }).catch(function (error) {
                if (error.response) {
                    // Request made and server responded
                    setError(error.response.data.error);
                    setErrorMsg(error.response.data.message);
                    setDisplayErrorDialog(true);
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
        <div>
            <div className="flex justify-content-center">
                <div className="grid p-fluid">
                    <div className="col-12 lg:col-6">
                        <h2>Create new report</h2>
                        <div className="card">
                            <form id="change_form" ref={form} onSubmit={handleSubmit}>
                                <h5>Description</h5>
                                <InputTextarea placeholder="e.g. pet condition, location, special notes" id='description' name='description' value={descriptionValue} maxLength={275} className={isDescriptionOk ? "" : "p-invalid"}
                                    onChange={changeDescription} autoResize rows="5" cols="30" aria-describedby="description-help" />
                                {isDescriptionOk ? ("") : (<small id="description-help" className="p-error">Description is mandatory!</small>)}
                                <h5>Type*</h5>
                                <Dropdown value={typeValue} onChange={(e) => setTypeValue(e.value)} options={typeValues} optionLabel="name" name='type' optionValue='code' placeholder="Select" />
                                <h5>Your location*</h5>

                                {isLocationOk ? (<div className="flex justify-content-center"><Image src={image} alt="Please, turn on your location and refresh page." width={250} preview /></div>) : (<small id="subject-help" className="p-error">Please, turn on your location and refresh page.</small>)}

                                <h5>Pet image upload*</h5>
                                <InputText
                                    type="file"
                                    label="Image"
                                    name="myFile"
                                    accept=".jpeg, .png, .jpg"
                                    onChange={(e) => handleFileUpload(e)}
                                    className={isImageOk ? "" : "p-invalid"} aria-describedby="subject-help"
                                />
                                {isImageOk ? ("") : (<small id="subject-help" className="p-error">Image is mandatory!</small>)}
                                <h5> </h5>
                                <div className="flex justify-content-center">
                                    <Button label="Create" type='submit' className="mr-2 mb-2"></Button>

                                    <Dialog header="Save this code for report tracking:" visible={displayConfirmation2} onHide={() => setDisplayConfirmation2(false)} style={{ width: '350px' }} modal footer={confirmationDialogFooter2}>
                                        <div className="flex align-items-center justify-content-center">
                                            <i className="pi pi-check-circle mr-3" style={{ fontSize: '2rem' }} />
                                            <span>{code}</span>
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
                    <div className="col-12 md:col-6">
                        <h2>Track your report</h2>
                        <div className="card">
                            <InputText type='text' placeholder="Tracking code" id='code' name='code' value={codeValue} maxLength={40} className={isCodeValueOk ? "" : "p-invalid"}
                                onChange={changeCodeValue} autoResize rows="5" cols="30" aria-describedby="description-help" />
                            {isCodeValueOk ? ("") : (<small id="description-help" className="p-error">Invalid code!</small>)}
                            <h5> </h5>
                            <Button type='button' label="Confirm" className="mr-2 mb-2" onClick={handleInputCode}></Button>
                            <Dialog header={error} visible={displayErrorDialog} onHide={() => setDisplayErrorDialog(false)} style={{ width: '350px' }} modal>
                            <div className="flex align-items-center justify-content-center">
                                <i className="pi pi-exclamation-circle mr-3" style={{ fontSize: '2rem' }} />
                                <span>{errorMsg}</span>
                            </div>
                        </Dialog>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    )
}

const comparisonFn = function (prevProps, nextProps) {
    return (prevProps.location.pathname === nextProps.location.pathname) && (prevProps.colorMode === nextProps.colorMode);
};

export default React.memo(ReportPet, comparisonFn);
