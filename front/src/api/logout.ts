import router from "next/router";
import instance from "./interceptors";

export const logout = () => {
	instance
		.get("/api/auth/logout")
		.then(function (response) {
			// 바뀐 부분 아래 1줄
			instance.defaults.headers.common = {};
			console.log("로그아웃 완료!");
			router.push("/");
		})
		.catch(function (error) {
			console.log(error);
		});
};
