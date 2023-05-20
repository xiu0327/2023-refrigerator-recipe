import { SetStateAction } from "react";
import instance from "./interceptors";

export const getIngredients = async (
	page: number,
	storage: string,
	isExpired: boolean,
) => {
	const url = `/api/ingredients?page=${page}&storage=${storage}&deadline=${isExpired}`;
	try {
		const response = await instance.get(url);
		console.log(
			"[api] page :",
			page,
			" arraylength :",
			response.data.data.length,
		);
		return response.data.data;
	} catch (error) {
		console.log(error);
		throw error;
	}
};

export const getIngredientInfo = async (ingredientID: number) => {
	const url = `/api/ingredients/${ingredientID}`;
	try {
		const response = await instance.get(url);
		return response.data;
	} catch (error) {
		console.log(error);
		throw error;
	}
};

export const getAllIngredients = async () => {
	const url = `/api/ingredients/search`;
	try {
		const response = await instance.get(url);
		return response.data.data;
	} catch (error) {
		console.log(error);
		throw error;
	}
};

export const getMatchedIngredients = async (keyword: string) => {
	const url = `/api/word-completion/ingredient?keyword=${keyword}`;
	try {
		const response = await instance.get(url);
		return response.data.data.slice(0, 5);
	} catch (error) {
		console.log(error);
		throw error;
	}
};

export const getIngredientUnit = async (name: string) => {
	const url = `/api/ingredients/unit?name=${name}`;
	try {
		const response = await instance.get(url);
		return response.data.unit;
	} catch (error) {
		console.log(error);
		throw error;
	}
};

export const requestIngredient = async (name: string, unit: string) => {
	const url = `/api/ingredients/propose`;
	const body = { name: name, unit: unit };
	try {
		await instance.post(url, body);
	} catch (error) {
		console.log(error);
		throw error;
	}
};

export const addIngredient = async (body: Object) => {
	const url = `/api/ingredients`;
	try {
		await instance.post(url, body);
	} catch (error) {
		console.log(error);
		throw error;
	}
};

export const deleteIngredient = async (ingredientID: number) => {
	const url = `/api/ingredients/${ingredientID}`;
	try {
		await instance.delete(url);
	} catch (error) {
		console.log(error);
		throw error;
	}
};

export const modifyIngredient = async (ingredientID: number, body: Object) => {
	const url = `/api/ingredients/${ingredientID}`;
	try {
		await instance.put(url, body);
	} catch (error) {
		console.log(error);
		throw error;
	}
};
