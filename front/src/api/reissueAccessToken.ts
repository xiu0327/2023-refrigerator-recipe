import instance from "./interceptors";

export const reissueAccessToken = async () => {
	const url = `/api/auth/reissue`;
	try {
		const response = await instance.post(url);
		instance.defaults.headers.common[
			"Authorization"
		] = `Bearer ${response.data.accessToken}`;
	} catch (error) {
		console.error(error);
	}
};
