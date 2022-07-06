import React, { useState, useEffect, useCallback } from 'react';
import { FilterMatchMode, FilterOperator } from 'primereact/api';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Button } from 'primereact/button';
import axios from 'axios';
import { Dialog } from 'primereact/dialog';
import useAuth from "../hooks/useAuth";
import { Dropdown } from 'primereact/dropdown';
import { InputText } from 'primereact/inputtext';
import { MultiSelect } from 'primereact/multiselect';
import {useLocation, useNavigate } from 'react-router-dom';
import { Checkbox } from 'primereact/checkbox';
import useAxiosPrivate from "../hooks/useAxiosPrivate";


const Users = () => {

    const { auth } = useAuth();
    const [users, setUsers] = useState(null);
    const [displayConfirmation2, setDisplayConfirmation2] = useState(false);
    const [search, setSearch] = useState('');
    const [data, setData] = useState('')
    const [roleKey, setRoleKey] = useState(null)
    const [checkboxValue, setCheckboxValue] = useState([]);
    const selectedValue = [...checkboxValue]
    const axiosPrivate = useAxiosPrivate();

    const navigate = useNavigate();

    const getAllUsers = () => {
      return axiosPrivate.get("http://localhost:8090/user/api/users", {
        headers: {
          Authorization: 'Bearer ' + auth?.accessToken
        }
      }).then((response) => {
          setUsers(response.data);
          setData(response.data)
          console.log(users)
          console.log(response.data)
        }).catch(error => console.error("${error}"));
      }
  
      const roles = [
        'ROLE_USER',
        'EMPLOYEE','ALL'
    ];

    const onCheckboxChange = (e) => {
      if (e.checked) {
          selectedValue.push(e.value);
      } else {
          selectedValue.splice(selectedValue.indexOf(e.value), 1);
      }
      setCheckboxValue(selectedValue);
      setData(users.filter((item) =>
      (selectedValue.includes('ROLE_EMPLOYEE') && item.role == 'ROLE_EMPLOYEE') ||
      (selectedValue.includes('ROLE_USER') && item.role == 'ROLE_USER') ||
      (selectedValue.includes('ROLE_ADMIN') && item.role == 'ROLE_ADMIN')
      ))
  };
    

  useEffect(() => {
    selectedValue.push('ROLE_ADMIN')
    selectedValue.push('ROLE_EMPLOYEE')
    selectedValue.push('ROLE_USER')
    setCheckboxValue(selectedValue);
    getAllUsers();
  }, []); 

  const handleSearch = (e) => {
    setSearch(e.target.value)
    setData(users.filter((item) =>
      search == '' || item.firstName.toLowerCase().includes(search.toLowerCase()) ||
      item.lastName.toLowerCase().includes(search.toLowerCase()) ||
      item.username.toLowerCase().includes(search.toLowerCase()) ||
      item.email.toLowerCase().includes(search.toLowerCase())
      ))
  }

   const showAlert = () => {
        console.log("vildana")
        setDisplayConfirmation2(false);
         getAllUsers();
    }

    const confirmationDialogFooter2 = (
        <>
          <div className="grid formgrid">
            <Button type="button" label="OK" onClick={showAlert} className="mr-2 mb-2" />
          </div>
        </>
      );

  
    
    
    const openBodyTemplate = (rowData) => {
        function editButtonClicked (){
            let id = "?id=" + rowData.uuid;
            console.log(id)
            navigate({pathname:'/edit-user', search: id });
        }
        function deleteButtonClicked (){
            /*let id = rowData.id;
            return axios.delete("http://localhost:8080/api/users/" + id, {
                headers: {
                    
                }
                }).then(() => {
                    setDisplayConfirmation2(true)

                }).catch(error => console.error("${error}"));*/
                console.log(search);
                console.log(users)
        }
        return <div> {rowData.role == 'ROLE_EMPLOYEE' && <Button label="Edit" className="p-button-raised p-button-info mr-2 mb-2" onClick={editButtonClicked}/>}
        {(rowData.role == 'ROLE_EMPLOYEE' || rowData.role == 'ROLE_USER') && <Button label="Delete" className="p-button-raised p-button-info mr-2 mb-2" icon = "pi pi-trash" onClick={deleteButtonClicked}/>}
       <Dialog header="User is deleted!" visible={displayConfirmation2} onHide={() => {setDisplayConfirmation2(false)}} style={{ width: '350px' }} modal footer={confirmationDialogFooter2}>
            <div className="flex align-items-center justify-content-center">
                <i className="pi pi-check-circle mr-3" style={{ fontSize: '2rem' }} />
            </div>
        </Dialog>   
        </div>;
    }

     const openNewUser = (event) => {
        navigate('/add-employee');
      };

      
    return (
        <div className="grid table-demo">
            <Button label="New user" className="mr-2 mb-2" onClick={openNewUser}></Button>
            <br></br>
            
            <h5></h5>
            <div className="col-12">
            <div className="col-12 mb-2 lg:col-4 lg:mb-0">
                            <span className="p-input-icon-right">
                                <InputText type="text" value={search} placeholder="Search" onChange={handleSearch} />
                                <i className="pi pi-search" />
                            </span>
                        </div>
                        <div className="grid" style={{"width":"50%"}}>
                        <div className="col-12 md:col-4">
                            <div className="field-checkbox">
                                <Checkbox inputId="checkOption1" name="option" value="ROLE_ADMIN" checked={checkboxValue.indexOf('ROLE_ADMIN') !== -1} onChange={onCheckboxChange} />
                                <label htmlFor="checkOption1">ADMIN</label>
                            </div>
                        </div>
                        <div className="col-12 md:col-4">
                            <div className="field-checkbox">
                                <Checkbox inputId="checkOption2" name="option" value="ROLE_EMPLOYEE" checked={checkboxValue.indexOf('ROLE_EMPLOYEE') !== -1} onChange={onCheckboxChange} />
                                <label htmlFor="checkOption2">EMPLOYEES</label>
                            </div>
                        </div>
                        <div className="col-12 md:col-4">
                            <div className="field-checkbox">
                                <Checkbox inputId="checkOption3" name="option" value="ROLE_USER" checked={checkboxValue.indexOf('ROLE_USER') !== -1} onChange={onCheckboxChange} />
                                <label htmlFor="checkOption3">USERS</label>
                            </div>
                        </div>
                    </div>
                        <div className="card">
                    <h2 style={{textAlign: "center"}}>Users</h2>
                    <DataTable value={data} paginator className="p-datatable-gridlines" showGridlines rows={10}
                        dataKey="id" responsiveLayout="scroll"
                          emptyMessage="No users found.">
                        <Column field="username" header="Username" style={{ minWidth: '12rem' }} />
                        <Column field="firstName" header="First Name" style={{ minWidth: '12rem' }} />
                        <Column field="lastName" header="LastName" style={{ minWidth: '10rem' }}/>
                        <Column field="email" header="Email" style={{ minWidth: '12rem' }}/>
                        <Column field="role" header="Role" style={{ minWidth: '12rem' }}/>
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

export default React.memo(Users, comparisonFn);