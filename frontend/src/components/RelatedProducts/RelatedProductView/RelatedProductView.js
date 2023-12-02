import "./RelatedProductView.css";

const RelatedProductView = ({product}) => {


    return (
        <div className="product-related-div">
            <div className="related-product-img">
                <img src={product.image}/>
            </div>

            <div className="related-product-info">

                <div className="related-product-price">
                    <h5>USD$ {product.price.toLocaleString()}</h5>
                </div>

                <div className="related-product-name">
                    <p>
                        {product.item_name.length > 25
                            ? `${product.item_name.substring(0, 25)}..... `
                            : product.item_name}
                    </p>
                </div>


            </div>

        </div>
    );
}

export default RelatedProductView;