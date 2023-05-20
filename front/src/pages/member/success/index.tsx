import instance from "@/api/interceptors";
import router from "next/router";
import { useEffect } from "react";

export default function Success() {
	useEffect(() => {
		const queryString = window.location.search;
		const urlParams = new URLSearchParams(queryString);
		const paramValue = urlParams.get("token");
		console.log(paramValue); //TODO: 지워야됨 ...

		instance.defaults.headers.common["Authorization"] = `Bearer ${paramValue}`;
		router.push("/member/profile"); // 냉장고 화면으로 가게 바꿔야됨
	}, []);
}
