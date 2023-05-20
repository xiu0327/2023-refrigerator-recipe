import { useState } from "react";
import instance from "./interceptors";

export const getRecipe = () => {
	const [recipe, setRecipe] = useState<Array<object>>([]);

	const url = `/api/recipe?page=0`;
	instance
		.get(url)
		.then((response) => {
			response.data.data.map((i) => setRecipe(i.image));
			console.log(recipe);
		})
		.catch((error) => {
			console.log(error);
		});
};
