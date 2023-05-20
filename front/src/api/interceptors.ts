import axios from "axios";

const instance = axios.create();

instance.interceptors.response.use(
	function (response) {
		return response;
	},
	async function (error) {
		const { response: errorResponse } = error;
		const originalRequest = error.config;

		if (errorResponse?.data?.error === "ACCESS_TOKEN_EXPIRED") {
			// TODO: accessToken 재발행 후 실패한 요청(originalRequest) 재시도
			return reissueAccessToken();
		}

		return Promise.reject(error);
	},
);

const reissueAccessToken = () => {
	const accessToken = localStorage.getItem("accessToken");
	const refreshToken = localStorage.getItem("refreshToken");
	axios
		.post("/api/auth/reissue", {
			accessToken: accessToken,
			refreshToken: refreshToken,
		})
		.then(function (response) {
			const accessToken = response.data.accessToken;
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

export default instance;
