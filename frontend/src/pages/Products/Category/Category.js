import "./Category.css";
import {useQuery} from "@tanstack/react-query";
import React from "react";
import SingleProduct from "../../../components/SingleProduct/SingleProduct";
import {useParams} from "react-router-dom";

const Category = () => {
    let {category} = useParams();

    // Replace all dashes with underscores for the API call.
    category = category.replace(/-/g, "_");

    const {data, error, status} = useQuery({
        queryKey: ['products', category],
        queryFn: () => fetch("http://localhost:8080/api/item/category/" + category).then((res) => res.json())
    });

    if (status === 'loading') {
        return <span className="fetching-status">Loading...</span>
    }

    if (status === 'error') {
        return <span className="fetching-status">There was an error fetching products... Try again later.</span>
    }


    let productCategory = category.replace(/_/g, " ").replace(/and/g, "&").split(" ").map(word => word.charAt(0).toUpperCase() + word.slice(1).toLowerCase()).join(" ");


    return (
        <div className="list-products-category">
            <div className="product-category-name">
                <h3>All {productCategory} Products</h3>
            </div>

            <div className="products-by-category">
                {Array.isArray(data) && data?.map((product) => (
                    <div key={product.item_id}>
                        <SingleProduct product={product}/>
                    </div>
                ))}
            </div>
        </div>
    );


}

export default Category;