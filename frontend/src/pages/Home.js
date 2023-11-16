import React from "react";
import {HeroBanner} from "../components/HeroBanner/HeroBanner";
import Products from "./Products/Products";

export const Home = () => {
    return (
        <div className="home">
            <HeroBanner/>
            <Products/>
        </div>
    );
};
