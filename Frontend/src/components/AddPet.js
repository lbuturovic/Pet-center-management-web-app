import React, { useState, useRef, useCallback, useEffect } from 'react';
import { InputText } from 'primereact/inputtext';
import { InputTextarea } from 'primereact/inputtextarea';
import { Dropdown } from 'primereact/dropdown';
import { Button } from 'primereact/button';
import { RadioButton } from 'primereact/radiobutton';
import { Dialog } from 'primereact/dialog';
import { useLocation, useNavigate } from 'react-router-dom';
import useAuth from "../hooks/useAuth";
import { FileUpload } from 'primereact/fileupload';
import useAxiosPrivate from "../hooks/useAxiosPrivate";

export const AddPet = (props) => {
  const [vrsta, setVrsta] = useState('DOG');
  const [rasa, setRasa] = useState('');
  const [velicina, setVelicina] = useState('SMALL');
  const [name, setName] = useState('');
  const [bolesti, setBolesti] = useState('');
  const [weight, setWeight] = useState('1');
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
  const urlDesiredPets ="http://localhost:8090/desired/desired-pets";
  const [desiredPets, setDesiredPets] = useState([]);
  const urlMail= "http://localhost:8090/main/sendMail"
  const { auth } = useAuth();
  const [allCenters, setAllCenters] = useState([]);
  const [centerValue, setCenterValue] = useState();
  const [center, setCenter] = useState(null);
  const urlCenters = "http://localhost:8090/main/api/centers";
  const location = useLocation();

 /* async function getCenter(id) {
    console.log("ID "+ id)
    await axiosPrivate.get('http://localhost:8090/main/api/center/' + id, {
        headers: {
            Authorization: 'Bearer ' + auth?.accessToken
        }
    }).then((response) => {
       setCenter(response.data);
    }).catch(error => console.error(`${error}`));
}*/

  const getAllDesiredPets = () =>{ 
    return axiosPrivate.get(urlDesiredPets, {
        headers: {
            'Authorization': 'Bearer ' + auth?.accessToken
        }
}).then(res => {
            setDesiredPets(res.data)
           
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
  getAllDesiredPets();
}, []);

async function sendEmail(user) {
  await axiosPrivate.post(urlMail, {
    recipient : user,
    msgBody: "Ljubimac koji odgovara vašim zeljama je sada dostupan u centru! "
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
    if (e.target.value.trim().length === 0 || parseFloat(weight) < 0.1 || parseFloat(weight) > 100) setIsWeightOk(false);
    else setIsWeightOk(true);
  }

  const changeName = (e) => {
    e.preventDefault();
    setName(e.target.value);
    if (e.target.value.trim().length === 0) setIsNameOk(false);
    else setIsNameOk(true);
  }

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
    //await getCenter(centerValue);
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
    if (weight.length === 0 || parseFloat(weight) < 0.1 || parseFloat(weight) > 100) {
      setIsWeightOk(false);
      ok = false;
    }

    console.log(base64TextString)
    if (!ok) return;
    await axiosPrivate.post(url, {
      species: vrsta,
      race: rasa,
      weight: weight,
      name: name,
      healthCondition: bolesti,
      image: base64TextString,
      age: dob,
      gender: gender,
      status: "ABANDONED",
      category: velicina,
      center: centerValue

    }, {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + auth?.accessToken
      }
    })
      .then(res => {
        handleCancelConfirmation2();
        //handleCancelConfirmation();
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
      

      desiredPets.forEach(dpet => {
            
        if(dpet.species === vrsta && dpet.race === rasa && dpet.age===dob){
            sendEmail(dpet.user.email);
            return;
        }
      })
  }




  const form = useRef(null);




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

  

  return (
    <div className="grid p-fluid" style={styles}>
      <div className="col-12 lg:col-6">
        <h2>Create new pet</h2>
        <div className="card">
          <h5>Species</h5>
          <form id="pet_form" ref={form} onSubmit={handleSubmit}>
            <Dropdown value={vrsta} onChange={(e) => setVrsta(e.value)} options={vrste} optionLabel="name" optionValue='id' name='vrsta' placeholder="Species" />

            <h5>Health status*</h5>
            <InputTextarea placeholder="Health status" id='bolesti' name='bolesti' value={bolesti} maxLength={275}
              onChange={changeBolesti} autoResize rows="5" cols="30" className={isBolestiOk ? "" : "p-invalid"} aria-describedby="subject-help" />
            {isBolestiOk ? ("") : (<small id="subject-help" className="p-error">Health status is mandatory!</small>)}

            <h5>Race*</h5>
            <InputText id="rasa" type="text" placeholder='Race' name="name" value={rasa} onChange={changeRasa} maxLength={57} className={isRasaOk ? "" : "p-invalid"} aria-describedby="subject-help" />
            {isRasaOk ? ("") : (<small id="subject-help" className="p-error">Race is mandatory!</small>)}

            <h5>Name*</h5>
            <InputText id="name" type="text" placeholder='Name' name="name" value={name} onChange={changeName} maxLength={57} className={isNameOk ? "" : "p-invalid"} aria-describedby="subject-help" />
            {isNameOk ? ("") : (<small id="subject-help" className="p-error">Name is mandatory!</small>)}



            <h5>Size*</h5>
            <Dropdown value={velicina} onChange={(e) => setVelicina(e.value)} options={velicine} optionLabel="name" optionValue='id' name='velicina' placeholder="Size" />
            <h5>Age*</h5>
            <Dropdown value={dob} onChange={(e) => setDob(e.value)} options={dobi} optionLabel="name" optionValue='id' name='dob' placeholder="Dob" />
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
            <h5>Weight</h5>
            <InputText id="weight" type="number" min="0.1" max="100" step="0.1" placeholder='weight' name="weight" value={weight} onChange={changeWeight} className={isWeightOk ? "" : "p-invalid"} aria-describedby="subject-help" />
            {isWeightOk ? ("") : (<small id="subject-help" className="p-error">Weight must be between 0.1 and 100!</small>)}
            <h5>Center*</h5>
            <Dropdown value={centerValue} onChange={(e) => setCenterValue(e.value)} options={allCenters} optionLabel="address" optionValue='id' name='center' />
            <h5>Upload image</h5>
            <InputText
            type="file"
            label="Image"
            name="myFile"
            accept=".jpeg, .png, .jpg"
            onChange={(e) => handleFileUpload(e)}
          />
             
            <h5></h5>


            <div style={{ display: "flex" }}>
              <Button label="Create" type='submit' className="mr-2 mb-2"></Button>

              <Dialog header="Pet is saved!" visible={displayConfirmation2} onHide={() => setDisplayConfirmation2(false)} style={{ width: '350px' }} modal footer={confirmationDialogFooter2}>
                <div className="flex align-items-center justify-content-center">
                  <i className="pi pi-check-circle mr-3" style={{ fontSize: '2rem' }} />
                  <span>Do you want to see all pets or create another one?</span>
                </div>
              </Dialog>
              <Button type='button' label="Cancel" className="mr-2 mb-2" onClick={() => setDisplayConfirmation(true)}></Button>
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

export default React.memo(AddPet, comparisonFn);
