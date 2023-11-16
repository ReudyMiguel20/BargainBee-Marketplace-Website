import "../../components/SingleProduct/SingleProduct.css";
import { Button } from "react-bootstrap";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faEye } from '@fortawesome/free-solid-svg-icons'

const SingleProduct = ({product}) => {
    return (
        <div className="product">
            <div className="product-info">
                <div className="product-img-div">
                    <img src={product.image} alt=""/>
                </div>

                <div className="product-name-description">
                    <h5>{product.item_name}</h5>
                    <p>{product.description}</p>
                </div>

                <div className="product-complete-info">
                    <div className="product-price-category">
                        <p>Price USD$: {product.price}</p>
                        <p>Category: {product.category.charAt(0).toUpperCase() + product.category.slice(1).toLowerCase()}</p>
                    </div>

                    <div className="product-info-button">
                        <Button variant="primary"><FontAwesomeIcon icon={faEye} /> Show Details</Button>
                        {/*href={`http://localhost:8080/api/item/${product.item_id}`*/}
                    </div>
                </div>
            </div>
        </div>
    );
}

export default SingleProduct;