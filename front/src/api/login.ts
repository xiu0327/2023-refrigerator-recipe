import router from "next/router";
import instance from "./interceptors";
import { reissueAccessToken } from "./reissueAccessToken";

export const login = async (email: string, password: string) => {
	const url = `/api/auth/login`;
	const body = { email: email, password: password };

	try {
		const response = await instance.post(url, body);
		instance.defaults.headers.common[
			"Authorization"
		] = `Bearer ${response.data.accessToken}`;
		console.log(response.data.accessToken);
		router.push("/notification");
	} catch (error) {
		alert(error.response.data.message);
		router.reload();
	}
};
