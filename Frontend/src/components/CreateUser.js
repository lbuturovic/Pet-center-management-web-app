import React, { useEffect, useState, useRef } from 'react';
import { InputText } from 'primereact/inputtext';
import { Dropdown } from 'primereact/dropdown';
import axios from "axios";
import { Dialog } from 'primereact/dialog';
import { Button } from 'primereact/button';
import { useNavigate } from 'react-router-dom';
import useAxiosPrivate from "../hooks/useAxiosPrivate";

export const CreateUser = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [firstname, setFirstname] = useState('');
  const [lastname, setLastname] = useState('');
  const [role, setRole] = useState('admin');
  const [email,setEmail] = useState('')
  const [errorMsg,setErrorMsg] = useState('')
  const [isUsernameOk, setIsUsernameOk] = useState(true);
  const [isPasswordOk, setIsPasswordOk] = useState(true);
  const [displayConfirmation, setDisplayConfirmation] = useState(false);
  const [displayConfirmation2, setDisplayConfirmation2] = useState(false);
  const [err, setErr] = useState(false);   
  const axiosPrivate = useAxiosPrivate();

  const form = useRef(null);
  const navigate = useNavigate();


  const roleValues = [
    { name: 'CLIENT', code: 'cl' },
    { name: 'SERVICE', code: 'sd' },
    { name: 'ADMIN', code: 'admin' },
    { name: 'DEV', code: 'dev' },
    { name: 'APPROVER', code: 'app' }
  ];



  
  const confirmationDialogFooter = (
    <>
      <Button type="button" label="No" icon="pi pi-times" onClick={() => setDisplayConfirmation(false)} className="p-button-text" />
      <Button type="button" label="Yes" icon="pi pi-check" onClick={handleCancelConfirmation} className="p-button-text" autoFocus />
    </>
  );

  const confirmationDialogFooter2 = (
    <>
      <div className="grid formgrid">
        <Button type="button" label="New user" icon="pi pi-plus" onClick={() => setDisplayConfirmation2(false)} className="p-button-text" />
      </div>
    </>
  );
 


  const styles = {

    marginLeft: '33%',
    marginRight: 'auto',
  };

  

  function handleRegistration(event) {
    event.preventDefault();
    //history.push('/registration');
  };

  function handleCancelConfirmation(event) {
    event.preventDefault();
    setUsername('');
    setPassword('');
    setFirstname('');
    setLastname('');
    setEmail('');
    setIsUsernameOk(true);
    //form.current.reset(); //this will reset all the inputs in the form
    setDisplayConfirmation(false);

  }

  function handleCancelConfirmation2() {
    setUsername('');
    setPassword('');
    setFirstname('');
    setLastname('');
    setEmail('');
    setIsUsernameOk(true);
    //form.current.reset(); //this will reset all the inputs in the form
    setDisplayConfirmation2(true);
  }

  const changeUsername = (e) => {
    e.preventDefault();
    setUsername(e.target.value);
    if (e.target.value.trim().length === 0) setIsUsernameOk(false);
    else setIsUsernameOk(true);
    setErr(false);
  }

  const changePassword = (e) => {
    e.preventDefault();
    setPassword(e.target.value);
    if (e.target.value.trim().length === 0) setIsPasswordOk(false);
    else setIsPasswordOk(true);
    setErr(false);
  }
  const changeEmail = (e) => {
    e.preventDefault();
    setEmail(e.target.value);
    setErr(false);
  }
  const changeFirstname = (e) => {
    e.preventDefault();
    setFirstname(e.target.value);
    setErr(false);
    
  }
  const changeLastname = (e) => {
    e.preventDefault();
    setLastname(e.target.value);
    setErr(false);
  }

  async function handleSubmit(event) {
    event.preventDefault();
    var ok = true;
    if (username.trim().length === 0) {
      setIsUsernameOk(false);
      ok = false;
    }
    if (password.trim().length === 0) {
      setIsPasswordOk(false);
      ok = false;
    }

    const roles = [];
    roles.push(role);
    if (!ok) return;
    setErr(false);
    var url = 'http://localhost:8080/api/auth/signup';
    await axiosPrivate.post(url, {
      username: username,
      password: password,
      firstName: firstname,
      lastName: lastname,
      role: roles,
      email: email
    }, {
      headers: {
        'Content-Type': 'application/json',
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
        <h2>Create new user</h2>
        <div className="card">
          <h5>Username*</h5>
          <form id="change_form" ref={form} onSubmit={handleSubmit}>

            <InputText id="username" type="text" placeholder='Username' name="username" value={username} onChange={changeUsername} maxLength={57} className={isUsernameOk ? "" : "p-invalid"} aria-describedby="subject-help" />
            {isUsernameOk ? ("") : (<small id="subject-help" className="p-error">Username is mandatory!</small>)}
            <h5>Password*</h5>
            <InputText id="password" type="password" placeholder='Password' value={password} name="password"  onChange={changePassword} maxLength={57} className={isPasswordOk ? "" : "p-invalid"} aria-describedby="subject-help" />
            {isPasswordOk ? ("") : (<small id="subject-help" className="p-error">Password is mandatory!</small>)}
            
            <h5>Role</h5>
            <Dropdown value={role} onChange={(e) => setRole(e.value)} options={roleValues} optionLabel="name" optionValue='code' name='role' placeholder="Select"/>
   
            <h5>First name</h5>   
            <InputText id="firstname" type="text" placeholder='First name' name="firstname" value={firstname} onChange={changeFirstname} maxLength={57}  aria-describedby="subject-help" />
            <h5>Last name</h5>
            <InputText id="lastname" type="text" placeholder='Last name' name="lastname" value={lastname} onChange={changeLastname} maxLength={57} aria-describedby="subject-help" />
            <h5>Email</h5>
            <InputText id="email" type="text" placeholder='Email' name="email" value={email} onChange={changeEmail} maxLength={57}  aria-describedby="subject-help" />
            {errorMsg ? (<small id="subject-help" className="p-error">Greska</small>) : ("") }
            
            <div style={{ display: "flex" }}>
              <Button label="Create" type='submit' className="mr-2 mb-2"></Button>

              <Dialog header="User is saved!" visible={displayConfirmation2} onHide={() => setDisplayConfirmation2(false)} style={{ width: '350px' }} modal footer={confirmationDialogFooter2}>
                <div className="flex align-items-center justify-content-center">
                  <i className="pi pi-check-circle mr-3" style={{ fontSize: '2rem' }} />
                </div>
              </Dialog>
              <Button label="Cancel" className="mr-2 mb-2" onClick={() => setDisplayConfirmation(true)}></Button>
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

export default React.memo(CreateUser, comparisonFn);
