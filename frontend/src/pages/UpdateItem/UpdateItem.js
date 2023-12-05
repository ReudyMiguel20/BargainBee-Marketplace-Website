import React, {useMemo, useState, useEffect} from 'react';

import {useQuery} from "@tanstack/react-query";
import "./UpdateItem.css";
import {useParams} from "react-router-dom";
import categories from "../../data/categories";
import conditions from "../../data/conditions";
import axios from "axios";
import Cookies from "js-cookie";

const UpdateItem = () => {
    const {id} = useParams();
    const owner = localStorage.getItem("username");

    console.log(owner);

    const [itemName, setItemName] = useState("");
    const [price, setPrice] = useState("");
    const [image, setImage] = useState("");
    const [tags, setTags] = useState("");
    const [quantity, setQuantity] = useState("");
    const [category, setCategory] = useState("");
    const [condition, setCondition] = useState("");
    const [description, setDescription] = useState("");

    const sortedCategories = useMemo(() => {
        return categories.sort((a, b) => a.localeCompare(b));
    }, []);

    const sortedConditions = useMemo(() => {
        return conditions.sort((a, b) => a.localeCompare(b));
    }, []);

    // Handle the query to get the item
    const {data, error, status} = useQuery({
        queryKey: ['products'],
        queryFn: () => fetch("http://localhost:8080/api/item/" + id).then((res) => res.json())
    });



    useEffect(() => {
        if (data) {
            setItemName(data.item_name);
            setPrice(data.price);
            setImage(data.image);
            setTags(data.tags);
            setQuantity(data.quantity);
            setCategory(data.category);
            setCondition(data.condition);
            setDescription(data.description);
        }
    }, [data, id]);


    if (status === 'loading') {
        return <span className="fetching-status">Loading...</span>
    }

    if (status === 'error') {
        return <span className="fetching-status">There was an error fetching products... Try again later.</span>
    }


    const handleSubmit = async (event) => {
        /* Only owner of the item should be able to update the item
             If the user is the owner of the post it should load completely, if
             they're not then it should give an error from the beginning, the
             wrong user should not be even be able to see the form.
         */
    }

    return (
        <div className="update-item-container">
            <div className="update-item-title">
                <h2>Update Item: {itemName} </h2>
            </div>

            <div className="update-item-form-container">
                <form className="update-item-form">

                    <div className="update-item-labels">
                        <label>
                            <h5>Item id: </h5>
                            <p>{data ? data.item_id : ""} </p>
                        </label>



                        <label>
                            <h5>Item Name</h5>
                            <input
                                type="text"
                                name="item_name"
                                value={itemName}
                                onChange={(e) => setItemName(e.target.value)}
                            />
                        </label>

                        <label>
                            <h5>Item Price</h5>
                            <input
                                type="number"
                                name="price"
                                value={price}
                                onChange={(e) => setPrice(e.target.value)}
                            />
                        </label>

                        <label>
                            <h5>Image</h5>
                            <input
                                type="text"
                                name="image"
                                value={image}
                                onChange={(e) => setImage(e.target.value)}
                            />
                        </label>

                        <label>
                            <h5>Tags</h5>
                            <input
                                type="text"
                                name="tags"
                                value={tags}
                                onChange={(e) => setTags(e.target.value)}
                            />
                        </label>

                        <label>
                            <h5>Quantity</h5>
                            <input
                                type="number"
                                min="1"
                                max="1000"
                                name="quantity"
                                value={quantity}
                                onChange={(e) => setQuantity(e.target.value)}
                            />
                        </label>

                        <label>
                            <h5>Category</h5>
                            <select
                                name="category"
                                onChange={(e) => setCategory(e.target.value)}
                                value={category}
                            >
                                {sortedCategories.map((category) => (
                                    <option key={category} value={category}>{category}</option>
                                ))};
                            </select>
                        </label>

                        <label>
                            <h5>Condition</h5>
                            <select
                                name="condition"
                                onChange={(e) => setCondition(e.target.value)}
                                value={condition}
                            >
                                {sortedConditions.map((condition) => (
                                    <option key={condition} value={condition}>{condition}</option>
                                ))};
                            </select>
                        </label>

                        <label>
                            <h5>Item Description</h5>
                            <textarea
                                name="description"
                                rows="4"
                                cols="50"
                                onChange={(e) => setDescription(e.target.value)}
                                value={description}
                            />
                        </label>
                    </div>

                    <div className="new-post-form-submit">
                        <button
                            type="submit"
                            onClick={(e) => handleSubmit(e)}
                        >
                            Submit
                        </button>
                    </div>

                </form>
            </div>
        </div>
    );
}

export default UpdateItem;