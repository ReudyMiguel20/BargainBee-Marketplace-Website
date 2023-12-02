import React from 'react'
import {useParams} from "react-router-dom";
import {useQuery} from "@tanstack/react-query";
import SingleProduct from "../../../components/SingleProduct/SingleProduct";
import "./SearchResultProducts.css";
import Cookies from "js-cookie";
import { jwtDecode } from 'jwt-decode';

const SearchResultProducts = () => {
    const {search} = useParams();
    const baseUrlSearch = "http://localhost:8080/api/item/search?item-name=" + search;

    const {data, error, status} = useQuery({
        queryKey: ['products', search],
        queryFn: () => fetch(baseUrlSearch).then((res) => res.json())
    });

    if (status === 'loading') {
        return <span className="fetching-status">Loading...</span>
    }

    if (status === 'error') {
        return <span className="fetching-status">There was an error fetching products... Try again later.</span>
    }

    // Cookies.set("access_token", "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJVS3FDSm5LVERTaTR0cjFuNThPaTdjZjNKVzlmcnIzanAtdUpRR1ZONGFVIn0.eyJleHAiOjE3MDE0Nzc2NzIsImlhdCI6MTcwMTQ0MTY3MiwianRpIjoiY2Q0YjhkYzEtYjQzYy00ZmJmLTgwYTItMDk5MGZiNTlhYjI3IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MTgxL3JlYWxtcy9zcHJpbmctYm9vdC1taWNyb3NlcnZpY2VzLXJlYWxtIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6ImIzYWI5NGU1LWQ2ODYtNDZmMS1hODE5LTYwNjZhMWQyZTRhZSIsInR5cCI6IkJlYXJlciIsImF6cCI6InNwcmluZy1jbG91ZC1jbGllbnQiLCJzZXNzaW9uX3N0YXRlIjoiYzE5YTY0NTUtOGE4ZS00OTUxLWE2YjMtNDA2MmVlNGU1NDVkIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyIvKiJdLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiIsImRlZmF1bHQtcm9sZXMtc3ByaW5nLWJvb3QtbWljcm9zZXJ2aWNlcy1yZWFsbSJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoiZW1haWwgcHJvZmlsZSIsInNpZCI6ImMxOWE2NDU1LThhOGUtNDk1MS1hNmIzLTQwNjJlZTRlNTQ1ZCIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwibmFtZSI6IlJldWR5IEd1ZXJyZXJvIiwicHJlZmVycmVkX3VzZXJuYW1lIjoidGVzdHVzZXIiLCJnaXZlbl9uYW1lIjoiUmV1ZHkiLCJmYW1pbHlfbmFtZSI6Ikd1ZXJyZXJvIiwiZW1haWwiOiJ0ZXN0ZXJAdGVzdGluZy5jb20ifQ.Ym4oLrfIMi5FNuT1CvEpijrXj16ex7cr4-8-pPYe9K-_NCGGvn47EBvg8_1CIYlDRbzLMzGDHMwM-HAHkZ8KZaOEi1pOfCYH5joUfGmMuiU1Zbr9zvTZdPlYumY65p2pV9zjB9NgBNjT8teUzpZRf_WS6EB-kacQjBeLjv1sZRUK7yyxqlt8b2MitvioejAiqY1yavvEjaSwGEX7d6FO5ZRs1g5QVuveqZJu1kphpuEwRFGFmKVKMzwCNN3CUhmHnYOekXrL2LAHPAAGeqkm_2thFW9N5fz7_We2mIyTOmaD87LOI9UkbRfgE88M4snxJkQhgj5RfT_k6djve1QpFw");
    //
    const decodedToken = jwtDecode(Cookies.get("access_token"));
    console.log(decodedToken.preferred_username);

    return (
        <div>
            <div className="product-search-name">
                <h3>Search result for: {search}</h3>
            </div>

            <div className="search-product-results">
            {Array.isArray(data) && data?.map((product) => (
                <div key={product.item_id}>
                    <SingleProduct product={product} />
                </div>
            ))}
            </div>

        </div>
    )
}

export default SearchResultProducts;