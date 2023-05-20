import instance from "./interceptors";

export const getRecipeList = async (page: number) => {
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

export const getHeartCommentIds = async () => {
	const url = `/api/comments/heart/list`;
	try {
		const response = await instance.get(url);
		console.log(response.data.data);
		return response.data.data;
	} catch (error) {
		console.error(error);
	}
};

export const addComment = (recipeId, content) => {
	const url = `/api/comments`;
	return instance
		.post(url, {
			recipeId: recipeId,
			content: content,
		})
		.then((response) => {
			console.log(response.data);
		})
		.catch((error) => {
			console.log(error);
		});
};

export const modifyComment = (commentId, content) => {
	const url = `/api/comments`;
	console.log(commentId, content);
	return instance
		.put(url, {
			commentID: commentId,
			content: content,
		})
		.then((response) => {
			console.log(response.data);
		})
		.catch((error) => {
			console.log(error);
		});
};

export const deleteComment = (commentId) => {
	const url = `/api/comments/${commentId}`;
	instance
		.delete(url)
		.then((response) => {
			console.log(response.data);
		})
		.catch((error) => {
			console.log(error);
		});
};

export const likeComment = (commentId) => {
	const url = `/api/comments/${commentId}/heart/add`;
	instance
		.post(url)
		.then((response) => {
			console.log(response.data);
		})
		.catch((error) => {
			console.log(error);
		});
};

export const unlikeComment = (commentId) => {
	const url = `/api/comments/${commentId}/heart/reduce`;
	instance
		.post(url)
		.then((response) => {
			console.log(response.data);
		})
		.catch((error) => {
			console.log(error);
		});
};

export const getCommentsByLike = (recipeId, page, size) => {
	const url = `/api/comments/heart?recipeId=${recipeId}&page=${page}&size=${size}`;
	return instance
		.get(url)
		.then((response) => {
			console.log("all comments by like", response.data.comments);
			return response.data.comments;
		})
		.catch((error) => {
			console.log(error);
		});
};

export const getCommentsByDate = (recipeId, page, size) => {
	const url = `/api/comments/heart?recipeId=${recipeId}&page=${page}&size=${size}`;
	return instance
		.get(url)
		.then((response) => {
			console.log("all comments by date", response.data.comments);
			return response.data.comments;
		})
		.catch((error) => {
			console.log(error);
		});
};

export const getRecipeCommentPreview = (recipeId, size, setData, setNum) => {
	const url = `/api/comments/preview?recipeId=${recipeId}&size=${size}`;
	instance
		.get(url)
		.then((response) => {
			console.log(response.data);
			setData(response.data.comments);
			setNum(response.data.count);
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

export const searchRecipe = async (page, searchWord) => {
	const url = `/api/recipe/search?page=${page}&searchWord=${searchWord}`;
	return instance
		.get(url)
		.then((response) => {
			console.log("연결은된것이냐...");
			console.log(response.data.data);
			return response.data.data;
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

export const getMyComments = async (recipeID) => {
	const url = `/api/comments/my/${recipeID}`;
	return instance
		.get(url)
		.then((response) => {
			console.log("my comments", response.data.comments);
			return response.data.comments;
		})
		.catch((error) => {
			console.log(error);
		});
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
