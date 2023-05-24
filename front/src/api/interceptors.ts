import axios from "axios";
import { reissueAccessToken } from "./reissueAccessToken";

const instance = axios.create();

instance.interceptors.response.use(
	function (response) {
		return response;
	},
	async function (error) {
		if (
			error.response?.data?.error === "ACCESS_TOKEN_EXPIRED" ||
			error.response?.statusText === "Unauthorized"
		) {
			if (await reissueAccessToken()) {
				return retryRequest(error.response.config);
			}
		}
		return Promise.reject(error);
	},
);

const retryRequest = async (request) => {
	let subscribers = [];
	try {
		const retryOriginalRequest = new Promise((resolve, reject) => {
			subscribers.push(async () => {
				try {
					resolve(instance(request));
				} catch (error) {
					reject(error);
				}
			});
		});
		subscribers.forEach((callback) => callback());
		return retryOriginalRequest;
	} catch (error) {
		return Promise.reject(error);
	}
};

export default instance;
