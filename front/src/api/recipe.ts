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

export const searchRecipe = async (page: number, query: string) => {
	const url = `/api/recipe/search?page=${page}&searchWord=${query}`;
	try {
		const response = await instance.get(url);
		return response.data.data;
	} catch (error) {
		console.log(error);
	}
};

export const getBookmarkIDs = async () => {
	const url = `/api/my-bookmark/added`;
	return instance
		.get(url)
		.then((response) => {
			console.log(response.data.data);
			return response.data.data;
		})
		.catch((error) => {
			console.log(error);
		});
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

export const 레시피자동완성 = (keyword) => {
	const url = `/api/word-completion/recipe?keyword=${keyword}`;
	instance
		.get(url)
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

export const getRecommendationSearches = (setData) => {
	const url = `/api/search-word/recommend`;
	instance
		.get(url)
		.then((response) => {
			setData(response.data.data);
		})
		.catch((error) => {
			console.log(error);
		});
};

export const getLastSearches = (setData) => {
	const url = `/api/search-word/last`;
	instance
		.get(url)
		.then((response) => {
			console.log(response.data);
			setData([...new Set(response.data.data)]);
		})
		.catch((error) => {
			console.log(error);
		});
};

export const removeLateSearch = (word: string) => {
	const url = `/api/search-word?word=${word}`;
	instance
		.delete(url)
		.then((response) => {
			console.log(response.data);
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
