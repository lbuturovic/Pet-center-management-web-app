import React, { useState, useRef, useEffect } from 'react';
import { InputText } from 'primereact/inputtext';
import { InputTextarea } from 'primereact/inputtextarea';
import { Dropdown } from 'primereact/dropdown';
import { Slider } from "@mui/material";
import { FormControl, FormLabel, FormControlLabel, Radio, RadioGroup } from "@mui/material";
import { Dialog } from 'primereact/dialog';
import { Button } from 'primereact/button';
import axios from "axios";
import { useLocation, useNavigate } from 'react-router-dom';
import useAuth from "../hooks/useAuth";
import useAxiosPrivate from "../hooks/useAxiosPrivate";

export const AddService = () => {
  const [type, setType] = useState('DAILY');
  const [name, setName] = useState('');
  const [price, setPrice] = useState(1);
  const [duration, setDuration] = useState(1);
  const [displayConfirmation, setDisplayConfirmation] = useState(false);
  const [displayConfirmation2, setDisplayConfirmation2] = useState(false);
  const url = "http://localhost:8090/main/api/service";
  const urlCenters = "http://localhost:8090/main/api/centers";
  const [allCenters, setAllCenters] = useState([]);
  const [centerValue, setCenterValue] = useState();
  const navigate = useNavigate();
  const axiosPrivate = useAxiosPrivate();

  const { auth } = useAuth();
  const location = useLocation();
  const centers = [];
  const center1 = {
    id: 1
  };

  centers.push(center1);

  const getAllCenters = () => {
    axiosPrivate.get(urlCenters, {
      headers: {
        Authorization: 'Bearer ' + auth?.accessToken
      }
    }).then((response) => {
   /*   let centers = []
      for (let i=0; i++; i<response.data.lenght){
          let c = response.data[i];
          c.name = c.city + "-" + c.address;
          centers.push(c);
      }
      setCenterValue(centers[0].id);*/
      setAllCenters(response.data);
      setCenterValue(response.data[0].id);
    }).catch(error => console.error("${error}"));
  }

  useEffect(() => {
    getAllCenters();
}, []); 

  async function handleSubmit(event) {
    event.preventDefault();
    await axiosPrivate.post(url, {
      name: name,
      price: price,
      duration: duration,
      type: type,
      centers: allCenters

    }, {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + auth?.accessToken
      }
    })
      .then(res => {
        handleCancelConfirmation2();
      }).catch(function (error) {
        if (error.response) {

          console.log(error.response.data);
          console.log(error.response.status);
          console.log(error.response.headers);
        } else if (error.request) {
          console.log(error.request);
        } else {
          console.log('Error', error.message);
        }

      });
  }




  const form = useRef(null);

  function handleShowServices(event) {
    event.preventDefault();
    navigate('/all-services');
  };

  const serviceTypes = [
    { name: 'Daily', id: 'DAILY' },
    { name: 'Hourly', id: 'HOURLY' },
    { name: 'Onetime', id: 'ONETIME' }
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
        <Button type="button" label="All services" icon="pi pi-list" onClick={handleShowServices} className="p-button-text" />
        <Button type="button" label="New service" icon="pi pi-plus" onClick={() => setDisplayConfirmation2(false)} className="p-button-text" />
      </div>
    </>
  );




  const styles = {

    marginLeft: '33%',
    marginRight: 'auto',

  };


  function handleShowServices(event) {
    event.preventDefault();
    navigate('/all-pets');
  };

  function handleCancelConfirmation2() {

    setDisplayConfirmation(false);
    //form.current.reset(); //this will reset all the inputs in the form
    setDisplayConfirmation2(true);
  }

  function handleCancelConfirmation(event) {
    event.preventDefault();

    setDisplayConfirmation(false);

  }



  return (
    <div className="grid p-fluid" style={styles}>
      <div className="col-12 lg:col-6">
        <h2>Create new service</h2>
        <div className="card">
          <h5>Service type</h5>
          <form id="pet_form" ref={form} onSubmit={handleSubmit}>
            <Dropdown value={type} onChange={(e) => setType(e.value)} options={serviceTypes} optionLabel="name" optionValue='id' name='type' placeholder="Types" />

            <h5>Name</h5>
            <InputTextarea placeholder="Service name" id='name' name='name' value={name} maxLength={275}
              onChange={(e) => setName(e.target.value)} autoResize rows="5" cols="30" />
            <h5></h5>
            <div>
              <h5>Price</h5>
              <InputText style={{ width: "20%" }} id="price" type="number" placeholder='price' name="price" value={price} onChange={(e) => setPrice(e.target.value)} />
              <h5></h5>
            </div>
            <h5>Duration</h5>
            <InputText style={{ width: "20%" }} id="duration" type="number" placeholder='duration' name="duration" value={duration} onChange={(e) => setDuration(e.target.value)} />
            <h5></h5>
            <div style={{ display: "flex" }}>
              <Button label="Create" type='submit' className="mr-2 mb-2"></Button>

              <Dialog header="Service is saved!" visible={displayConfirmation2} onHide={() => setDisplayConfirmation2(false)} style={{ width: '350px' }} modal footer={confirmationDialogFooter2}>
                <div className="flex align-items-center justify-content-center">
                  <i className="pi pi-check-circle mr-3" style={{ fontSize: '2rem' }} />
                  <span>Do you want to see all services or create another one?</span>
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

export default React.memo(AddService, comparisonFn);
