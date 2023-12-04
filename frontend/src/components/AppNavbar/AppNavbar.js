import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";
import NavDropdown from "react-bootstrap/NavDropdown";
import beeicon from "../../assets/bee.png";
import "./AppNavbar.css";
import {Link, useNavigate} from "react-router-dom";
import {faMagnifyingGlass, faRightFromBracket} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {useContext, useRef, useState} from "react";
import UserContext from "../../UserContext";
import Cookies from "js-cookie";
import {jwtDecode} from "jwt-decode";

function AppNavbar() {
    const [searchTerm, setSearchTerm] = useState("");
    const {setUserLoggedIn} = useContext(UserContext);
    const navigate = useNavigate();
    const searchButtonRef = useRef();
    const accessToken = Cookies.get("access_token");
    const decodedToken = typeof accessToken === 'string' ? jwtDecode(accessToken) : null;
    const username = decodedToken ? decodedToken.preferred_username : null;
    const testUserLoggedIn = localStorage.getItem("userLoggedIn");

    // Store username in local storage so that it can be used in the navbar even after a page refresh.
    localStorage.setItem("username", username);

    const handleInputChange = (event) => {
        setSearchTerm(event.target.value);
    }

    const handleSearchClick = (event) => {
        event.preventDefault();
        navigate(`/search/${searchTerm}`);
    }

    const handleKeyPress = (event) => {
        if (event.key === 'Enter') {
            handleSearchClick(event);
        }
    }

    const handleLoginClick = (event) => {
        event.preventDefault();
        navigate(`/login`);
    }

    const removeAllCookies = () => {
        for (var cookie in Cookies.get()) {
            Cookies.remove(cookie);
        }
    }

    const flushLogoutDetails = (event) => {
        event.preventDefault();
        removeAllCookies();
        localStorage.removeItem("userLoggedIn");
        setUserLoggedIn(false);
        navigate(`/`);
    }


    return (

        <Navbar data-bs-theme="dark" expand="lg" className="bg-body-tertiary">
            <div className="upper-nav">
                <Navbar.Brand className="title" href="/">
                    <img src={beeicon} style={{width: '40px', height: '40px'}}/>
                    Bargain Bee
                </Navbar.Brand>
                <div className="search-container">
                    <input
                        className="search-bar"
                        type="text"
                        placeholder="Search"
                        value={searchTerm}
                        onChange={handleInputChange}
                        onKeyDown={handleKeyPress}
                    />
                    <button
                        ref={searchButtonRef}
                        className="search-button"
                        onClick={handleSearchClick}
                    >
                        <FontAwesomeIcon icon={faMagnifyingGlass} style={{color: "#000000",}}/>
                    </button>
                </div>


                {testUserLoggedIn === "true" ? (
                    <div className="user-logged-in-status">
                        <h6>Welcome back, {username}</h6>
                        <div className="user-logged-in-buttons">
                            <button
                                style={{backgroundColor: "#dda12a"}}
                                onClick={() => navigate(`/products/new`)}
                            >
                                + Publish New Item
                            </button>
                            <button
                                onClick={flushLogoutDetails}
                                style={{backgroundColor: "red"}}
                            >
                                Logout <FontAwesomeIcon style={{paddingLeft: "5px"}} icon={faRightFromBracket}/>
                            </button>
                        </div>
                    </div>
                ) : (
                    <div className="buttons-container">
                        <button
                            onClick={handleLoginClick}
                        >
                            Login
                        </button>

                        <button
                            onClick={() => window.location.href = "http://localhost:8181/realms/spring-boot-microservices-realm/account/#/"}
                            // onClick={() => window.location.href = "http://localhost:8181/realms/spring-boot-microservices-realm/protocol/openid-connect/auth?client_id=account-console&redirect_uri=http%3A%2F%2Flocalhost%3A3000%2F&state=634d9590-adc2-47a1-8228-706794d1ea01&response_mode=fragment&response_type=code&scope=openid&nonce=0e3e042a-e096-4084-b392-1068ff5a9a57&code_challenge=Bmvh984fpxOBJ3t_ahFpab-gNtm_UQJXI0T-Fh_Yo50&code_challenge_method=S256"}
                        >
                            Register
                        </button>
                    </div>
                )};

            </div>
            <div className="lower-nav">
                <Navbar.Toggle aria-controls="basic-navbar-nav"/>
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="me-auto">
                        <Nav.Link href="/">Home</Nav.Link>
                        <Nav.Link href="/products">All Products</Nav.Link>
                        <NavDropdown title="Products by Category" id="basic-nav-dropdown">
                            <NavDropdown.Item as={Link} to="/products/category/appliances">
                                Appliances
                            </NavDropdown.Item>
                            <NavDropdown.Item as={Link} to="/products/category/art-and-collectibles">
                                Art & Collectibles
                            </NavDropdown.Item>
                            <NavDropdown.Item as={Link} to="/products/category/books">
                                Books
                            </NavDropdown.Item>
                            <NavDropdown.Item as={Link} to="/products/category/clothing">
                                Clothing
                            </NavDropdown.Item>
                            <NavDropdown.Item as={Link} to="/products/category/computers">
                                Computers
                            </NavDropdown.Item>
                            <NavDropdown.Item as={Link} to="/products/category/electronics">
                                Electronics
                            </NavDropdown.Item>
                            <NavDropdown.Item as={Link} to="/products/category/food-and-drink">
                                Food & Drink
                            </NavDropdown.Item>
                            <NavDropdown.Item as={Link} to="/products/category/furniture">
                                Furniture
                            </NavDropdown.Item>
                            <NavDropdown.Item as={Link} to="/products/category/health-and-beauty">
                                Health & Beauty
                            </NavDropdown.Item>
                            <NavDropdown.Item as={Link} to="/products/category/home-and-garden">
                                Home & Garden
                            </NavDropdown.Item>
                            <NavDropdown.Item as={Link} to="/products/category/jewelry">
                                Jewelry
                            </NavDropdown.Item>
                            <NavDropdown.Item as={Link} to="/products/category/music">
                                Music
                            </NavDropdown.Item>
                            <NavDropdown.Item as={Link} to="/products/category/pets">
                                Pets
                            </NavDropdown.Item>
                            <NavDropdown.Item as={Link} to="/products/category/sports-and-outdoors">
                                Sports & Outdoors
                            </NavDropdown.Item>
                            <NavDropdown.Item as={Link} to="/products/category/toys">
                                Toys
                            </NavDropdown.Item>
                            <NavDropdown.Item as={Link} to="/products/category/vehicles">
                                Vehicles
                            </NavDropdown.Item>
                            <NavDropdown.Item as={Link} to="/products/category/videogames">
                                Videogames
                            </NavDropdown.Item>
                            <NavDropdown.Item as={Link} to="/products/category/other">
                                Other
                            </NavDropdown.Item>
                            <NavDropdown.Divider/>
                            <NavDropdown.Item href="#action/3.4">
                                Test for divider
                            </NavDropdown.Item>
                        </NavDropdown>
                        <Nav.Link href="/products/featured">Featured Products</Nav.Link>
                    </Nav>
                </Navbar.Collapse>
            </div>


        </Navbar>
    );
}

export default AppNavbar;
