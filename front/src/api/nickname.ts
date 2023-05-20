import instance from "./interceptors";

export const nickname = (nick: string) => {
	instance
		.put("/api/members/nickname", {
			nickname: nick,
		})
		.then(function (response) {
			console.log(response);
		})
		.catch(function (error) {
			console.log(error.response.data.error);
		});
};
