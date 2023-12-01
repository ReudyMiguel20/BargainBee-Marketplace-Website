import React from 'react'
import {useParams} from "react-router-dom";
import {useQuery} from "@tanstack/react-query";
import SingleProduct from "../../../components/SingleProduct/SingleProduct";
import "./SearchResultProducts.css";

const SearchResultProducts = () => {
    const {search} = useParams();
    const baseUrlSearch = "http://localhost:8080/api/item/search?item-name=" + search;

    const {data, error, status} = useQuery({
        queryKey: ['products', search],
        queryFn: () => fetch(baseUrlSearch).then((res) => res.json())
    });

    if (status === 'loading') {
        return <span className="fetching-status">Loading...</span>
    }

    if (status === 'error') {
        return <span className="fetching-status">There was an error fetching products... Try again later.</span>
    }


    return (
        <div>
            <div className="product-search-name">
                <h3>Search result for: {search}</h3>
            </div>

            <div className="search-product-results">
            {Array.isArray(data) && data?.map((product) => (
                <div key={product.item_id}>
                    <SingleProduct product={product} />
                </div>
            ))}
            </div>

        </div>
    )
}

export default SearchResultProducts;