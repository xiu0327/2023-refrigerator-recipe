import instance from "./interceptors";

export const getStarPreview = async () => {
	try {
		const response = await instance.get("/api/my-score/preview?size=5");
		console.log(response);
		return response;
	} catch (error) {
		console.log(error);
	}
};
