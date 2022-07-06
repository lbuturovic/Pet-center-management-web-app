import React, { useEffect, useState, useRef } from 'react';
import axios from "axios";
import { useNavigate } from 'react-router-dom';
import { Image } from "primereact/image";
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Dropdown } from 'primereact/dropdown';
import { InputTextarea } from 'primereact/inputtextarea';
import { Button } from 'primereact/button';
import { Dialog } from 'primereact/dialog';
import useAuth from "../hooks/useAuth";
import useAxiosPrivate from "../hooks/useAxiosPrivate";

export const ReportActivity = () => {
    const [descriptionValue, setDescriptionValue] = useState('');
    const [descriptionInputValue, setDescriptionInputValue] = useState('');
    const [isDescriptionOk, setIsDescriptionOk] = useState(true);
    const [creationValue, setCreationValue] = useState('');
    const [statusValue, setStatusValue] = useState('');
    const [statusDropdownValue, setStatusDropdownValue] = useState('PENDING');
    const [centerValue, setCenterValue] = useState('');
    const [updateValue, setUpdateValue] = useState('');
    const [image, setImage] = useState('');
    const [location, setLocation] = useState('');
    const [isImageOk, SetIsImageOk] = useState(true);
    const urlReport = "http://localhost:8083/api/report/";
    const urlCenters = "http://localhost:8083/api/centers";
    const [allCenters, setAllCenters] = useState('');
    const [displayConfirmation, setDisplayConfirmation] = useState(false);
    const [idValue, setIdValue] = useState(null);
    const [error, setError] = useState('');
    const [errorMsg, setErrorMsg] = useState('');
    const [displayErrorDialog, setDisplayErrorDialog] = useState(false);
    const [activity, setActivity] = useState([]);
    const form = useRef(null);
    const { auth } = useAuth();
    const navigate = useNavigate();

    //PENDING, ARRIVING, PICKED_UP, NOT_FOUND, AT_CENTER, ADOPTABLE, ADOPTED, CLOSED, REJECTED
    const statusDropdownValues = [
        { name: 'Pending', code: 'PENDING' },
        { name: 'Arriving', code: 'ARRIVING' },
        { name: 'At location', code: 'AT_LOCATION' },
        { name: 'At center', code: 'AT_CENTER' },
        { name: 'Picked up', code: 'PICKED_UP' },
        { name: 'Not found', code: 'NOT_FOUND' },
        { name: 'Adoptable', code: 'ADOPTABLE' },
        { name: 'Adopted', code: 'ADOPTED' },
        { name: 'Rejected', code: 'REJECTED' },

    ];

    const changeDescription = (e) => {
        e.preventDefault();
        setDescriptionInputValue(e.target.value);
        if (e.target.value.trim().length == 0) setIsDescriptionOk(false);
        else setIsDescriptionOk(true);
    }

    useEffect(() => {
        /*const search = location.search;
        let id = new URLSearchParams(search).get('id');*/
        const params = new URLSearchParams(window.location.search) // id=123
        let id = params.get('id') // 123 
        getReport(urlReport, id);
        getForms(id);
        getAllCenters();

    }, [location]);

    

    const getForms = (id) => {
        axios.get("http://localhost:8083/api/report/" + id + "/forms", {

        }).then((response) => {
            setActivity(response.data);
           // console.log(response.data);
        }).catch(error => console.error("${error}"));
    }

    const getAllCenters = () => {
        axios.get(urlCenters, {

        }).then((response) => {
            setAllCenters(response.data);
            setCenterValue(response.data[0].centerID);
        }).catch(error => console.error("${error}"));
    }

    const getReport = (urlReport, id) => {
       // console.log(id);
        axios.get(urlReport + id, {
        }).then((response) => {
            setDescriptionValue(response.data.description);
            setCreationValue(response.data.created);
            setUpdateValue(response.data.updated);
            setStatusValue(response.data.status);
            let lat = response.data.latitude;
            let long = response.data.longitude;
            setLocation("https://dev.virtualearth.net/REST/V1/Imagery/Map/Road/" + lat + "%2C" + long + "/15?mapSize=400,300&dc=c,64009900,FF009900,2,50;" + lat + "," + long + "&pp=" + lat + "," + long + "&format=jpeg&key=Alo3-CHhi5lXLv1oAbr61e7HVKpHbUlyj2BUUe9N7NKSAyQKEkNSjsPR_HLzwg6X");
            setImage(response.data.image);
            setIdValue(id);
        }).catch(error => console.error(`${error}`));
    }

    const formatDate = (value) => {
        const dateTime = new Date(value);
        const date = dateTime.toLocaleDateString('en-GB', {
            day: '2-digit',
            month: '2-digit',
            year: 'numeric',
        });

        const time = dateTime.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
        return date + " " + time;
    }

    const dateBodyTemplateCreated = (rowData) => {
        return formatDate(rowData.timestamp);
    }

    const statusBodyTemplate = (status) => {
        let color = 'new';
        if (status === 'PENDING') color = 'new';
        else if (status === 'ACCEPTED') color = 'qualified';
        else if (status === 'REJECTED' || status === 'CLOSED') color = 'unqualified';
        else if (status === 'REVIEWING' || status === 'PLANNING') color = 'proposal';
        else if (status === 'IMPLEMENTING') color = 'renewal';
        else if (status === 'TESTING') color = 'negotiation';
        return color;
    }

    //ef440ad3-5a44-47e1-9b56-77938ae4a86b

    async function handleSubmit(event) {
        event.preventDefault();
        if (descriptionInputValue.trim().length === 0 || centerValue === '') {
            setIsDescriptionOk(false);
            return;
        }
        let urlForm = 'http://localhost:8083/api/center/' + centerValue + '/report/' + idValue + '/form';
        await axios.post(urlForm, {
            description: descriptionInputValue,
            status: statusDropdownValue

        }, {

        })
            .then(res => {
                handleConfirmation();
                getForms(idValue);
                setStatusValue(statusDropdownValue);
                setUpdateValue(res.data.timestamp);
            }).catch(function (error) {
                if (error.response) {
                    // Request made and server responded
                    setError(error.response.data.error);
                    setErrorMsg(error.response.data.message);
                    setDisplayErrorDialog(true);
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

    function handleConfirmation() {
        setDescriptionInputValue('');
        setStatusDropdownValue('PENDING');
        setIsDescriptionOk(true);
        setDisplayConfirmation(true);
    }

    const confirmationDialogFooter2 = (
        <>
            <div className="grid formgrid">
                <Button type="button" label="Yes" icon="pi pi-list" className="p-button-text" />
                <Button type="button" label="No" onClick={() => setDisplayConfirmation(false)} className="p-button-text" />
            </div>
        </>
    );


    return (
        <div className="grid p-fluid">
            <div className="col-12 lg:col-6">
                <div className="card">
                    <h4>Report code: {idValue} </h4>
                    <p>Pet: </p>
                    <div className="flex justify-content-center">
                        <Image src={`data:image/jpeg;base64,${image}`} alt="Pet image" width={250} preview />
                    </div>
                    <p> </p>
                    <p>Description: <b>{descriptionValue}</b></p>
                    <p>Time created: <b>{formatDate(creationValue)}</b></p>
                    <p>Status: <b><span className={`customer-badge status-${statusBodyTemplate(statusValue)}`}>{statusValue}</span></b></p>
                    <p>Status updated: <b>{formatDate(updateValue)}</b></p>
                    <p>Location:</p>
                    <div className="flex justify-content-center">
                        <Image src={location} alt="Location map" width={250} preview />
                    </div>
                </div>
            </div>
            <div className="col-12 md:col-6">
            {
                auth && auth?.roles == 'ROLE_EMPLOYEE' &&
                <div className="card">
                    <h4>Add activity</h4>
                    <h5>Description*</h5>
                    <InputTextarea placeholder="Description of activity" id='description' name='description' value={descriptionInputValue} maxLength={275} className={isDescriptionOk ? "" : "p-invalid"}
                        onChange={changeDescription} autoResize rows="5" cols="30" aria-describedby="description-help" />
                    {isDescriptionOk ? ("") : (<small id="description-help" className="p-error">Description is mandatory!</small>)}
                    <h5>Status*</h5>
                    <Dropdown value={statusDropdownValue} onChange={(e) => setStatusDropdownValue(e.value)} options={statusDropdownValues} optionLabel="name" optionValue='code' name='status' placeholder="Pending" />
                    <h5>Center*</h5>
                    <Dropdown value={centerValue} onChange={(e) => setCenterValue(e.value)} options={allCenters} optionLabel="name" optionValue='centerID' name='center' />
                    <h5> </h5>
                    <div style={{ display: "flex" }}>
                        <Button label="Create" className="mr-2 mb-2" onClick={handleSubmit} ></Button>
                        <Dialog header="Activity is saved!" visible={displayConfirmation} onHide={() => setDisplayConfirmation(false)} style={{ width: '350px' }} modal footer={confirmationDialogFooter2}>
                            <div className="flex align-items-center justify-content-center">
                                <i className="pi pi-check-circle mr-3" style={{ fontSize: '2rem' }} />
                                <span>Do you want to see active reports?</span>
                            </div>
                        </Dialog>
                        <Dialog header={error} visible={displayErrorDialog} onHide={() => setDisplayErrorDialog(false)} style={{ width: '350px' }} modal>
                            <div className="flex align-items-center justify-content-center">
                                <i className="pi pi-exclamation-circle mr-3" style={{ fontSize: '2rem' }} />
                                <span>{errorMsg}</span>
                            </div>
                        </Dialog>
                        <Button label="Cancel" type='button' className="mr-2 mb-2"></Button>
                    </div>
                </div>
}
                <div className="card">
                    <h4>Recent activity</h4>
                    <DataTable value={activity} rows={5} paginator responsiveLayout="scroll">
                        <Column field="status" header="Status"></Column>
                        <Column field="description" header="Description"></Column>
                        <Column field="center.name" header="Center"></Column>
                        <Column field="timestamp" header="Date and time" body={dateBodyTemplateCreated}></Column>
                    </DataTable>
                </div>
            </div>
        </div>

    )
}

const comparisonFn = function (prevProps, nextProps) {
    return (prevProps.location.pathname === nextProps.location.pathname) && (prevProps.colorMode === nextProps.colorMode);
};

export default React.memo(ReportActivity, comparisonFn);
