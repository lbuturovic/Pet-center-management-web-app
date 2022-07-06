import React, { useState, useRef, useCallback } from 'react';
import { InputText } from 'primereact/inputtext';
import { InputTextarea } from 'primereact/inputtextarea';
import { Dropdown } from 'primereact/dropdown';
import { Slider } from "@mui/material";
import { FormControl, FormLabel, FormControlLabel, Radio, RadioGroup } from "@mui/material";
import { Dialog } from 'primereact/dialog';
import { Button } from 'primereact/button';
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import {useLocation, useNavigate } from 'react-router-dom';
import useAuth from "../hooks/useAuth";

export const AddDesiredPet = () => {
    const [vrsta, setVrsta] = useState('DOG');
    const [rasa, setRasa] = useState('');
    const [dob, setDob] = useState('JUNIOR');
    const [displayConfirmation, setDisplayConfirmation] = useState(false);
    const [displayConfirmation2, setDisplayConfirmation2] = useState(false); 
    const url = "http://localhost:8090/desired/desired-pet/";
    const navigate = useNavigate();
    const axiosPrivate = useAxiosPrivate();

    const { auth } = useAuth();
    const location = useLocation();

    async function handleSubmit(event) {
      event.preventDefault();
      await axiosPrivate.post(url + auth?.uuid, {
        species: vrsta,
        race: rasa,
        age: dob
      }, {
        headers: {
          'Authorization': 'Bearer ' + auth?.accessToken,
          'Content-Type': 'application/json'
        }
      })
        .then(res => {
            handleCancelConfirmation2();
        }).catch(function (error) {
          if (error.response) {
            handleCancelConfirmation2();
            console.log(error.response.data);
            console.log(error.response.status);
            console.log(error.response.headers);
          } else if (error.request) {
            handleCancelConfirmation2();
            console.log(error.request);
          } else {
            handleCancelConfirmation2();
            console.log('Error', error.message);
          }
  
        });
      }    



 
  const form = useRef(null);


  const vrste = [
    { name: 'Dog', id: 'DOG' },
    { name: 'Cat', id: 'CAT' }
  ];

  const dobi = [
    { name: 'Newborn', id: 'NEWBORN' },
    { name: 'Junior', id: 'JUNIOR' },
    { name: 'Preteen', id: 'PRETEEN' },
    { name: 'Teen', id: 'TEEN' },
    { name: 'Postteen', id: 'POSTTEEN' },
    { name: 'Fullgrown', id: 'FULLGROWN' }
  ];

  const confirmationDialogFooter = (
    <>
      <Button type="button" label="No" icon="pi pi-times" onClick={() => setDisplayConfirmation(false)} className="p-button-text" />
      <Button type="button" label="Yes" icon="pi pi-check" onClick={handleCancelConfirmation} className="p-button-text" autoFocus />
    </>
  );

  const confirmationDialogFooter2 = (
    <>
    </>
  );
 



  const styles = {

    marginLeft: '33%',
    marginRight: 'auto',
  
  };


  function handleCancelConfirmation2() {
    setVrsta('CAT');
    setRasa('');
    setDob('JUNIOR');
    setDisplayConfirmation(false);
    setDisplayConfirmation2(true);
  }

  function handleCancelConfirmation(event) {
    event.preventDefault();
    setVrsta('CAT');
    setRasa('');
    setDob('JUNIOR');
    setDisplayConfirmation(false);

  }



  return (
    <div className="grid p-fluid" style={styles}>
      <div className="col-12 lg:col-6">
        <h2>Create new desired pet</h2>
        <div className="card">
          <h5>Species</h5>
          <form id="pet_form" ref={form} onSubmit={handleSubmit}>
            <Dropdown value={vrsta}  onChange={(e) => setVrsta(e.value)} options={vrste} optionLabel="name" optionValue='id' name='vrsta' placeholder="Species" />
           
            <h5>Race</h5>
            <InputText id="rasa" type="text" placeholder='Race' name="name" value={rasa} onChange={(e) => setRasa(e.target.value)} maxLength={57}/>  
              
            <h5>Age</h5>
            <Dropdown style={{marginBottom:'10px'}} value={dob} onChange={(e) => setDob(e.value)} options={dobi} optionLabel="name" optionValue='id' name='dob' placeholder="Dob" />
            
            
            <div style={{ display: "flex" }}>
              <Button label="Create" type='submit' className="mr-2 mb-2"></Button>

              <Dialog header="Desired pet is saved!" visible={displayConfirmation2} onHide={() => setDisplayConfirmation2(false)} style={{ width: '350px' }} modal footer={confirmationDialogFooter2}>
               
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

export default React.memo(AddDesiredPet, comparisonFn);
