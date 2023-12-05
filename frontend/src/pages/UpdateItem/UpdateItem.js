import React, {useMemo, useState, useEffect} from 'react';

import {useQuery} from "@tanstack/react-query";
import "./UpdateItem.css";
import {useParams} from "react-router-dom";
import categories from "../../data/categories";
import conditions from "../../data/conditions";
import axios from "axios";
import Cookies from "js-cookie";
import {jwtDecode} from "jwt-decode";

const UpdateItem = () => {
    const {id} = useParams();
    const accessToken = Cookies.get("access_token");
    const decodedToken = typeof accessToken === 'string' ? jwtDecode(accessToken) : null;
    const username = decodedToken ? decodedToken.preferred_username : null;

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

    if (data && data.seller !== username) {
        return <span>You are not the owner of this product, therefore you can't modify it-</span>
    }

    const handleSubmit = async (event) => {
        event.preventDefault();

        // Check if the current user is the owner of the item
        if (data && data.seller !== username) {
            console.error('Error: User is not the owner of this item');
            return; // Return early to prevent the update
        }

        const updatedProduct = {
            item_name: itemName,
            price,
            image,
            tags,
            quantity,
            category,
            condition,
            description
        }

        try {
            const response = await axios.put("http://localhost:8080/api/item/update/" + data.item_id, updatedProduct, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${Cookies.get("access_token")}`
                },
                withCredentials: true
            });

            console.log("succ");
        } catch (error) {
            console.error(error);
        }
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