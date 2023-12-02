import React from 'react';
import herobanner from '../../assets/hero-banner.png';
import './HeroBanner.css';

export const HeroBanner = () => {
    return (
        <div className="hero-banner">
            <img src={herobanner}/>
        </div>
    );
}