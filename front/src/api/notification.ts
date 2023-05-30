import instance from "./interceptors";

export const notification = async () => {
	const url = `/api/notifications?page=0&size=12`;
	try {
		const response = await instance.get(url);
		console.log(response.data.data);
		return response.data.data;
	} catch (error) {
		console.log(error);
	}
};
