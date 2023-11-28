import React, {useState} from "react";
import {useQuery} from "@tanstack/react-query";

const RelatedProducts = () => {
    // const [relatedProducts, setRelatedProducts] = useState(null);
    const {data, error, status} = useQuery ( {
        queryKey: ["relatedProducts"],
        queryFn: () => fetch("")
    })



}

export default RelatedProducts;