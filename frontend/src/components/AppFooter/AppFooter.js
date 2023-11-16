import "./AppFooter.css";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import {faFacebook, faGooglePlus, faInstagram, faXTwitter, faYoutube} from '@fortawesome/free-brands-svg-icons'

const AppFooter = () => {
    return (
        <footer>

            <div className="footerContainer">

                <div className="socialIcons">
                    <a href=""><FontAwesomeIcon icon={faFacebook} /></a>
                    <a href=""><FontAwesomeIcon icon={faInstagram} /></a>
                    <a href=""><FontAwesomeIcon icon={faXTwitter} /></a>
                    <a href=""><FontAwesomeIcon icon={faGooglePlus} /></a>
                    <a href=""><FontAwesomeIcon icon={faYoutube} /></a>
                </div>

                <div className="footerNav">
                    <ul>
                        <li><a href="">Home</a></li>
                        <li><a href="">About Us</a></li>
                        <li><a href="">Contact Us</a></li>
                        <li><a href="">Privacy Policy</a></li>
                        <li><a href="">Terms & Conditions</a></li>
                    </ul>
                </div>

                <div class="footerBottom">
                    <p>© 2023 All Rights Reserved. Developed By <a href="https://github.com/ReudyMiguel20">Reudy
                        Guerrero</a></p>
                </div>
            </div>

        </footer>
    );
}

export default AppFooter;