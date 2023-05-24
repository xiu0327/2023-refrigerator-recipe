import instance from "./interceptors";

export const changePassword = (password: string) => {
	instance
		.put("/api/members/password", {
			password: password,
		})
		.then(function (response) {
			//console.log(response);
		})
		.catch(function (error) {
			console.log(error.response);
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
