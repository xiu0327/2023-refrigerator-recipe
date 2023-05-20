import { useState } from "react";
import { Button } from "react-bootstrap";
import ConfirmModal from "../ConfirmModal/ConfirmModal";
import styles from "./ModalOnBtn.module.scss";
import axios from "axios";

export default function ModalOnBtn({ title, ment, email, password, nickname }) {
	const [showModal, setShowModal] = useState(false);

	const register = (email: string, password: string, nickname: string) => {
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
				setShowModal(true);
			})
			.catch((error) => {
				if (error.response.data.code === "NOT_EMPTY_INPUT_DATA") {
					setShowModal(false);
					alert(error.response.data.message);
				}
			});
	};

	return (
		<div className={`d-grid gap-2`}>
			<Button
				className={styles.modalOnBtn}
				variant="primary"
				size="lg"
				onClick={() => {
					register(email, password, nickname);
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
