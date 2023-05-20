import instance from "./interceptors";

export const login = async () => {
	return instance
		.post("/api/auth/login", {
			email: "mskim@gmail.com",
			password: "password123!",
		})
		.then(function (response) {
			const { _, accessToken, refreshToken } = response.data;
			instance.defaults.headers.common[
				"Authorization"
			] = `Bearer ${accessToken}`;
			localStorage.setItem("accessToken", accessToken);
			localStorage.setItem("refreshToken", refreshToken);
			localStorage.setItem("userID", "mskim@gmail.com");
			console.log("토큰 초기 설정 완료!");
			console.log(accessToken);
		})
		.catch(function (error) {
			console.log(error.response.data.error);
		});
};
