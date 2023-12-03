import React from 'react'
import {useQuery} from "@tanstack/react-query";
import SingleProduct from "../../../components/SingleProduct/SingleProduct";
import "./Featured.css";

const Featured = () => {
    const {data, error, status} = useQuery({
        queryKey: ['products'],
        queryFn: () => fetch("http://localhost:8080/api/item/featured").then((res) => res.json())
    });

    if (status === 'loading') {
        return <span className="fetching-status">Loading...</span>
    }

    if (status === 'error') {
        return <span className="fetching-status">There was an error fetching products... Try again later.</span>
    }

    return (
        <div className="list-products-featured">
            <div className="product-featured-name">
                <h2>Featured Items</h2>
            </div>

            <div className="products-by-featured">
                {Array.isArray(data) && data?.map(( product ) => (
                    <div key={product.item_id}>
                        <SingleProduct product={product}/>
                    </div>
                ))};
            </div>
        </div>
    )
}


export default Featured;