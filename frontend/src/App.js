import "./App.css";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import AppNavbar from "./components/AppNavbar/AppNavbar";
import {BrowserRouter as Router, Routes, Route} from "react-router-dom";
import {Home} from "./pages/Home.js";
import { Products } from "./pages/Products/Products.js";
import AppFooter from "./components/AppFooter/AppFooter";
import ProductDetails from "./pages/ProductDetails/ProductDetails";


function App() {
    const client = new QueryClient();

    return (
        <div className="app">
            <QueryClientProvider client={client}>
                <Router>
                    <AppNavbar/>
                    <div className="main-content">
                        <Routes>
                            <Route path="/" element={<Home/>}/>
                            <Route path="/products" element={<Products />}/>
                            <Route path="/products/:id" element={<ProductDetails />}/>
                            <Route path="*" element={<h1>Not Found</h1>}/>
                        </Routes>
                    </div>
                </Router>
            </QueryClientProvider>
            <AppFooter className="AppFooter" />
        </div>
    );
}

export default App;
