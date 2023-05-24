import router from "next/router";
import instance from "./interceptors";

export const login = (email: string, password: string) => {
	instance
		.post("/api/auth/login", {
			email: email,
			password: password,
		})
		.then(function (response) {
			const { _, accessToken, refreshToken } = response.data;
			instance.defaults.headers.common[
				"Authorization"
			] = `Bearer ${accessToken}`;
			localStorage.setItem("accessToken", accessToken);
			localStorage.setItem("refreshToken", refreshToken);
			console.log("토큰 초기 설정 완료!");
			console.log(accessToken);
			router.push("/mypage");
		})
		.catch(function (error) {
			alert(error.response.data.message);
			router.reload();
		});
};
