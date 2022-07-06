import React, { useState, useEffect, useCallback, useRef } from 'react';
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

export const EditPet = () => {
  const [vrsta, setVrsta] = useState('DOG');
  const [rasa, setRasa] = useState('');
  const [velicina, setVelicina] = useState('SMALL');
  const [name, setName] = useState('');
  const [bolesti, setBolesti] = useState('');
  const [weight, setWeight] = useState(10);
  const [dob, setDob] = useState('JUNIOR');
  const [gender, setGender] = useState('MALE');
  const [displayConfirmation, setDisplayConfirmation] = useState(false);
  const [displayConfirmation2, setDisplayConfirmation2] = useState(false);
  const url = "http://localhost:8090/main/api/pet";
  const navigate = useNavigate();
  const [isRasaOk, setIsRasaOk] = useState(true);
  const [isBolestiOk, setIsBolestiOk] = useState(true);
  const [isNameOk, setIsNameOk] = useState(true);
  const [isWeightOk, setIsWeightOk] = useState(true);
  const [isDobOk, setIsDobOk] = useState(true);
  const [base64TextString, setBase64TextString] = useState('');
  const axiosPrivate = useAxiosPrivate();
  const [idValue, setIdValue] = useState(null)
  const { auth } = useAuth();
  const location = useLocation();
  const urlCenter = 'http://localhost:8090/main/api/pet/';
  const [status, setStatus] = useState('')
  const [productDialog, setProductDialog] = useState(false);
  const [submitted, setSubmitted] = useState(false);
  const [centerValue, setCenterValue] = useState(null);
  const [id, setId] = useState('');
  const form = useRef(null);

  const getPet = (urlPet, id) => {
    setId(id)
    axios.get(urlCenter + id, {
      headers: {
        Authorization: 'Bearer ' + auth?.accessToken
      }
    }).then((response) => {
      setVrsta(response.data.species);
      setCenterValue(response.data.center);
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
    }).catch(error => console.error(`${error}`));
  }

  const changeRasa = (e) => {
    e.preventDefault();
    setRasa(e.target.value);
    if (e.target.value.trim().length === 0) setIsRasaOk(false);
    else setIsRasaOk(true);
  }

  const changeBolesti = (e) => {
    e.preventDefault();
    setBolesti(e.target.value);
    if (e.target.value.trim().length === 0) setIsBolestiOk(false);
    else setIsBolestiOk(true);
  }

  const changeWeight = (e) => {
    e.preventDefault();
    setWeight(e.target.value);
    if (e.target.value.trim().length === 0 || weight < 0.1 || weight > 100) setIsWeightOk(false);
    else setIsWeightOk(true);
  }

  const changeName = (e) => {
    e.preventDefault();
    setName(e.target.value);
    if (e.target.value.trim().length === 0) setIsNameOk(false);
    else setIsNameOk(true);
  }


  async function savePet(event) {
    event.preventDefault();
    var ok = true;
    if (rasa.trim().length === 0) {
      setIsRasaOk(false);
      ok = false;
    }
    if (bolesti.trim().length === 0) {
      setIsBolestiOk(false);
      ok = false;
    }
    if (name.trim().length === 0) {
      setIsNameOk(false);
      ok = false;
    }
    if (weight.length === 0 || weight < 0.1 || weight > 100) {
      setIsWeightOk(false);
      ok = false;
    }

    console.log(base64TextString)
    if (!ok) return;
    let enumStatus = '';
    if (status === 'Adopted')
      enumStatus = 'ADOPTED'
    if (status === 'Abandoned')
      enumStatus = 'ABANDONED'
    var url = 'http://localhost:8090/main/api/edit/pet/' + id;
    await axiosPrivate.post(url, {
      species: vrsta,
      race: rasa,
      weight: weight,
      name: name,
      healthCondition: bolesti,
      image: base64TextString,
      age: dob,
      gender: gender,
      status: enumStatus,
      category: velicina,
      center: centerValue
    }, {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + auth?.accessToken
      }
    })
      .then(res => {
        setDisplayConfirmation2(true)
        //handleCancelConfirmation();
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

  useEffect(() => {
    const search = location.search;
    const id = new URLSearchParams(search).get('id');
    setIdValue(id);
    getPet(urlCenter, id);

  }, [location]);







  const velicine = [
    { name: 'Big', id: 'BIG' },
    { name: 'Small', id: 'SMALL' },
    { name: 'Medium', id: 'MEDIUM' }
  ];

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
      <div className="grid formgrid">
        <Button type="button" label="All pets" icon="pi pi-list" onClick={handleShowPets} className="p-button-text" />
        <Button type="button" label="New pet" icon="pi pi-plus" onClick={() => setDisplayConfirmation2(false)} className="p-button-text" />
      </div>
    </>
  );

  const convertToBase64 = (file) => {
    return new Promise((resolve, reject) => {
      const fileReader = new FileReader();
      fileReader.readAsDataURL(file);
      fileReader.onload = () => {
        resolve(fileReader.result);
      };
      fileReader.onerror = (error) => {
        reject(error);
      };
    });
  };

  const handleFileUpload = async (e) => {
    const file = e.target.files[0];
    console.log(file)
    const base64 = await convertToBase64(file);
    console.log(base64.split(',')[1]);
    setBase64TextString(base64.split(',')[1]);
    console.log(base64)
  };


  const styles = {

    marginLeft: '33%',
    marginRight: 'auto',

  };


  function handleShowPets(event) {
    event.preventDefault();
    navigate('/all-pets');
  };

  function handleCancelConfirmation2() {
    setBolesti('');
    setVrsta('CAT');
    setRasa('');
    setName('');
    setGender('MALE');
    setVelicina('SMALL');
    setDob('JUNIOR');
    setWeight(0);
    setDisplayConfirmation(false);
    //form.current.reset(); //this will reset all the inputs in the form
    setDisplayConfirmation2(true);
  }

  function handleCancelConfirmation(event) {
    event.preventDefault();
    setBolesti('');
    setVrsta('CAT');
    setRasa('');
    setName('');
    setGender('MALE');
    setVelicina('SMALL');
    setDob('JUNIOR');
    setWeight(0);
    setDisplayConfirmation(false);

  }

  const cancelClick = () => {
    navigate('/all-pets')
  }

  const hideDialog = () => {
    setSubmitted(false);
    setProductDialog(false);
  }

  const saveStatus = () => {
    setSubmitted(true);
    setProductDialog(false);
    //setStatus('');
  }

  const productDialogFooter = (
    <>
      <Button label="Cancel" icon="pi pi-times" className="p-button-text" onClick={hideDialog} />
      <Button label="Save" icon="pi pi-check" className="p-button-text" onClick={saveStatus} />
    </>
  );


  const editStatus = () => {
    setProductDialog(true);
  }


  const onStatusChange = (e) => {
    setStatus(e.value);
  }


  return (
    <Splitter>
      <SplitterPanel>
        <div>
          <div className="card">
            <h2>Edit pet</h2>
            <div className="p-fluid grid formgrid">
              <div className="field col-12 md:col-5">
                <h5>Species</h5>
                <Dropdown value={vrsta} onChange={(e) => setVrsta(e.value)} options={vrste} optionLabel="name" optionValue='id' name='vrsta' placeholder="Species" />
              </div>
              <div className="field col-12 md:col-5">
                <h5>Health status*</h5>
                <InputTextarea placeholder="Health status" id='bolesti' name='bolesti' value={bolesti} maxLength={275}
                  onChange={changeBolesti} autoResize rows="5" cols="30" className={isBolestiOk ? "" : "p-invalid"} aria-describedby="subject-help" />
                {isBolestiOk ? ("") : (<small id="subject-help" className="p-error">Health status is mandatory!</small>)}
              </div>
              <div className="field col-12 md:col-5">
                <h5>Race*</h5>
                <InputText id="rasa" type="text" placeholder='Race' name="name" value={rasa} onChange={changeRasa} maxLength={57} className={isRasaOk ? "" : "p-invalid"} aria-describedby="subject-help" />
                {isRasaOk ? ("") : (<small id="subject-help" className="p-error">Race is mandatory!</small>)}
              </div>
              <div className="field col-12 md:col-5">
                <h5>Name*</h5>
                <InputText id="name" type="text" placeholder='Name' name="name" value={name} onChange={changeName} maxLength={57} className={isNameOk ? "" : "p-invalid"} aria-describedby="subject-help" />
                {isNameOk ? ("") : (<small id="subject-help" className="p-error">Name is mandatory!</small>)}
              </div>
              <div className="field col-12 md:col-5">
                <h5>Size*</h5>
                <Dropdown value={velicina} onChange={(e) => setVelicina(e.value)} options={velicine} optionLabel="name" optionValue='id' name='velicina' placeholder="Size" />

              </div>
              <div className="field col-12 md:col-5">
                <h5>Age*</h5>
                <Dropdown value={dob} onChange={(e) => setDob(e.value)} options={dobi} optionLabel="name" optionValue='id' name='dob' placeholder="Dob" />

              </div>
              <div className="field col-12 md:col-5">
                <h5>Gender*</h5>
                <div className="grid">
                  <div className="col-12 md:col-4">
                    <div className="field-radiobutton">
                      <RadioButton inputId="checkOption1" name="option" value="FEMALE" checked={gender === 'FEMALE'} onChange={(e) => setGender(e.value)} />
                      <label htmlFor="checkOption1">Female</label>
                    </div>
                  </div>
                  <div className="col-12 md:col-4">
                    <div className="field-radiobutton">
                      <RadioButton inputId="checkOption2" name="option" value="MALE" checked={gender === 'MALE'} onChange={(e) => setGender(e.value)} />
                      <label htmlFor="checkOption2">Male</label>
                    </div>
                  </div>
                </div>
              </div>
              <div className="field col-12 md:col-5">
                <h5>Weight</h5>
                <InputText id="weight" type="number" min="0.1" max="100" step="0.1" placeholder='weight' name="weight" value={weight} onChange={changeWeight} className={isWeightOk ? "" : "p-invalid"} aria-describedby="subject-help" />
                {isWeightOk ? ("") : (<small id="subject-help" className="p-error">Weight must be between 0.1 and 100!</small>)}

              </div>
              <form id="pet_form" ref={form}>
                <div className="field col-12 md:col-5">
                  <h5>Status</h5>
                  <InputText id="status" type="text" placeholder='Status' name="status" value={status} disabled={true} maxLength={57} aria-describedby="subject-help" />
                </div>
                <div className="field col-12 md:col-5">
                  <h5>Upload image</h5>
                  <InputText
                    type="file"
                    label="Image"
                    name="myFile"
                    accept=".jpeg, .png, .jpg"
                    onChange={(e) => handleFileUpload(e)}
                  />
                </div>
              </form>
            </div>


          </div>
        </div>
        <Dialog visible={productDialog} style={{ width: '450px' }} header={name} modal className="p-fluid" footer={productDialogFooter} onHide={hideDialog}>
          {base64TextString && <img src={`data:image/jpeg;base64,${base64TextString}`} width="150" className="mt-0 mx-auto mb-5 block shadow-2" />}
          <div className="field">
            <h2>Status</h2>
            <div className="formgrid grid">
              <div className="field-radiobutton col-6">
                <RadioButton inputId="status1" name="status" value="Adopted" onChange={onStatusChange} checked={status === 'Adopted'} />
                <label htmlFor="category1">Adopted</label>
              </div>
              <div className="field-radiobutton col-6">
                <RadioButton inputId="status2" name="status" value="Abandoned" onChange={onStatusChange} checked={status === 'Abandoned'} />
                <label htmlFor="category2">Abandoned</label>
              </div>
            </div>
          </div>
        </Dialog>
      </SplitterPanel>
      <SplitterPanel>
        <div className='card'>
          <img style={{ "height": "455px" }} src={`data:image/jpeg;base64,${base64TextString}`} onError={(e) => e.target.src = 'https://www.pngall.com/wp-content/uploads/4/German-Shepherd-Puppy-PNG-Picture.png'} alt={name} />
        </div>
        <div className="card">
          <span className="p-buttonset">
            <Button label="Change status" icon="pi pi-pencil" onClick={editStatus} />
            <Button label="Save" icon="pi pi-check" onClick={savePet} />
            <Dialog header="Pet is saved!" visible={displayConfirmation2} onHide={() => setDisplayConfirmation2(false)} style={{ width: '350px' }} modal footer={confirmationDialogFooter2}>
              <div className="flex align-items-center justify-content-center">
                <i className="pi pi-check-circle mr-3" style={{ fontSize: '2rem' }} />
              </div>
            </Dialog>
            <Button label="Remove" icon="pi pi-trash" />
            <Button label="Cancel" icon="pi pi-times" onClick={cancelClick} />
          </span>
        </div>
      </SplitterPanel>
    </Splitter>

  )
}

const comparisonFn = function (prevProps, nextProps) {
  return (prevProps.location.pathname === nextProps.location.pathname) && (prevProps.colorMode === nextProps.colorMode);
};

export default React.memo(EditPet, comparisonFn);
