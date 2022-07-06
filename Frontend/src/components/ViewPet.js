import React, { useState, useEffect, useCallback } from 'react';
import { InputText } from 'primereact/inputtext';
import { InputTextarea } from 'primereact/inputtextarea';
import { Dropdown } from 'primereact/dropdown';
import { Button } from 'primereact/button';
import { RadioButton } from 'primereact/radiobutton';
import { Dialog } from 'primereact/dialog';
import axios from "axios";
import { useLocation, useNavigate } from 'react-router-dom';
import useAuth from "../hooks/useAuth";
import { FileUpload } from 'primereact/fileupload';
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import { Splitter, SplitterPanel } from 'primereact/splitter';
import { getRectCenter } from '@fullcalendar/core';
import TextField from '@mui/material/TextField';
import Stack from '@mui/material/Stack';
import { InputNumber } from 'primereact/inputnumber';
import { Calendar } from 'primereact/calendar';
import { Label } from '@material-ui/icons';

export const ViewPet = () => {
  const [vrsta, setVrsta] = useState('DOG');
  const [rasa, setRasa] = useState('');
  const [velicina, setVelicina] = useState('SMALL');
  const [name, setName] = useState('');
  const [bolesti, setBolesti] = useState('');
  const [weight, setWeight] = useState(10);
  const [dob, setDob] = useState('JUNIOR');
  const [gender, setGender] = useState('MALE');
  const [displayConfirmation2, setDisplayConfirmation2] = useState(false);
  const navigate = useNavigate();
  const [isRasaOk, setIsRasaOk] = useState(true);
  const [isBolestiOk, setIsBolestiOk] = useState(true);
  const [isNameOk, setIsNameOk] = useState(true);
  const [isWeightOk, setIsWeightOk] = useState(true);
  const [isDobOk, setIsDobOk] = useState(true);
  const [base64TextString, setBase64TextString] = useState('');
  const axiosPrivate = useAxiosPrivate();
  const [hour, setHour] = useState(8)
  const [minute, setMinute] = useState(0)
    const [idValue, setIdValue] = useState(null)
  const { auth } = useAuth();
  const location = useLocation();
  const [status, setStatus] = useState('')
  const [productDialog, setProductDialog] = useState(false);
  const [submitted, setSubmitted] = useState(false);
  const [id, setId] = useState('');
  const [centerId, setCenterId] = useState('')
  const [center, setCenter] = useState('')
const [date, setDate]= useState("2017-05-24T10:30");
const url2 = "http://localhost:8090/reservation/visit";


 const getPet = (id) => {
    setId(id)
    axiosPrivate.get('http://localhost:8090/main/api/pet/' + id, {
        headers: {
            Authorization: 'Bearer ' + auth?.accessToken
        }
    }).then((response) => {
        setVrsta(response.data.species);
        setVelicina(response.data.category);
        setBolesti(response.data.healthCondition);
        setRasa(response.data.race);
        setGender(response.data.gender);
        setWeight(response.data.weight)
        setDob(response.data.age)
        setBase64TextString(response.data.image)
        setName(response.data.name)
        if (response.data.status == 'ABANDONED')
        setStatus('Abandoned')
        else if (response.data.status == 'ADOPTED')
        setStatus('Adopted')
        setCenterId(response.data.center)
        getCenter(response.data.center)
    }).catch(error => console.error(`${error}`));
}

const getCenter = (id) => {
    console.log("ID "+ id)
    axiosPrivate.get('http://localhost:8090/main/api/center/' + id, {
        headers: {
            Authorization: 'Bearer ' + auth?.accessToken
        }
    }).then((response) => {
       setCenter(response.data.city + "-" + response.data.address)
    }).catch(error => console.error(`${error}`));
}
  

  useEffect(() => {
    const search = location.search;
    const id = new URLSearchParams(search).get('id');
    setIdValue(id);
    getPet(id);

}, [location]);



    

  const styles = {

    marginLeft: '33%',
    marginRight: 'auto',

  };

  async function saveVisit(event) {
    let d = '';
    if (hour<10)
      d = '0'+hour
    else
      d = hour
    let m = ''
    if (minute<30)
      m = '0'+minute
    else
      m = minute
    console.log(date);
    event.preventDefault();
    console.log(date + ' '+d + ':' + m +':' +':00')
    let formatDate = (date) => {
      let d = new Date(date)
      return '' + d.getFullYear() +'-' + (d.getMonth()+1).toString().padStart(2,'0') + '-' + d.getDate().toString().padStart(2,'0');
  }
  let newDate = formatDate(date)
  console.log(auth?.uuid)
  console.log(newDate + ' ' + d + ':' + m +':' +'00')
    await axios.post(url2+'/' + centerId + '/' + auth?.uuid, {
      startDate: newDate + ' ' + d + ':' + m +':' +'00'
      
    }, {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + auth?.accessToken
      }
    })
      .then(res => {
        setSubmitted(true);
        setProductDialog(false);
          //handleCancelConfirmation2();
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

  function handleShowPets(event) {
    event.preventDefault();
    navigate('/abandoned-pets');
  };

  
  const cancelClick = () => {
    navigate('/abandoned-pets')
  }

  const hideDialog = () => {
    setSubmitted(false);
    setProductDialog(false);
}

  const productDialogFooter = (
    <>
        <Button label="Cancel" icon="pi pi-times" className="p-button-text" onClick={hideDialog} />
        <Button label="Save" icon="pi pi-check" className="p-button-text" onClick={saveVisit} />
    </>
);





 return (
    <Splitter>
    <SplitterPanel>
    <div>
            <div className="card">
                <h2>View pet</h2>
                <div className="p-fluid grid formgrid">
                    <div className="field col-12 md:col-5">
                        <h5>Species</h5>
                        <InputText id="name" type="text" placeholder='Species' name="species" value={vrsta}
                            disabled = "true"
                          aria-describedby="subject-help" />
                    </div>
                    <div className="field col-12 md:col-5">
                        <h5>Health status</h5>
                        <InputTextarea placeholder="Health status" id='bolesti' name='bolesti' value={bolesti}
                        disabled = "true"
                        rows="5" cols="30" aria-describedby="subject-help" />
                    </div>
                    <div className="field col-12 md:col-5">
                        <h5>Race</h5>
                        <InputText id="rasa" type="text" placeholder='Race' name="name" value={rasa}
                        disabled = "true" aria-describedby="subject-help" />
                    </div>
                    <div className="field col-12 md:col-5">
                        <h5>Name</h5>
                        <InputText id="name" type="text" placeholder='Name' name="name" value={name}
                         aria-describedby="subject-help" 
                         disabled = "true"/>
                    </div>
                    <div className="field col-12 md:col-5">
                    <h5>Size</h5>
                    <InputText id="size" type="text" placeholder='Size' name="size" value={velicina} aria-describedby="subject-help"
                    disabled = "true"  />                                
                    </div>
<div className="field col-12 md:col-5">
<h5>Age</h5>
<InputText id="age" type="text" placeholder='Age' name="age" value={dob} aria-describedby="subject-help"
                    disabled = "true"  /> 
</div>
<div className="field col-12 md:col-5">
<h5>Gender</h5>
<InputText id="gender" type="text" placeholder='Gender' name="gender" value={gender} aria-describedby="subject-help"
                    disabled = "true"  /> 
</div>
<div className="field col-12 md:col-5">
<h5>Weight</h5>
            <InputText id="weight" type="number" placeholder='weight' name="weight" value={weight} 
            disabled = "true"
            aria-describedby="subject-help" />
</div>
   </div>
                    <span className="p-buttonset">
                        <Button label="Visit" icon="pi pi-plus" onClick={() => setProductDialog(true)}/>
                        <Dialog visible={productDialog} style={{ width: '450px' }} header="Visit pet" modal className="p-fluid" footer={productDialogFooter} onHide={hideDialog}>
                        {base64TextString && <img src={`data:image/jpeg;base64,${base64TextString}`} width="150" className="mt-0 mx-auto mb-5 block shadow-2" />}
                        <div className="field">
                            <h3>Center</h3>
                            <InputText id="center" value={center} disabled = "true" />
                        </div>
                        <div className="field"> 
                        <h3>Date</h3>
                        <Calendar minDate={new Date()} dateFormat="dd/mm/yy" disabledDays={[6]} value={date} onChange={(e) => setDate(e.value)}></Calendar>
                        </div> 
                        <div className="field">
                          <h3>Time</h3>
<InputNumber value={hour} onValueChange={(e) => setHour(e.value)} mode="decimal" showButtons buttonLayout="vertical" style={{width: '4em'}}
    decrementButtonClassName="p-button-secondary" incrementButtonClassName="p-button-secondary" incrementButtonIcon="pi pi-plus" decrementButtonIcon="pi pi-minus" step={1} min = {8} max = {16}/>
  <InputNumber  value={minute} onValueChange={(e) => setMinute(e.value)} mode="decimal" showButtons buttonLayout="vertical" style={{width: '4em'}}
    decrementButtonClassName="p-button-secondary" incrementButtonClassName="p-button-secondary" incrementButtonIcon="pi pi-plus" decrementButtonIcon="pi pi-minus" step={30} min = {0} max = {30}/>
                          </div>                        
                    </Dialog>
                        <Button label="Cancel" icon="pi pi-times" onClick={cancelClick} />
                    </span>
                </div>          
        </div>
        </SplitterPanel>
        <SplitterPanel>
        <div className='card'>
        <img style = {{"height":"512px"}} src={`data:image/jpeg;base64,${base64TextString}`} onError={(e) => e.target.src='https://www.pngall.com/wp-content/uploads/4/German-Shepherd-Puppy-PNG-Picture.png'}alt={name} />
        </div>
        </SplitterPanel>
    </Splitter>

  )
}

const comparisonFn = function (prevProps, nextProps) {
  return (prevProps.location.pathname === nextProps.location.pathname) && (prevProps.colorMode === nextProps.colorMode);
};

export default React.memo(ViewPet, comparisonFn);
