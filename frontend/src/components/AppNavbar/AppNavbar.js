import Container from "react-bootstrap/Container";
import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";
import NavDropdown from "react-bootstrap/NavDropdown";
import beeicon from "../../assets/bee.png";
import "./AppNavbar.css";
import {Link} from "react-router-dom";
import {faMagnifyingGlass} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

function AppNavbar() {
    return (




        <Navbar data-bs-theme="dark" expand="lg" className="bg-body-tertiary">
            <div className="upper-nav">
                <Navbar.Brand className="title" href="/"><img src={beeicon}  style={{ width: '40px', height: '40px' }} /> Bargain Bee</Navbar.Brand>
                <div className="search-container">
                    <input className="search-bar" type="text" placeholder="Search"/>
                    <button className="search-button"><FontAwesomeIcon icon={faMagnifyingGlass} style={{color: "#000000",}} /></button>
                </div>

            </div>
            <div className="lower-nav">
                <Navbar.Toggle aria-controls="basic-navbar-nav"/>
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="me-auto">
                        <Nav.Link href="/">Home</Nav.Link>
                        <Nav.Link href="/products">Items</Nav.Link>
                        <NavDropdown title="Categories" id="basic-nav-dropdown">
                            <Link to="/category/appliances">
                                <NavDropdown.Item href="#action/3.3">Appliances</NavDropdown.Item>
                            </Link>
                            <Link to="/category/artandcollectibles">
                                <NavDropdown.Item href="#action/3.3">Art & Collectibles</NavDropdown.Item>
                            </Link>
                            <Link to="/category/books">
                                <NavDropdown.Item href="#action/3.3">Books</NavDropdown.Item>
                            </Link>
                            <Link to="/category/clothing">
                                <NavDropdown.Item href="#action/3.3">Clothing</NavDropdown.Item>
                            </Link>
                            <Link to="/category/computers">
                                <NavDropdown.Item href="#action/3.3">Computers</NavDropdown.Item>
                            </Link>
                            <Link to="/category/electronics">
                                <NavDropdown.Item href="#action/3.3">Electronics</NavDropdown.Item>
                            </Link>
                            <Link to="/category/foodanddrink">
                                <NavDropdown.Item href="#action/3.3">Food & Drink</NavDropdown.Item>
                            </Link>
                            <Link to="/category/furniture">
                                <NavDropdown.Item href="#action/3.3">Furniture</NavDropdown.Item>
                            </Link>
                            <Link to="/category/healthandbeauty">
                                <NavDropdown.Item href="#action/3.3">Health & Beauty</NavDropdown.Item>
                            </Link>
                            <Link to="/category/homeandgarden">
                                <NavDropdown.Item href="#action/3.3">Home & Garden</NavDropdown.Item>
                            </Link>
                            <Link to="/category/jewelry">
                                <NavDropdown.Item href="#action/3.3">Jewelry</NavDropdown.Item>
                            </Link>
                            <Link to="/category/music">
                                <NavDropdown.Item href="#action/3.3">Music</NavDropdown.Item>
                            </Link>
                            <Link to="/category/pets">
                                <NavDropdown.Item href="#action/3.3">Pets</NavDropdown.Item>
                            </Link>
                            <Link to="/category/sportsandoutdoors">
                                <NavDropdown.Item href="#action/3.3">Sports & Outdoors</NavDropdown.Item>
                            </Link>
                            <Link to="/category/toys">
                                <NavDropdown.Item href="#action/3.3">Toys</NavDropdown.Item>
                            </Link>
                            <Link to="/category/vehicles">
                                <NavDropdown.Item href="#action/3.3">Vehicles</NavDropdown.Item>
                            </Link>
                            <Link to="/category/videogames">
                                <NavDropdown.Item href="#action/3.3">Videogames</NavDropdown.Item>
                            </Link>
                            <Link to="/category/other">
                                <NavDropdown.Item href="#action/3.3">Other</NavDropdown.Item>
                            </Link>
                            <NavDropdown.Divider/>
                            <NavDropdown.Item href="#action/3.4">
                                Test for divider
                            </NavDropdown.Item>
                        </NavDropdown>
                    </Nav>
                </Navbar.Collapse>
            </div>


        </Navbar>
    );
}

export default AppNavbar;
