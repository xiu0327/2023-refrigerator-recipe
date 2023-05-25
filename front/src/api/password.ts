import router from "next/router";
import instance from "./interceptors";
import { AxiosError } from "axios";

export const changePassword = (password: string) => {
	return new Promise((resolve, reject) => {
		instance
			.put("/api/members/password", {
				password: password,
			})
			.then(function (response) {
				// API 호출 성공
				resolve(response); // 반환할 데이터를 resolve로 전달
			})
			.catch(function (error) {
				// API 호출 실패
				reject(error); // 에러를 reject로 전달
			});
	});
};

export const findPassword = async (email: string) => {
	instance
		.post("/api/members/password/find", {
			email: email,
		})
		.then(function (response) {
			const accessToken = response.data.authToken;
			instance.defaults.headers.common[
				"Authorization"
			] = `Bearer ${accessToken}`;
			localStorage.setItem("accessToken", accessToken);
			console.log("토큰 재발행!");
		})
		.catch(function (error) {
			console.log(error);
		});
};
