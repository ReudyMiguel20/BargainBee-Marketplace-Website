import "./App.css";
import {QueryClient, QueryClientProvider} from "@tanstack/react-query";
import AppNavbar from "./components/AppNavbar/AppNavbar";
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import {Home} from "./pages/Home.js";
import {Products} from "./pages/Products/Products.js";
import AppFooter from "./components/AppFooter/AppFooter";
import ProductDetails from "./pages/ProductDetails/ProductDetails";
import Category from "./pages/Products/Category/Category";
import SearchResultProducts from "./pages/Products/SearchResultProducts/SearchResultProducts";
import UserLogin from "./pages/UserLogin";
import UserContext from "./UserContext";
import {useState} from "react";


function App() {
    const client = new QueryClient();
    const [userLoggedIn, setUserLoggedIn] = useState(false);

    return (
        <div className="app">
            <UserContext.Provider value={{userLoggedIn, setUserLoggedIn}}>
                <QueryClientProvider client={client}>
                    <Router>
                        <AppNavbar/>
                        <div className="main-content">
                            <Routes>
                                <Route path="/" element={<Home/>}/>
                                <Route path="/products" element={<Products/>}/>
                                <Route path="/products/:id" element={<ProductDetails/>}/>
                                <Route path="/category/:category" element={<Category/>}/>
                                <Route path="/search/:search" element={<SearchResultProducts/>}/>
                                <Route path="/login" element={<UserLogin/>}/>
                                <Route path="*" element={<h1>Not Found</h1>}/>
                            </Routes>
                        </div>
                    </Router>
                </QueryClientProvider>
                <AppFooter className="AppFooter"/>
            </UserContext.Provider>
        </div>
    );
}

export default App;
