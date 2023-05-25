import instance from "./interceptors";

export const getRecipes = async (page: number) => {
	const url = `/api/recipe?page=${page}`;
	try {
		const response = await instance.get(url);
		return response.data.data;
	} catch (error) {
		console.error(error);
	}
};

export const getRecipe = async (recipeID: string) => {
	const url = `/api/recipe/${recipeID}`;
	try {
		const response = await instance.get(url);
		return response.data;
	} catch (error) {
		console.error(error);
	}
};

export const getRecipeSteps = async (recipeID: number) => {
	const url = `/api/recipe/${recipeID}/course`;
	try {
		const response = await instance.get(url);
		return response.data.data;
	} catch (error) {
		console.error(error);
	}
};

export const getRecipeIngredients = (recipeId) => {
	const url = `/api/recipe/${recipeId}/ingredient/volume`;
	instance
		.get(url)
		.then((response) => {
			console.log(response.data);
		})
		.catch((error) => {
			console.log(error);
		});
};

export const rateRecipe = (recipeId, score) => {
	const url = `/api/my-score/cooking?recipeId=${recipeId}&score=${score}`;
	instance
		.post(url)
		.then((response) => {
			console.log(response.data);
		})
		.catch((error) => {
			console.log(error);
		});
};

export const recommendRecipe = () => {
	const url = `/api/recipe/recommend`;
	instance
		.get(url)
		.then((response) => {
			console.log(response.data);
		})
		.catch((error) => {
			console.log(error);
		});
};

export const getRecipeFoodType = () => {
	const url = `/api/recipe/search/condition/food-type`;
	return instance
		.get(url)
		.then((response) => {
			return response.data.data;
		})
		.catch((error) => {
			console.log(error);
		});
};

export const getRecipeIngredientCategory = () => {
	const url = `/api/recipe/search/condition/category`;
	return instance
		.get(url)
		.then((response) => {
			return response.data.data;
		})
		.catch((error) => {
			console.log(error);
		});
};

export const getRecipeRecipeType = () => {
	const url = `/api/recipe/search/condition/recipe-type`;
	return instance
		.get(url)
		.then((response) => {
			return response.data.data;
		})
		.catch((error) => {
			console.log(error);
		});
};

export const getRecipeDifficulty = () => {
	const url = `/api/recipe/search/condition/difficulty`;
	return instance
		.get(url)
		.then((response) => {
			return response.data.data;
		})
		.catch((error) => {
			console.log(error);
		});
};

export const calculateIngredient = (ingredients) => {
	const url = `/api/ingredients/deduction`;
	instance
		.put(url, { ingredients: ingredients })
		.then((response) => {
			console.log(response.data);
		})
		.catch((error) => {
			console.log(error);
		});
};
// ingredients = [{name, volume, unit}, ...]

export const getOwnedIngredientIDs = async (recipeID: number) => {
	const url = `/api/ingredients/owned/${recipeID}`;
	try {
		const response = await instance.get(url);
		return response.data.data;
	} catch (error) {
		console.log(error);
	}
};
