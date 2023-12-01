import React, {useEffect, useState} from 'react'
import "./ModalProductCondition.css";

const ModalProductCondition = ({setModalShow}) => {

    let loremIpsum = "Sed faucibus eros tortor, at condimentum libero consectetur ac. Duis elit lorem, lobortis nec leo ac, suscipit convallis odio. Nam gravida nisi nisi, ut placerat sapien eleifend tempor. ";

    const [isHidden, setIsHidden] = useState(true);

    useEffect(() => {
        const timeoutId = setTimeout(() => setIsHidden(false), 10);
        return () => clearTimeout(timeoutId);
    }, []);

    return (
        <div className="modal-background">
            <div className={"modal-content ${isHidden ? 'hidden' : ''"} style={{backgroundColor: 'white'}}>
                <div className="close-button">
                    <button
                        onClick={() => setModalShow(false)}
                    >X
                    </button>
                </div>

                <div className="product-condition-title">
                    <h1>Product Condition</h1>
                    <h5>Get to know in more details about the product condition</h5>
                </div>

                <h5>New</h5>
                <p>{loremIpsum} </p>

                <h5>Like New</h5>
                <p>{loremIpsum} </p>

                <h5>Used</h5>
                <p>{loremIpsum} </p>

                <h5>Acceptable</h5>
                <p>{loremIpsum} </p>

                <h5>Defective</h5>
                <p>{loremIpsum} </p>

                <h5>Refurbished</h5>
                <p>{loremIpsum} </p>
            </div>
        </div>
    )
}

export default ModalProductCondition;