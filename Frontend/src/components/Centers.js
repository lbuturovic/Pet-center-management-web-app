import React, { useState, useEffect } from 'react';
import { classNames } from 'primereact/utils';
import { FilterMatchMode, FilterOperator } from 'primereact/api';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Dialog } from 'primereact/dialog';
import { Dropdown } from 'primereact/dropdown';
import { Button } from 'primereact/button';
import { Calendar } from 'primereact/calendar';
import { TriStateCheckbox } from 'primereact/tristatecheckbox';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import useAuth from "../hooks/useAuth";
import useAxiosPrivate from "../hooks/useAxiosPrivate";



const Centers = () => {
    const { auth } = useAuth();
    const [centers, setCenters] = useState(null);
    const axiosPrivate = useAxiosPrivate();
    //const [loading1, setLoading1] = useState(true);
    const [displayConfirmation2, setDisplayConfirmation2] = useState(false);
    const navigate = useNavigate();

    const getAllCenters = () => {
        return axiosPrivate.get("http://localhost:8090/main/api/centers/", {
          headers: {
            Authorization: 'Bearer ' + auth?.accessToken
          }
        }).then((response) => {
            setCenters(response.data);
            console.log(centers);
          }).catch(error => console.error(`${error}`));
        }
    

    useEffect(() => {
        //setLoading1(false);
        getAllCenters();

    }, []); 


 /*   const getCenters = (data) => {
        return [...data || []].map(d => {
            d.date = new Date(d.date);
            return d;
        });
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


    const dateBodyTemplate = (rowData) => {
        if(rowData)
        return rowData.created;
    }

    const dateFilterTemplate = (options) => {
        return <Calendar value={options.value} onChange={(e) => options.filterCallback(e.value, options.index)} dateFormat="mm/dd/yy" placeholder="mm/dd/yyyy" mask="99/99/9999" />
    }

    const categoryBodyTemplate = (rowData) => {
        return <span>{rowData.category}</span>;
    }

    const statusBodyTemplate = (rowData) => {
        const status = rowData.status;
        let color = 'new';
        if (status === 'PENDING') color = 'new';
        else if (status === 'RESOLVED') color = 'qualified';
        else if (status === 'CLOSED' || status === 'CLOSED') color = 'unqualified';
        else if (status === 'REVIEWING' || status === 'PLANNING') color = 'proposal';
        else if (status === 'OPEN') color = 'renewal';
        else if (status === 'IN PROGRESS') color = 'negotiation';

        return <span className={`customer-badge status-${color}`}>{rowData.status}</span>;
    }

    const priorityBodyTemplate = (rowData) => {
        const priority = rowData.priority;
        let color = 'new';
        if (priority === 'PENDING') color = 'new';
        else if (priority === 'LOW') color = 'qualified';
        else if (priority === 'URGENT' || priority === 'CLOSED') color = 'unqualified';
        else if (priority === 'MEDIUM') color = 'negotiation';
        else if (priority === 'HIGH') color = 'proposal';
        return <span className={`customer-badge status-${color}`}>{rowData.priority}</span>;
    }

    const priorityFilterTemplate = (options) => {
        return <Dropdown value={options.value} options={priorities} onChange={(e) => options.filterCallback(e.value, options.index)} itemTemplate={priorityItemTemplate} placeholder="Select a Status" className="p-column-filter" showClear />;
    }

    const statusFilterTemplate = (options) => {
        return <Dropdown value={options.value} options={statuses} onChange={(e) => options.filterCallback(e.value, options.index)} itemTemplate={statusItemTemplate} placeholder="Select a Status" className="p-column-filter" showClear />;
    }

    const categoryFilterTemplate = (options) => {
        return <Dropdown value={options.value} options={categories} onChange={(e) => options.filterCallback(e.value, options.index)} itemTemplate={categoryItemTemplate} placeholder="Select a Category" className="p-column-filter" showClear />;
    }

    const statusItemTemplate = (option) => {
        return <span className={`customer-badge status-${option}`}>{option}</span>;
    }

    const priorityItemTemplate = (option) => {
        return <span className={`customer-badge priority-${option}`}>{option}</span>;
    }

    const categoryItemTemplate = (option) => {
        return <span>{option}</span>;
    }   

    const approvedBodyTemplate = (rowData) => {
        return <i className={classNames('pi', { 'text-green-500 pi-check-circle': rowData.approved, 'text-pink-500 pi-times-circle': !rowData.approved })}></i>;
    }

    const approvedFilterTemplate = (options) => {
        return <TriStateCheckbox value={options.value} onChange={(e) => options.filterCallback(e.value)} />
    }*/

    const showAlert = () => {
        setDisplayConfirmation2(false);
         getAllCenters()
    }
    const confirmationDialogFooter2 = (
        <>
          <div className="grid formgrid">
            <Button type="button" label="OK" onClick={showAlert} className="mr-2 mb-2" />
          </div>
        </>
      );
    useEffect(() => {
    }, []);



    const openBodyTemplate = (rowData) => {
        function editButtonClicked (){
            let id = "?id=" + rowData.id;
            navigate({pathname:'/edit-center', search: id });
        }
        function deleteButtonClicked (){
            let id = rowData.id;
                    return axiosPrivate.post("http://localhost:8090/main/api/delete-center/" + id, {
                headers: {
                    Authorization: 'Bearer ' + auth?.accessToken
                }
                }).then((response) => {
                    setDisplayConfirmation2(true);

                }).catch(error => console.error(`${error}`));
        }
        return <div><Button label="Edit" className="p-button-raised p-button-info mr-2 mb-2" onClick={editButtonClicked}/>
        <Button label="Delete" className="p-button-raised p-button-info mr-2 mb-2" icon = "pi pi-trash" onClick={deleteButtonClicked}/>
        <Dialog header="Center is deleted!" visible={displayConfirmation2} onHide={() => {setDisplayConfirmation2(false)}} style={{ width: '350px' }} modal footer={confirmationDialogFooter2}>
            <div className="flex align-items-center justify-content-center">
                <i className="pi pi-check-circle mr-3" style={{ fontSize: '2rem' }} />
            </div>
        </Dialog>   
        </div>;
    }

/*    const dateBodyTemplateCreated = (rowData) => {
        return formatDate(rowData.created);
    }*/

    return (
        <div className="grid table-demo">
            <div className="col-12">
                <div className="card">
                    <h2 style={{textAlign: "center"}}>Centers</h2>
                    <DataTable value={centers} paginator className="p-datatable-gridlines" showGridlines rows={10}
                        dataKey="id" responsiveLayout="scroll"
                          emptyMessage="No centers found.">
                        <Column field="city" header="City" style={{ minWidth: '12rem' }} />
                        <Column field="address" header="Address" style={{ minWidth: '12rem' }} />
                        <Column field="phoneNumber" header="Phone number" style={{ minWidth: '12rem' }}/>
                        <Column field="capacity" header="Capacity" style={{ minWidth: '12rem' }}/>
                        <Column field="petNo" header="Number of pets" style={{ minWidth: '12rem' }} />
                        <Column bodyClassName="text-center" style={{ minWidth: '8rem' }} body={openBodyTemplate}/>
                    </DataTable>
                </div>
            </div>
        </div>
    );
}

const comparisonFn = function (prevProps, nextProps) {
    return prevProps.location.pathname === nextProps.location.pathname;
};

export default React.memo(Centers, comparisonFn);