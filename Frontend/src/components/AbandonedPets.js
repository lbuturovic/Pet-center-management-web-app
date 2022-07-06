import React, { useState, useEffect, useRef } from 'react';
import { DataView, DataViewLayoutOptions } from 'primereact/dataview';
import { ProductService } from '../service/ProductService';
import { Rating } from 'primereact/rating';
import { Button } from 'primereact/button';
import './AllPets.css';
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import useAuth from "../hooks/useAuth";
import { Checkbox } from 'primereact/checkbox';
import {useLocation, useNavigate } from 'react-router-dom';
import { Dropdown } from 'primereact/dropdown';
import { InputText } from 'primereact/inputtext';

const map1 = new Map();
map1.set('ADOPTED', 'INSTOCK');
map1.set('ABANDONED', 'LOWSTOCK');

const AbandonedPets = () => {

    const [pets, setPets] = useState(null);
    const [layout, setLayout] = useState('grid');
    const [loading, setLoading] = useState(true);
    const [first, setFirst] = useState(0);
    const [totalRecords, setTotalRecords] = useState(0);
    const rows = useRef(6);
    const datasource = useRef(null);
    const isMounted = useRef(false);
    const url = "http://localhost:8090/main/api/pets/abandoned";
    const navigate = useNavigate();
    const [speciesKey, setSpeciesKey] = useState('ALL')
    const [ageKey, setAgeKey] = useState('ALL')
    const [categoryKey, setCategoryKey] = useState('ALL')
    const [filteredData, setFilteredData] = useState(null);
    const [statusKey, setStatusKey] = useState('ALL')
    const [search, setSearch] = useState('')

    const axiosPrivate = useAxiosPrivate();
    const { auth } = useAuth();

    useEffect(() => {
        if (isMounted.current) {
            setTimeout(() => {
                setLoading(false);
            }, 1000);
        }
    }, [loading]); // eslint-disable-line react-hooks/exhaustive-deps

    const getAllPets = () =>{ 
        return axiosPrivate.get(url, {
            headers: {
                Authorization: 'Bearer ' + auth?.accessToken
            }
        })
            .then(res => {
                setPets(res.data)
                setFilteredData(res.data)
                datasource.current = res.data;
                setTotalRecords(datasource.current.length);
                setPets(datasource.current.slice(0, rows.current));
                setLoading(false);
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
        setTimeout(() => {
            isMounted.current = true;
            getAllPets();
            setLoading(false);
        }, 1000);
    }, []); // eslint-disable-line react-hooks/exhaustive-deps

    const onPage = (event) => {
        setLoading(true);

        //imitate delay of a backend call
        setTimeout(() => {
            const startIndex = event.first;
            const endIndex = Math.min(event.first + rows.current, totalRecords - 1);
            const newPets = startIndex === endIndex ? datasource.current.slice(startIndex) : datasource.current.slice(startIndex, endIndex);

            setFirst(startIndex);
            setPets(newPets);
            setLoading(false);
        }, 1000);
    }

    const moreButtonClicked = (data) => {
        let id = "?id=" + data.id;
        navigate({pathname:'/view-pet', search: id });
    }
    
    const renderListItem = (data) => {
        return (
            <div className="col-12">
                <div className="product-list-item">
                    <img src={`data:image/jpeg;base64,${data.image}`} onError={(e) => e.target.src='https://www.pngall.com/wp-content/uploads/4/German-Shepherd-Puppy-PNG-Picture.png'}alt={data.name}/>
                    <div className="product-list-detail">
                        <div className="product-name">{data.name}</div>
                        <div className="product-description">{data.race}</div>
                        <i className="pi pi-tag product-category-icon"></i><span className="product-category">{data.category}</span>
                    </div>
                    <div className="product-list-action">
                        <span className="product-price">{data.age}</span>
                        <Button icon="pi pi-list" label="More" onClick={() => moreButtonClicked(data)}></Button>
                        <span className={`product-badge status-${map1.get(data.status).toLowerCase()}`}>{data.status}</span>
                    </div>
                </div>
            </div>
        );
    }

    
    const renderGridItem = (data) => {
        return (
            <div className="col-12 md:col-4">
                <div className="product-grid-item card">
                    <div className="product-grid-item-top">
                        <div>
                            <i className="pi pi-tag product-category-icon"></i>
                            <span className="product-category">{data.species}</span>
                        </div>
                        <span className={`product-badge status-${map1.get(data.status).toLowerCase()}`}>{data.status}</span>
                    </div>
                    <div className="product-grid-item-content">
                    <img src={`data:image/jpeg;base64,${data.image}`} onError={(e) => e.target.src='https://www.pngall.com/wp-content/uploads/4/German-Shepherd-Puppy-PNG-Picture.png'}alt={data.name} />
                        <div className="product-name">{data.name}</div>
                        <div className="product-description">{data.race}</div>
                    </div>
                    <div className="product-grid-item-bottom">
                        <span className="product-price">{data.age}</span>
                        <Button icon="pi pi-list" label="More" onClick={() => moreButtonClicked(data)}></Button>
                    </div>
                </div>
            </div>
        );
    }

    const itemTemplate = (product, layout) => {
        if (!product) {
            return;
        }

        if (layout === 'list')
            return renderListItem(product);
        else if (layout === 'grid')
            return renderGridItem(product);
    }

    const renderHeader = () => {
        let onOptionChange = (e) => {
            setLoading(true);
            setLayout(e.value);
        };

        const speciesOptions = [
            {label: 'All', value: 'ALL'},
            {label: 'Dogs', value: 'DOG'},
            {label: 'Cats', value: 'CAT'},
        ];

        const statusOptions = [
            {label: 'All', value: 'ALL'},
            {label: 'Abandoned', value: 'ABANDONED'},
            {label: 'Adopted', value: 'ADOPTED'},
        ];

        const categoryOptions = [
            {label: 'All', value: 'ALL'},
            {label: 'Small', value: 'SMALL'},
            {label: 'Medium', value: 'MEDIUM'},
            {label: 'Big', value: 'BIG'},
        ];

        const ageOptions = [
            {label: 'All', value: 'ALL'},
            {label: 'Newborn', value: 'NEWBORN'},
            {label: 'Junior', value: 'JUNIOR'},
            {label: 'Preteen', value: 'PRETEEN'},
            {label: 'Teen', value: 'TEEN'},
            {label: 'Postteen', value: 'POSTTEEN'},
            {label: 'Fullgrown', value: 'FULLGROWN'}
        ];

        const onSpeciesChange = (event) => {
            const value = event.value;
            setSpeciesKey(value)
        }

        const onAgeChange = (event) => {
            const value = event.value;
            setAgeKey(value)
        }
        const onCategoryChange = (event) => {
            const value = event.value;
            setCategoryKey(value)
        }

        const onStatusChange = (event) => {
            const value = event.value;
            setStatusKey(value)
        }

        const handleSearch = (e) => {
            setSearch(e.target.value)
          }

        const filterData = () => {
            setFilteredData(pets.filter((item) =>
            (item.species.toLowerCase().includes(speciesKey.toLowerCase()) || speciesKey == 'ALL') &&
            (item.category.toLowerCase().includes(categoryKey.toLowerCase()) || categoryKey == 'ALL') &&
            (item.age.toLowerCase().includes(ageKey.toLowerCase()) || ageKey == 'ALL') &&
            (search == '' || item.race.toLowerCase().includes(search.toLowerCase()) ||
            item.name.toLowerCase().includes(search.toLowerCase()))
      ))
        }

        return (
            <div style={{ textAlign: 'left' }}>
                <DataViewLayoutOptions layout={layout} onChange={onOptionChange} />
                <h5></h5>
                <div className="grid">
                        <div className="col-12 md:col-5">
                	        <h5>Species</h5>
                            <Dropdown id = "species" options={speciesOptions} value={speciesKey} onChange={onSpeciesChange} placeholder="Sort By" />
                        </div>
                        <div className="col-12 md:col-5">
                            <h5>Category</h5>
                            <Dropdown id = "category" options={categoryOptions} value={categoryKey} onChange={onCategoryChange} placeholder="Sort By" />
                        </div>
                        <div className="col-12 md:col-5">
                            <h5>Age</h5>
                            <Dropdown id = "age" options={ageOptions} value={ageKey} onChange={onAgeChange} placeholder="Sort By" />
                            </div>
                            <div className="col-12 md:col-5">
                            <InputText type="text" value={search} style = {{"fontSize": "16px"}}placeholder="Enter race or name" onChange={handleSearch} />
                            </div>
                            <div className="col-12 md:col-4">
                                <Button icon="pi pi-search" label="Search" onClick = {filterData}></Button>
                                </div>
            </div>
        </div>
        );
    }

    const header = renderHeader();

    return (
        <div className="dataview-demo">
            <div className="card">
                <DataView value={filteredData} layout={layout} header={header}
                        itemTemplate={itemTemplate} lazy paginator paginatorPosition={'both'} rows={rows.current}
                        totalRecords={totalRecords} first={first} onPage={onPage} loading={loading} />
            </div>
        </div>
    );
}
            
export default AbandonedPets