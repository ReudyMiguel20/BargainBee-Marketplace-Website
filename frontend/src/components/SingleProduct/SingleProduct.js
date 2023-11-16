import "../../components/SingleProduct/SingleProduct.css";

const SingleProduct = ({ product }) => {
    return (
        <div className="product">
            <div className="product-info">
            <h3>{product.item_id}</h3>
            <h2>{product.item_name}</h2>
            <p>{product.description}</p>
            <p>{product.price}</p>
            <p>{product.quantity}</p>
            <p>{product.category}</p>
            <p>{product.image}</p>
            </div>
        </div>

    );
}

export default SingleProduct;