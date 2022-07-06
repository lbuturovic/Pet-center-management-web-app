import React, { useState, useEffect } from 'react';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Button } from 'primereact/button';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const ActiveReports = props => {
    const [reports, setReports] = useState([]);
    const [forms, setForms] = useState(null);
    const [expandedRows, setExpandedRows] = useState(null);
    const [displayRejectDialog, setDisplayRejectDialog] = useState(false);
    const [descriptionValue, setDescriptionValue] = useState('');
    const [isDescriptionOk, setIsDescriptionOk] = useState(true);
    const [reportId, setReportId] = useState(null);


    let navigate = useNavigate();


    const getAllFormsFun = (reports) => {
        for (let i = 0; i < reports.length; i++) {
            axios.get("http://localhost:8083/api/report/" + reports[i].reportID + "/forms", {
                
            }).then((response) => {
                reports[i]["history"] = response.data;
            }).catch(error => console.error("${error}"));
        }

        setReports(reports);
    }

    const getReports = () => {
        return axios.get("http://localhost:8083/api/reports", {

        }).then((response) => {
            getAllFormsFun(response.data);
        }).catch(error => console.error("${error}"));
    }

    useEffect(() => {
        getReports();
        //setApprover(currentUser.roles.includes('ROLE_APPROVER'));
        //getAllReports();
        //initFilters1();
    }, []); // eslint-disable-line react-hooks/exhaustive-deps


    const formatDate = (value) => {
        if(value===null) return;
        const dateTime = new Date(value);
        const date = dateTime.toLocaleDateString('en-GB', {
            day: '2-digit',
            month: '2-digit',
            year: 'numeric',
        });

        const time = dateTime.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
        return date + " " + time;
    }

    const formatDateWithoutTime = (value) => {
        if(value===null) return;
        const dateTime = new Date(value);
        const date = dateTime.toLocaleDateString('en-GB', {
            day: '2-digit',
            month: '2-digit',
            year: 'numeric',
        });

        return date;
    }





    const dateBodyTemplateCreated = (rowData) => {
        return formatDate(rowData.timestamp);
    }
 
    const dateBodyTemplateUpdated = (rowData) => {
        return formatDate(rowData.updateTimestamp);
    }

    const statusBodyTemplate = (rowData) => {
        const status = rowData.status;
        let color = 'new';
        if (status === 'PENDING') color = 'new';
        else if (status === 'ACCEPTED') color = 'qualified';
        else if (status === 'REJECTED' || status === 'CLOSED') color = 'unqualified';
        else if (status === 'REVIEWING' || status === 'PLANNING') color = 'proposal';
        else if (status === 'IMPLEMENTING') color = 'renewal';
        else if (status === 'TESTING') color = 'negotiation';

        return <span className={`customer-badge status-${color}`}>{rowData.status}</span>;
    }

    const typeBodyTemplate = (rowData) => {
        const type = rowData.type;
        let color = 'new';
        if (type === 'DOG') color = 'new';
        else if (type === 'CAT') color = 'proposal';
        else color = 'negotiation';
        return <span className={`customer-badge status-${color}`}>{rowData.type}</span>;
    }

/*
    async function handleReject(event) {
        event.preventDefault();
        var ok = true;
        if (descriptionValue.trim().length === 0) {
            setIsDescriptionOk(false);
            ok = false;
        }
        if (!ok) return;

        var urlChange = 'http://localhost:8080/api/user/' + currentUser.id + '/change/' + changeId + '/close';
        await axios.put(urlChange, {
            description: descriptionValue.trim(),
        }, {
            headers: {
                'Authorization': authHeader().Authorization
            }
        })
            .then(res => {
                setChanges(changes.filter(item => item.id !== changeId));
                setDisplayRejectDialog(false);
                //window.location = "/edit-change" //This line of code will redirect you once the submission is succeed
            }).catch(function (error) {
                if (error.response) {
                    // Request made and server responded
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

    }*/

    const rejectDialogFooter = (
        <>
            <Button type="button" label="Confirm" icon="pi pi-check" className="p-button-text" />
            <Button type="button" label="Cancel" icon="pi pi-times" onClick={() => setDisplayRejectDialog(false)} className="p-button-text" autoFocus />
        </>
    );


    const openBodyTemplate = (rowData) => {
        function handleEditClick() {
            let id = "?id=" + rowData.reportID;
            navigate({pathname:'/report', search: id });
        }
        return <div><Button label="Edit" className="p-button-raised p-button-info mr-2 mb-2" onClick={handleEditClick} />
        </div>;
    }

    const expandAll = () => {
        let _expandedRows = {};
        reports.forEach(p => _expandedRows[`${p.reportID}`] = true);
        setExpandedRows(_expandedRows);
    }

    const collapseAll = () => {
        setExpandedRows(null);
    }

    const rowExpansionTemplate = (data) => {
        return (
            <div className="orders-subtable">
                <h5>Activity for report</h5>
                <DataTable value={data.history} responsiveLayout="scroll">
                <Column field="status" header="Status" body={statusBodyTemplate}></Column>
                <Column field="description" header="Description"></Column>
                <Column field="center.name" header="Center"></Column>
                <Column field="timestamp" header="Date and time" body={dateBodyTemplateCreated}></Column>
                </DataTable>
            </div>
        );
    }

    const header = (
        <div className="table-header-container">
            <Button icon="pi pi-plus" label="Expand All" onClick={expandAll} className="mr-2 mb-2" />
            <Button icon="pi pi-minus" label="Collapse All" onClick={collapseAll} className="mb-2" />
        </div>
    );


    return (
        <div className="grid table-demo">
            <div className="col-12">
                <div className="card">
                    <h5>Active reports</h5>
                    <DataTable value={reports} expandedRows={expandedRows} onRowToggle={(e) => setExpandedRows(e.data)} responsiveLayout="scroll"
                        rowExpansionTemplate={rowExpansionTemplate} dataKey="reportID" header={header}>
                        <Column expander style={{ width: '3em' }} />
                        <Column field="description" header="Description" sortable />
                        <Column field="status" header="Status" sortable body={statusBodyTemplate} />
                        <Column field="type" header="Type" sortable body={typeBodyTemplate} />
                        <Column field="timestamp" header="Time created" sortable body={dateBodyTemplateCreated} />
                        <Column field="timestampUpdate" header="Time updated" sortable body={dateBodyTemplateUpdated} />
                        <Column bodyClassName="text-center" style={{ minWidth: '8rem' }} body={openBodyTemplate} />
                    </DataTable>
                </div>
            </div>
        </div>
    );
}

const comparisonFn = function (prevProps, nextProps) {
    return prevProps.location.pathname === nextProps.location.pathname;
};

export default React.memo(ActiveReports, comparisonFn);
