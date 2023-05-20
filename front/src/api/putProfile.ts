import instance from "./interceptors";

export const putProfile = (image: any) => {
	instance
		.put("/api/members/profile", {
			imageName: image,
		})
		.then(function (response) {
			console.log(response);
		})
		.catch(function (error) {
			console.log(error);
		});
};
