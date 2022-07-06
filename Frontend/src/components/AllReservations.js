import React, { useState, useEffect, useCallback } from 'react';
import { classNames } from 'primereact/utils';
import { FilterMatchMode, FilterOperator } from 'primereact/api';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Button } from 'primereact/button';
import { ProgressBar } from 'primereact/progressbar';
import { Calendar } from 'primereact/calendar';
import { MultiSelect } from 'primereact/multiselect';
import { Slider } from 'primereact/slider';
import { TriStateCheckbox } from 'primereact/tristatecheckbox';
import { ToggleButton } from 'primereact/togglebutton';
import { Rating } from 'primereact/rating';
import { CustomerService } from '../service/CustomerService';
import { ProductService } from '../service/ProductService';
import axios from "axios";
import useAuth from "../hooks/useAuth";
import { Dropdown } from 'primereact/dropdown';
import useAxiosPrivate from "../hooks/useAxiosPrivate";

const AllReservations = () => {

    const [reservations, setReservations] = useState([]);
    const [loading1, setLoading1] = useState(true);
    const [allReservations, setAllReservations] = useState([]);
    const url = "http://localhost:8090/reservation/reservations";
    
    const axiosPrivate = useAxiosPrivate();
    const { auth } = useAuth();
 
    const getAllReservations = () =>{ 
        return axiosPrivate.get(url, {
            headers: {
                'Authorization': 'Bearer ' + auth?.accessToken
            }
    }).then(res => {
                setReservations(res.data)
               
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
        getAllReservations();
        setLoading1(false);
    }, []);
    
    useEffect(() => {
        setLoading1(false);
        reservations.forEach(reser => {
            const newRes = {
                startDate: reser.startDate,
                endDate:reser.endDate,
                service:reser.service.name

            }
            allReservations.push(newRes);
        })
    }, [reservations]);
  

    return (
    <div className="grid table-demo">
    <div className="col-12">
        <div className="card">
            <h5>Reservations</h5>
            <DataTable value={allReservations} paginator className="p-datatable-gridlines" showGridlines rows={10}
                        dataKey="id"  loading={loading1} responsiveLayout="scroll"
                          emptyMessage="No reservations found.">
                        <Column  header="Start date" field="startDate" style={{ minWidth: '12rem' }} />
                        <Column header="End date" field="endDate" style={{ minWidth: '14rem' }} />
                        <Column header="Service" field="service" style={{ minWidth: '14rem' }} />
                    </DataTable>
        </div>
    </div>
    </div>
    );

    
}

const comparisonFn = function (prevProps, nextProps) {
    return prevProps.location.pathname === nextProps.location.pathname;
};

export default React.memo(AllReservations, comparisonFn);
