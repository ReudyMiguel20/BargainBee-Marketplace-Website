import React, {useMemo, useState} from 'react'
import "./PostNewItem.css";
import categories from "../../data/categories";
import conditions from "../../data/conditions";
import Cookies from "js-cookie";
import axios from "axios";

const PostNewItem = () => {
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


    const handleSubmit = async (event) => {
        event.preventDefault();

        const newProduct = {
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
            const response = await axios.post("http://localhost:8080/api/item/new", newProduct, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${Cookies.get("access_token")}`
                },
                withCredentials: true
            });
        } catch (error) {
            console.error('Error:', error);
        }



        console.log(newProduct);
    }

    return (
        <div className="new-post-form-container">
            <form>
                <div className="new-post-form-title">
                    <h2>Post New Item</h2>
                </div>

                <div className="new-post-form-labels">
                    <label>
                        <h5>Item Name</h5>
                        <input
                            type="text"
                            name="item_name"
                            onChange={(e) => setItemName(e.target.value)}
                        />
                    </label>

                    <label>
                        <h5>Item Price</h5>
                        <input
                            type="number"
                            name="price"
                            onChange={(e) => setPrice(e.target.value)}
                        />
                    </label>

                    <label>
                        <h5>Image</h5>
                        <input
                            type="text"
                            name="image"
                            onChange={(e) => setImage(e.target.value)}
                        />
                    </label>

                    <label>
                        <h5>Tags</h5>
                        <input
                            type="text"
                            name="tags"
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
                            onChange={(e) => setQuantity(e.target.value)}
                        />
                    </label>

                    <label>
                        <h5>Category</h5>
                        <select
                            name="category"
                            onChange={(e) => setCategory(e.target.value)}
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
    )
}

export default PostNewItem;