import axios from "axios";

const BASE_URL = "http://127.0.0.1:8080";
const AUTHORIZATION =
	"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJlbWFpbDEyMzRAZ21haWwuY29tIiwiYXV0aCI6IlJPTEVfU1RFQURZX1NUQVRVUyIsImlhdCI6MTY4MjY4ODMwOCwiZXhwIjoxNjgyNjkwMTA4fQ.Z0tpDVnr_RA9VYgCLiFNeQGaymHXd_tKmXhErvptVBQhlnPPHDLVsFiJM_rY6wHNaafg8G6J53DlAuGwT28UQQ";

export const api = axios.create({
	baseURL: BASE_URL,
	headers: {
		Authorization: AUTHORIZATION,
	},
});
