import instance from "./interceptors";

export const putPassword = (password: string) => {
	instance
		.put("/api/members/password", {
			password: password,
		})
		.then(function (response) {
			console.log(response);
		})
		.catch(function (error) {
			console.log(error.response.data.error);
		});
};
