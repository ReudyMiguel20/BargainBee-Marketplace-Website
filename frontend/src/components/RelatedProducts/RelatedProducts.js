import React, {useState} from "react";
import {useQuery} from "@tanstack/react-query";
import SingleProduct from "../SingleProduct/SingleProduct";
import RelatedProductView from "./RelatedProductView/RelatedProductView";
import "./RelatedProducts.css";

const RelatedProducts = (id) => {
    // const [relatedProducts, setRelatedProducts] = useState(null);
    const {data, error, status} = useQuery ( {
        queryKey: ["relatedProducts"],
        queryFn: () => fetch("http://localhost:8080/api/item/related/" + id.id).then((res) => res.json())
    });

    if (status === 'loading') {
        return <span className="fetching-status">Loading...</span>
    }

    if (status === 'error') {
        return <span className="fetching-status">There was an error fetching products... Try again later.</span>
    }


    return (
      <div className="related-products">
          {Array.isArray(data) && data?.map((product) => (
              <div key={product.item_id}>
                  <RelatedProductView product={product} />
              </div>
              ))};
      </div>
    );


}

export default RelatedProducts;