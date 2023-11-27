import React, {useEffect, useState} from 'react';
import {Link, useParams} from "react-router-dom";
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome'
import "./ProductDetails.css";
import {faHeart} from "@fortawesome/free-solid-svg-icons";

const ProductDetails = () => {
    const {id} = useParams();
    const [product, setProduct] = useState(null);

    useEffect(() => {
        fetch(`http://localhost:8080/api/item/${id}`)
            .then(response => response.json())
            .then(data => setProduct(data))
            .catch(error => console.error('Error:', error));
    }, [id]);

    if (!product) {
        return <span>Fetching product...</span>
    }

    let productCategory = product.category.charAt(0).toUpperCase() + product.category.slice(1).toLowerCase();

    return (
        <div className="principal-div">
            <div className="product-image">
                <p><Link to="/">Home</Link> > {productCategory} > {product.item_name}</p>
                <img src={product.image}/>
            </div>

            <div className="product-principal-info">
                <h3>{product.item_name}</h3>
                <h4>USD$ {product.price}</h4>
                <h5>Published: {product.date_listed}</h5>

                <hr/>

                <div className="product-button-info">
                    <button><FontAwesomeIcon icon={faHeart}/> Add To Favorites</button>
                    <h5>Quantity: {product.quantity} unit</h5>
                </div>

                <hr/>

                <div>
                    <h5>Location here</h5>
                    <h5>Category Again here</h5>
                </div>
                {/*<div className="product-description">*/}
                {/*    */}
                {/*</div>*/}
            </div>
        </div>
    );
}

export default ProductDetails;