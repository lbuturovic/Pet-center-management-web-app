import React, { useEffect, useState, useRef } from 'react';
import { InputText } from 'primereact/inputtext';
import axios from "axios";
import { Dialog } from 'primereact/dialog';
import { Button } from 'primereact/button';
import { faCheck, faTimes, faInfoCircle } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import './Register.css';
import {useLocation, useNavigate } from 'react-router-dom';
import useAuth from "../hooks/useAuth";
import useAxiosPrivate from "../hooks/useAxiosPrivate";


export const AddEmployee = () => {

    const userRef = useRef()
    const errRef = useRef()

    const { auth } = useAuth();

    const navigate = useNavigate();

    const axiosPrivate = useAxiosPrivate();

    const USER_REGEX = /^[A-z][A-z0-9-_]{3,23}$/;
    const NAME_REGEX = /^[A-z][A-z\s]{3,23}$/;
    const PWD_REGEX = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%]).{8,24}$/;
    const EMAIL_REGEX = /^[a-z0-9]+@[a-z]+\.[a-z]{2,3}$/

    const [displayConfirmation, setDisplayConfirmation] = useState(false)
    const [displayConfirmation2, setDisplayConfirmation2] = useState(false)
        
    const [username, setUsername] = useState('');
    const [validUsername, setValidUsername] = useState(false);
    const [usernameFocus, setUsernameFocus] = useState(false);

    const [firstName, setFirstName] = useState('');
    const [validFirstName, setValidFirstName] = useState(false);
    const [firstNameFocus, setFirstNameFocus] = useState(false);

    const [lastName, setLastName] = useState('');
    const [validLastName, setValidLastName] = useState(false);
    const [lastNameFocus, setLastNameFocus] = useState(false);

    const [email, setEmail] = useState('');
    const [validEmail, setValidEmail] = useState(false);
    const [emailFocus, setEmailFocus] = useState(false);

    const [pwd, setPwd] = useState('');
    const [validPwd, setValidPwd] = useState(false);
    const [pwdFocus, setPwdFocus] = useState(false);

    const [matchPwd, setMatchPwd] = useState('');
    const [validMatch, setValidMatch] = useState(false);
    const [matchFocus, setMatchFocus] = useState(false);

    const [errMsg, setErrMsg] = useState('');
    const [success, setSuccess] = useState(false);


    useEffect(() => {
        userRef.current.focus();
    }, [])

    useEffect(() => {
        setValidUsername(USER_REGEX.test(username));
    }, [username])

    useEffect(() => {
        setValidFirstName(NAME_REGEX.test(firstName));
    }, [firstName])

    useEffect(() => {
        setValidLastName(NAME_REGEX.test(lastName));
    }, [lastName])

    useEffect(() => {
        setValidPwd(PWD_REGEX.test(pwd));
        setValidMatch(pwd === matchPwd);
    }, [pwd, matchPwd])

    useEffect(() => {
        setValidEmail(EMAIL_REGEX.test(email));
    }, [email])

    useEffect(() => {
        setErrMsg('');
    }, [username, firstName, lastName, pwd, matchPwd])

    function handleShowUsers(event) {
        event.preventDefault();
        navigate('/all-users'); 
      };

    
      const confirmationDialogFooter2 = (
        <>
          <div className="grid formgrid">
            <Button type="button" label="All users" icon="pi pi-list" onClick={handleShowUsers} className="p-button-text" />
            <Button type="button" label="New user" icon="pi pi-plus" onClick={() => setDisplayConfirmation2(false)} className="p-button-text" />
          </div>
        </>
      );

  const styles = {
    marginLeft: '33%',
    marginRight: 'auto',
  };

  
  

  function handleCancelConfirmation(event) {
    event.preventDefault();
    /*setUsername('');
    setPassword('');
    setFirstName('');
    setLastName('');
    setEmail('');
    setIsPasswordOk(true);
    setIsUsernameOk(true);
    setIsFirstNameOk(true);
    setIsLastNameOk(true);
    setDisplayConfirmation(false);
    //navigate('/users')*/

  }

  function handleCancelConfirmation2() {
    /*setUsername('');
    setPassword('');
    setFirstName('');
    setLastName('');
    setEmail('');
    setIsUsernameOk(true);
    setIsFirstNameOk(true);
    setIsLastNameOk(true);
    setDisplayConfirmation2(true);*/
  }

  const confirmationDialogFooter = (
    <>
      <Button type="button" label="No" icon="pi pi-times" onClick={() => setDisplayConfirmation(false)} className="p-button-text" />
      <Button type="button" label="Yes" icon="pi pi-check" onClick={handleCancelConfirmation} className="p-button-text" autoFocus />
    </>
  );

  async function handleSubmit(event) {
    event.preventDefault();
    const v1 = USER_REGEX.test(username);
    const v2 = PWD_REGEX.test(pwd);
    const v3 = NAME_REGEX.test(firstName);
    const v4 = NAME_REGEX.test(lastName);
    const v5 = EMAIL_REGEX.test(email)
    if (!v1 || !v2 || !v3 || !v4) {
        setErrMsg("Invalid data!");
        return;
    }
    var url = 'http://localhost:8090/user/api/user';
    await axiosPrivate.post(url, {
      username: username,
      password: pwd,
      firstName: firstName,
      lastName: lastName,
      email: email
    }, {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + auth?.accessToken
      }
    })
      .then(response => {
            console.log(response?.data);
            console.log(response?.accessToken);
            console.log(JSON.stringify(response))
            setSuccess(true);
            setUsername('');
            setPwd('');
            setFirstName('')
            setLastName('')
            setMatchPwd('');
            setEmail('');
            setDisplayConfirmation2(true);
      }).catch(function (err) {
        if (!err?.response) {
            setErrMsg('No Server Response');
        } else if (err.response?.status === 409) {
            setErrMsg('Username Taken');
        } else {
            setErrMsg('Registration Failed')
        }
        errRef.current.focus();
      });

  }

  const styles3 = {
    marginLeft:'37%',
    width: '25%'
  };


  return (
    
    <div className="grid p-fluid" style={styles}>
      <div className="col-12 lg:col-6">
      <div className="text-center mb-5">
                        <h1 >Create new employee</h1>
                        </div>
        <div className="card">
          <form id="userForm" onSubmit={handleSubmit}>
          <h5 htmlFor="username">
                Username:
                <FontAwesomeIcon icon={faCheck} className={validUsername ? "valid" : "hide"} />
                <FontAwesomeIcon icon={faTimes} className={validUsername || !username ? "hide" : "invalid"} />
        </h5>
        <InputText
                type="text"
                id="username"
                ref={userRef}
                autoComplete="off"
                onChange={(e) => setUsername(e.target.value)}
                value={username}
                required
                aria-invalid={validUsername ? "false" : "true"}
                aria-describedby="uidnote"
                onFocus={() => setUsernameFocus(true)}
                onBlur={() => setUsernameFocus(false)}
            />
            <p id="uidnote" className={usernameFocus && username && !validUsername ? "instructions" : "offscreen"}>
                <FontAwesomeIcon icon={faInfoCircle} />
                    4 to 24 characters.<br />
                    Must begin with a letter.<br />
                    Letters, numbers, underscores, hyphens allowed.
            </p>
            <h5 htmlFor="firstName">
                First name:
                <FontAwesomeIcon icon={faCheck} className={validFirstName ? "valid" : "hide"} />
                <FontAwesomeIcon icon={faTimes} className={validFirstName || !firstName ? "hide" : "invalid"} />
        </h5>
        <InputText
                type="text"
                id="firstName"
                autoComplete="off"
                onChange={(e) => setFirstName(e.target.value)}
                value={firstName}
                required
                aria-invalid={validFirstName ? "false" : "true"}
                aria-describedby="firstNameNote"
                onFocus={() => setFirstNameFocus(true)}
                onBlur={() => setFirstNameFocus(false)}
            />
            <p id="firstNameNote" className={firstNameFocus && firstName && !validFirstName ? "instructions" : "offscreen"}>
                <FontAwesomeIcon icon={faInfoCircle} />
                    4 to 24 characters.<br />
                    Must begin with a letter.<br />
                    Letters and spaces allowed.
            </p>

            <h5 htmlFor="lastName">
                Last name:
                <FontAwesomeIcon icon={faCheck} className={validLastName ? "valid" : "hide"} />
                <FontAwesomeIcon icon={faTimes} className={validLastName || !lastName ? "hide" : "invalid"} />
        </h5>
        <InputText
                type="text"
                id="lastName"
                autoComplete="off"
                onChange={(e) => setLastName(e.target.value)}
                value={lastName}
                required
                aria-invalid={validLastName ? "false" : "true"}
                aria-describedby="lastNameNote"
                onFocus={() => setLastNameFocus(true)}
                onBlur={() => setLastNameFocus(false)}
            />
            <p id="lastNameNote" className={lastNameFocus && lastName && !validLastName ? "instructions" : "offscreen"}>
                <FontAwesomeIcon icon={faInfoCircle} />
                    4 to 24 characters.<br />
                    Must begin with a letter.<br />
                    Letters and spaces allowed.
            </p>

            <h5 htmlFor="email">
                Email:
                <FontAwesomeIcon icon={faCheck} className={validEmail ? "valid" : "hide"} />
                <FontAwesomeIcon icon={faTimes} className={validEmail || !email ? "hide" : "invalid"} />
            </h5>
        <InputText
                type="text"
                id="email"
                autoComplete="off"
                onChange={(e) => setEmail(e.target.value)}
                value={email}
                required
                aria-invalid={validEmail ? "false" : "true"}
                aria-describedby="emailNote"
                onFocus={() => setEmailFocus(true)}
                onBlur={() => setEmailFocus(false)}
            />
            <p id="emailNote" className={emailFocus && email && !validEmail ? "instructions" : "offscreen"}>
                <FontAwesomeIcon icon={faInfoCircle} />
                    Invalid email
            </p>

            <h5 htmlFor="password">
                Password:
                <FontAwesomeIcon icon={faCheck} className={validPwd ? "valid" : "hide"} />
                <FontAwesomeIcon icon={faTimes} className={validPwd || !pwd ? "hide" : "invalid"} />
        </h5>
        <InputText
                type="password"
                id="password"
                autoComplete="off"
                onChange={(e) => setPwd(e.target.value)}
                value={pwd}
                required
                aria-invalid={validPwd ? "false" : "true"}
                aria-describedby="pwdnote"
                onFocus={() => setPwdFocus(true)}
                onBlur={() => setPwdFocus(false)}
            />
            <p id="pwdnote" className={pwdFocus && !validPwd ? "instructions" : "offscreen"}>
                            <FontAwesomeIcon icon={faInfoCircle} />
                            8 to 24 characters.<br />
                            Must include uppercase and lowercase letters, a number and a special character.<br />
                            Allowed special characters: <span aria-label="exclamation mark">!</span> <span aria-label="at symbol">@</span> <span aria-label="hashtag">#</span> <span aria-label="dollar sign">$</span> <span aria-label="percent">%</span>
            </p>

            <h5 htmlFor="password">
                Confirm password:
                <FontAwesomeIcon icon={faCheck} className={validMatch && matchPwd ? "valid" : "hide"} />
                <FontAwesomeIcon icon={faTimes} className={validMatch || !matchPwd ? "hide" : "invalid"} />
            </h5>
                        <InputText
                            type="password"
                            id="confirm_pwd"
                            onChange={(e) => setMatchPwd(e.target.value)}
                            value={matchPwd}
                            required
                            aria-invalid={validMatch ? "false" : "true"}
                            aria-describedby="confirmnote"
                            onFocus={() => setMatchFocus(true)}
                            onBlur={() => setMatchFocus(false)}
                        />
                        <p id="confirmnote" className={matchFocus && !validMatch ? "instructions" : "offscreen"}>
                            <FontAwesomeIcon icon={faInfoCircle} />
                            Must match the first password input field.
                        </p>
                        <br></br>
                        <p ref={errRef} className={errMsg ? "errmsg" : "offscreen"} aria-live="assertive">{errMsg}</p>
                        <div style={{ display: "flex" }}>
              <Button label="Create" type='submit' className="mr-2 mb-2"></Button>

              <Dialog header="User is saved!" visible={displayConfirmation2} onHide={() => setDisplayConfirmation2(false)} style={{ width: '350px' }} modal footer={confirmationDialogFooter2}>
                <div className="flex align-items-center justify-content-center">
                  <i className="pi pi-check-circle mr-3" style={{ fontSize: '2rem' }} />
                  <span>Do you want to see all users or create another one?</span>
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

export default React.memo(AddEmployee, comparisonFn);
