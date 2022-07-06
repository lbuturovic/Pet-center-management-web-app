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



const Services = () => {
    const { auth } = useAuth();
    const [services, setServices] = useState(null);
    const axiosPrivate = useAxiosPrivate();
    //const [loading1, setLoading1] = useState(true);
    const [displayConfirmation2, setDisplayConfirmation2] = useState(false);
    const navigate = useNavigate();

    const getAllServices = () => {
        return axiosPrivate.get("http://localhost:8090/main/api/services", {
          headers: {
            Authorization: 'Bearer ' + auth?.accessToken
          }
        }).then((response) => {
            setServices(response.data);
            console.log(services);
          }).catch(error => console.error(`${error}`));
        }
    

    useEffect(() => {
        //setLoading1(false);
        getAllServices();

    }, []); 

    const showAlert = () => {
        setDisplayConfirmation2(false);
         getAllServices()
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
            navigate({pathname:'/edit-service', search: id });
        }
        function deleteButtonClicked (){
            let id = rowData.id;
                    return axiosPrivate.post("http://localhost:8090/main/api/delete-service/" + id, {
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


    return (
        <div className="grid table-demo">
            <div className="col-12">
                <div className="card">
                    <h2 style={{textAlign: "center"}}>Services</h2>
                    <DataTable value={services} paginator className="p-datatable-gridlines" showGridlines rows={10}
                        dataKey="id" responsiveLayout="scroll"
                          emptyMessage="No services found.">
                        <Column field="name" header="Name" style={{ minWidth: '12rem' }} />
                        <Column field="price" header="Price" style={{ minWidth: '12rem' }} />
                        <Column field="duration" header="Duration" style={{ minWidth: '12rem' }}/>
                        <Column field="type" header="Service type" style={{ minWidth: '12rem' }}/>
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

export default React.memo(Services, comparisonFn);