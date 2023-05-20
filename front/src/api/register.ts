import axios from "axios";
import ConfirmModal from "@/components/member/ConfirmModal/ConfirmModal";
import { useState } from "react";

export const register = (email: string, password: string, nickname: string) => {
	let data = JSON.stringify({
		email: email,
		password: password,
		nickname: nickname,
	});

	let config = {
		method: "post",
		maxBodyLength: Infinity,
		url: "/api/members/join",
		headers: {
			"Content-Type": "application/json",
		},
		data: data,
	};

	axios
		.request(config)
		.then((response) => {
			console.log(`회원가입 완.`);
			return true;
		})
		.catch((error) => {
			if (error.response.data.code === "NOT_EMPTY_INPUT_DATA") {
				alert(error.response.data.message);
			}
		});
};
