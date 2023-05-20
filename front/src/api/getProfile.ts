import instance from "./interceptors";

export const getProfile = async () => {
	try {
		const response = await instance.get("/api/members");
		return response;
	} catch (error) {
		console.log("잘못된 접근입니다.");
	}
};

// export const getProfile = () => {
// 	const url = `/api/members`;
// 	instance
// 		.get(url)
// 		.then((response) => {
// 			console.log(response);
// 		})
// 		.catch((error) => {
// 			console.log(error);
// 		});
// };
