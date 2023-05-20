import instance from "./interceptors";

export const bookPreview = async () => {
	try {
		const response = await instance.get("/api/my-bookmark/list?page=0&size=11");
		console.log(response);
		return response;
	} catch (error) {
		console.log(error);
	}
};
