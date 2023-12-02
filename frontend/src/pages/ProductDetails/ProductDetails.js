import React, {useEffect, useState} from 'react';
import {Link, useParams} from "react-router-dom";
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome'
import "./ProductDetails.css";
import {faCircleInfo, faHeart, faLocationDot, faTag} from "@fortawesome/free-solid-svg-icons";
import RelatedProducts from "../../components/RelatedProducts/RelatedProducts";
import ModalProductCondition from "../../components/ModalProductCondition/ModalProductCondition";

const ProductDetails = () => {
    const {id} = useParams();
    const [product, setProduct] = useState(null);
    const [modalShow, setModalShow] = useState(false);

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

    let productCondition = product.condition
        .replace('_', ' ').split(' ')
        .map(word => word.charAt(0).toUpperCase() + word.slice(1).toLowerCase()).join(' ');

    let userPicture = "https://upload.wikimedia.org/wikipedia/commons/thumb/5/59/User-avatar.svg/2048px-User-avatar.svg.png";

    let baseUrlCategory = "http://localhost:3000/category/";

    return (
        <div className="principal-div">
            <div className="product-image">
                <div className="product-image-info">
                    <p className="product-links"><Link to="/">Home</Link> > <Link
                        to={`${baseUrlCategory}${productCategory}`}>{productCategory}</Link> > {product.item_name}</p>
                    <img src={product.image}/>
                </div>

                <div className="product-condition">
                    <div className="product-condition-info">
                        <h6>Condition</h6>
                        <h6>{productCondition}
                            <button
                                onClick={() => setModalShow(true)}
                                style={{border: "none", backgroundColor: "transparent", cursor: "pointer"}}
                            >
                                <FontAwesomeIcon style={{paddingLeft: "5px"}} icon={faCircleInfo}/>
                            </button>
                        </h6>
                    </div>

                    <div className="hr">
                        <hr/>
                    </div>


                    <div className="product-condition-status">
                        <h6>Available for home delivery</h6>
                        <h6>*condition goes here*</h6>
                    </div>
                </div>

                <div className="product-description">
                    <h5>Description</h5>
                    <p>{product.description}</p>
                </div>

                <div className="product-related">
                    <h4>Related products:</h4>

                    <div className="product-related-info">
                        <RelatedProducts id={id}/>
                    </div>
                </div>
            </div>

            <div className="product-principal-info">
                <div className="product-title">
                    <h3>{product.item_name}</h3>
                    <h4>USD$ {product.price.toLocaleString('en-US')}</h4>
                    <h5 style={{color: "grey"}}>Published: {product.date_listed}</h5>
                    <h6>Boost status here</h6>
                    <hr/>
                </div>

                <div className="product-button-info">
                    <button><FontAwesomeIcon icon={faHeart}/> Add To Favorites</button>
                    <h5>Quantity: {product.quantity} unit</h5>
                </div>

                <hr/>

                <div className="product-location">
                    <h5 style={{color: "grey"}}><FontAwesomeIcon icon={faLocationDot}/> Location here</h5>
                    <h5 style={{color: "grey"}}><FontAwesomeIcon icon={faTag}/> {productCategory}</h5>
                </div>

                <hr/>

                <div className="product-seller-info">
                    <div className="product-seller-username">
                        <h5>Sold By: {product.seller}</h5>
                    </div>

                    <div className="product-seller-picture">
                        <img src={userPicture}/>
                    </div>

                    <div className="product-seller-buttons">
                        <button>Contact Seller</button>
                        <button>Report Ad</button>
                    </div>
                </div>
            </div>

            {modalShow && <ModalProductCondition setModalShow={setModalShow}/>}
        </div>
    );
}

export default ProductDetails;