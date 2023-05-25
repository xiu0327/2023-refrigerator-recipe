import instance from "./interceptors";

export const getCommentsPreview = async (recipeID: number) => {
	const url = `/api/comments/preview?recipeId=${recipeID}&size=3`;
	try {
		const response = await instance.get(url);
		return response.data;
	} catch (error) {
		console.error(error);
	}
};

export const getCommentsByLike = async (recipeID: number, page: number) => {
	const url = `/api/comments/heart?recipeId=${recipeID}&page=${page}`;
	try {
		const response = await instance.get(url);
		return response.data.comments;
	} catch (error) {
		console.error(error);
	}
};

export const getCommentsByDate = async (recipeID: number, page: number) => {
	const url = `/api/comments/date?recipeId=${recipeID}&page=${page}`;
	try {
		const response = await instance.get(url);
		return response.data.comments;
	} catch (error) {
		console.error(error);
	}
};

export const getMyComments = async (recipeID: number) => {
	const url = `/api/comments/my/${recipeID}`;
	try {
		const response = await instance.get(url);
		return response.data.comments;
	} catch (error) {
		console.error(error);
	}
};

export const addComment = async (recipeID: number, content: string) => {
	const url = `/api/comments/`;
	const body = { recipeId: recipeID, content: content };
	try {
		const response = await instance.post(url, body);
		console.log(response.data);
	} catch (error) {
		console.error(error);
	}
};

export const modifyComment = async (commentID: number, content: string) => {
	const url = `/api/comments/`;
	const body = { commentID: commentID, content: content };
	try {
		const response = await instance.put(url, body);
		console.log(response.data);
	} catch (error) {
		console.error(error);
	}
};

export const deleteComment = async (commentID: number) => {
	const url = `/api/comments/${commentID}`;
	try {
		const response = await instance.delete(url);
		console.log(response.data);
	} catch (error) {
		console.error(error);
	}
};

export const likeComment = async (commentID: number) => {
	const url = `/api/comments/${commentID}/heart/add`;
	try {
		const response = await instance.post(url);
		console.log(response.data);
	} catch (error) {
		console.error(error);
	}
};

export const unlikeComment = async (commentID: number) => {
	const url = `/api/comments/${commentID}/heart/reduce`;
	try {
		const response = await instance.post(url);
		console.log(response.data);
	} catch (error) {
		console.error(error);
	}
};

export const getHeartCommentIDs = async () => {
	const url = `/api/comments/heart/list`;
	try {
		const response = await instance.get(url);
		return response.data.data;
	} catch (error) {
		console.error(error);
	}
};
