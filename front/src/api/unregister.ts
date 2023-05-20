import instance from "./interceptors";

export const unregister = async (password) => {
	try {
		const response = await instance.delete("/api/members", {
			data: { password: password },
		});
		console.log(response);
	} catch (error) {
		console.log(error);
	}
};
