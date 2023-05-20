import axios from "axios";

export const getProfileList = async () => {
	try {
		const response = await axios.get("/api/members/profile/list");
		return response.data.data.map((item: any) => item.imageUrl);
	} catch (error) {
		console.log("프로필 이미지 접근 에러:", error);
		throw error;
	}
};
