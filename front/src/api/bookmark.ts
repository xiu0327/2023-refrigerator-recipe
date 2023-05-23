import instance from "./interceptors";

export const getBookmarks = async (page: number) => {
	const url = `/api/my-bookmark/list?page=${page}`;
	try {
		const response = await instance.get(url);
		return response.data.bookmarks;
	} catch (error) {
		console.error(error);
	}
};

export const addBookmark = async (
	recipeID: number,
	setBookmarkIDs: Function,
) => {
	const url = `/api/my-bookmark/add/${recipeID}`;
	try {
		const response = await instance.post(url);
		setBookmarkIDs((prev: number[]) => [...prev, recipeID]);
	} catch (error) {
		console.error(error);
	}
};

export const removeBookmark = async (
	recipeID: number,
	setBookmarkIDs: Function,
) => {
	const url = `/api/my-bookmark/remove/${recipeID}`;
	try {
		const response = await instance.delete(url);
		setBookmarkIDs((prev: number[]) => prev.filter((id) => id !== recipeID));
	} catch (error) {
		console.error(error);
	}
};
