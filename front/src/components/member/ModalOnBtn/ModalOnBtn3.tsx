import { useState } from "react";
import { Button } from "react-bootstrap";
import ConfirmModal from "../ConfirmModal/ConfirmModal";
import styles from "./ModalOnBtn.module.scss";
import axios from "axios";
import instance from "@/api/interceptors";
import { login } from "@/api/login";

export default function ModalOnBtn3({ title, ment, image }) {
	const [showModal, setShowModal] = useState(false);
	//login("member@naver.com", "member123!!");
	const putProfile = (nick: any, image: any) => {
		const headers = {
			"Init-Status": "true",
			// 다른 헤더 필드들은 삭제하거나 주석 처리
		};
		instance
			.put(
				"/api/members/init",
				{
					nickname: nick,
					imageName: image,
				},
				{ headers },
			)
			.then(function (response) {
				console.log(response);
				setShowModal(true);
			})
			.catch(function (error) {
				console.log(error);
				setShowModal(false);
				alert(error.response.data.message);
			});
	};

	return (
		<div className={`d-grid gap-2`}>
			<Button
				className={styles.modalOnBtn}
				variant="primary"
				size="lg"
				onClick={() => {
					putProfile(nickname, image);
				}}
			>
				{title}
			</Button>

			<ConfirmModal
				ment={ment}
				show={showModal}
				onHide={() => setShowModal(false)}
			/>
		</div>
	);
}
