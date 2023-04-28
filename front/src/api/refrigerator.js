import { api } from "./api";

export const getMatchedIngredients = (keyword, setData) => {
	api
		.get(`/word-completion/ingredient?keyword=${keyword}`)
		.then((response) => {
			setData(response.data.data.slice(0, 5));
		})
		.catch((error) => {
			console.log(error);
		});
};
