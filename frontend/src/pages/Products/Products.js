import {useQuery} from "@tanstack/react-query";
import SingleProduct from "../../components/SingleProduct/SingleProduct";
import "./Products.css";

export const Products = () => {
    const {data, error, status} = useQuery({
        queryKey: ["products"],
        queryFn: () => fetch("http://localhost:8080/api/item/all").then((res) => res.json())
    });

    if (status === 'loading') {
        return <span className="fetching-status">Loading...</span>
    }

    if (status === 'error') {
        return <span className="fetching-status">There was an error fetching products... Try again later.</span>
    }

    return (
        <div className="products">
            <div className="all-products">
                {Array.isArray(data) && data?.map((product) => (
                    <div key={product.item_id}>
                        <SingleProduct product={product}/>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default Products;