import "./App.css";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import AppNavbar from "./components/AppNavbar/AppNavbar";
import {BrowserRouter as Router, Routes, Route} from "react-router-dom";
import {Home} from "./pages/Home.js";
import { Products } from "./pages/Products.js";


function App() {
    const client = new QueryClient();

    return (
        <div className="app">
            <QueryClientProvider client={client}>
            <Router>
                <AppNavbar/>
                <Routes>
                    <Route path="/" element={<Home/>}/>
                    <Route path="/products" element={<Products />}/>
                    {/*<Route path"/products/:id" element={<Product />}/>*/}
                    <Route path="*" element={<h1>Not Found</h1>}/>
                </Routes>
            </Router>
            </QueryClientProvider>
        </div>
    );
}

export default App;
