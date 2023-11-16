import { useQuery } from "@tanstack/react-query";

export const Products = () => {
    const { data, error } = useQuery({
        queryKey: ["products"],
        queryFn: () => fetch("http://localhost:8080/api/item/all").then((res) => res.json())
    });

    return (
        <div className="products">
            <h1>Products</h1>
            {data?.map((product) => (
                <div key={product.id}>
                    <h3>{product.item_id}</h3>
                    <h2>{product.item_name}</h2>
                    <p>{product.description}</p>
                    <p>{product.price}</p>
                </div>
            ))}
        </div>
    );
}

export default Products;