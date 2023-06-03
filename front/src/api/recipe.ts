import { RecipeDeductedIngredient } from "@/types";
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

export const getRecipe = async (recipeID: number) => {
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

export const getRecipeIngredients = async (recipeID: number) => {
	const url = `/api/recipe/${recipeID}/ingredient/volume`;
	try {
		const response = await instance.get(url);
		return response.data.data;
	} catch (error) {
		console.error(error);
	}
};

export const getRecommendedRecipes = async () => {
	const url = `/api/recipe/recommend`;
	try {
		const response = await instance.get(url);
		return response.data.data;
	} catch (error) {
		console.error(error);
	}
};

export const getRecipeFoodType = async () => {
	const url = `/api/recipe/search/condition/food-type`;
	try {
		const response = await instance.get(url);
		return response.data.data;
	} catch (error) {
		console.error(error);
	}
};

export const getRecipeIngredientCategory = async () => {
	const url = `/api/recipe/search/condition/category`;
	try {
		const response = await instance.get(url);
		return response.data.data;
	} catch (error) {
		console.error(error);
	}
};

export const getRecipeRecipeType = async () => {
	const url = `/api/recipe/search/condition/recipe-type`;
	try {
		const response = await instance.get(url);
		return response.data.data;
	} catch (error) {
		console.error(error);
	}
};

export const getRecipeDifficulty = async () => {
	const url = `/api/recipe/search/condition/difficulty`;
	try {
		const response = await instance.get(url);
		return response.data.data;
	} catch (error) {
		console.error(error);
	}
};

export const deductIngredient = async (
	ingredients: RecipeDeductedIngredient[],
) => {
	const url = `/api/ingredients/deduction`;
	console.log(ingredients);
	try {
		await instance.put(url, { ingredients });
	} catch (error) {
		console.error(error);
	}
};

export const rateRecipe = async (recipeID: number, score: number) => {
	const url = `/api/my-score/cooking?recipeId=${recipeID}&score=${score}`;
	try {
		await instance.post(url);
	} catch (error) {
		console.error(error);
	}
};

export const getOwnedIngredientIDs = async (recipeID: number) => {
	const url = `/api/ingredients/owned/${recipeID}`;
	try {
		const response = await instance.get(url);
		return response.data.data;
	} catch (error) {
		console.log(error);
	}
};
