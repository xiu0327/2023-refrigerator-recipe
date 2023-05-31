import router from "next/router";
import instance from "./interceptors";

const JWT_EXPIRY_TIME = 2 * 60 * 1000;

export const login = async (email: string, password: string) => {
	const url = `/api/auth/login`;
	const body = { email, password };
	try {
		const response = await instance.post(url, body);
		loginSuccess(response);
		console.log("login success");
		router.push("/refrigerator");
	} catch (error: any) {
		alert(error.response.data.message);
		router.reload();
	}
};

export const silentRefresh = async () => {
	console.log(window.location.href);
	const url = `/api/auth/reissue`;
	try {
		const response = await instance.post(url);
		loginSuccess(response);
		console.log("silent refresh works");
	} catch (error) {
		console.error(error);
		// TODO: 로그인 만료 안내 모달
		router.push("/");
	}
};

const loginSuccess = (response: any) => {
	instance.defaults.headers.common[
		"Authorization"
	] = `Bearer ${response.data.accessToken}`;

	console.log(response.data);
	setTimeout(silentRefresh, JWT_EXPIRY_TIME - 60000);
};
