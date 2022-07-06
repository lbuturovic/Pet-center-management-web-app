import React, {useState, useEffect, useRef} from 'react';
import { Dropdown } from 'primereact/dropdown';
import TextField from '@mui/material/TextField';
import Stack from '@mui/material/Stack';
import axios from "axios";
import useAuth from "../hooks/useAuth";
import { InputText } from 'primereact/inputtext';
import { InputTextarea } from 'primereact/inputtextarea';
import { Dialog } from 'primereact/dialog';
import { Button } from 'primereact/button';
import {useLocation, useNavigate } from 'react-router-dom';
import useAxiosPrivate from "../hooks/useAxiosPrivate";

const Reservation = () => {  
   const [services, setServices] = useState([]);
   const [service, setService] = useState();
   const [date, setDate]= useState("2021-06-16T10:30");
   const [centers, setCenters]= useState([]);
   const [center, setCenter] = useState();
   const [displayConfirmation, setDisplayConfirmation] = useState(false);
   const [displayConfirmation2, setDisplayConfirmation2] = useState(false); 
   const url = "http://localhost:8090/main/api/services/center/";
   const url2 = "http://localhost:8090/reservation/reservation";
   const urlCenter = "http://localhost:8090/main/api/centers";
   const { auth } = useAuth();
   const [loading1, setLoading1] = useState(true);
   const navigate = useNavigate();
   const axiosPrivate = useAxiosPrivate();

   const getAllServices = () =>{ 
    return axiosPrivate.get(url + center, {
        headers: {
            'Authorization': 'Bearer ' + auth?.accessToken
        }
}).then(res => {
            setServices(res.data)
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
const getAllCenters = () =>{ 
  return axios.get(urlCenter, {
      headers: {
          'Authorization': 'Bearer ' + auth?.accessToken
      }
}).then(res => {
          setCenters(res.data)
          setCenter(centers.at(1))
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

const styles = {

    marginLeft: '33%',
    marginRight: 'auto',
  
  };

useEffect(() => {
    getAllCenters();
    setLoading1(false);
}, []);

useEffect(() => {
  if (center!== undefined) getAllServices();
  setLoading1(false);
}, [center]);

function handleCancelConfirmation2() {
    
    setDisplayConfirmation(false);
    setDisplayConfirmation2(true);
  }

async function handleSubmit(event) {
    console.log(date);
    event.preventDefault();
    await axiosPrivate.post(url2 + "/user/" + auth?.uuid, {
      "startDate": date.replace('T',' ')+':00',
      "center":{
        id:center
      },
      "service":{
      
        id: service
      } 
      
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
    function handleShowServices(event) {
        event.preventDefault();
        navigate('/all-reservations');
    };

      function handleCancelConfirmation2() {
    
        setDisplayConfirmation(false);
  
        setDisplayConfirmation2(true);
      }
    
      function handleCancelConfirmation(event) {
        event.preventDefault();
        
        setDisplayConfirmation(false);
    
      }  

    
  const confirmationDialogFooter = (
    <>
      <Button type="button" label="No" icon="pi pi-times" onClick={() => setDisplayConfirmation(false)} className="p-button-text" />
      <Button type="button" label="Yes" icon="pi pi-check" onClick={handleCancelConfirmation} className="p-button-text" autoFocus />
    </>
  );

  const confirmationDialogFooter2 = (
    <>
      <div className="grid formgrid">
        <Button type="button" label="New" icon="pi pi-plus" onClick={() => setDisplayConfirmation2(false)} className="p-button-text" />
      </div>
    </>
  );


      
  return (
    <div className="grid p-fluid" style={styles}>
      <div className="col-12 lg:col-6">
        <h2>Make new reservation</h2>
        <div className="card">
             <h2>Center</h2>
            <Dropdown value={center}  onChange={(e) => setCenter(e.value)} options={centers} optionLabel="address" optionValue='id' name='center' placeholder="Center" />  
            <h2>Service</h2>
            <Dropdown value={service}  onChange={(e) => setService(e.value)} options={services} optionLabel="name" optionValue='id' name='service' placeholder="Service" />  
         
      <Stack component="form" noValidate spacing={3}>
        <TextField
            style={{marginTop:'15px'}}
            id="datetime-local"
            label="Next appointment"
            type="datetime-local"
            inputProps={{
                min: new Date().toISOString().slice(0, 16),
            }}
            defaultValue="2017-05-24T10:30"
            sx={{ width: 250 }}
            InputLabelProps={{
            shrink: true,
            }}
            onChange={(e) => setDate(e.value)}
        />
    </Stack>
    </div>
          <form id="reservation_form" ref={form} onSubmit={handleSubmit}>
            <div style={{ display: "flex" }}>
              <Button label="Create" type='submit' className="mr-2 mb-2"></Button>

              <Dialog header="Reservation is saved!" visible={displayConfirmation2} onHide={() => setDisplayConfirmation2(false)} style={{ width: '350px' }} modal footer={confirmationDialogFooter2}>
                <div className="flex align-items-center justify-content-center">
                  <i className="pi pi-check-circle mr-3" style={{ fontSize: '2rem' }} />
                  <span>Do you want to create another one?</span>
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
  );
}

export default Reservation;