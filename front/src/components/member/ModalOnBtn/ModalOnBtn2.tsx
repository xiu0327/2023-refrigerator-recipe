import { useState } from "react";
import { Button } from "react-bootstrap";
import ConfirmModal from "../ConfirmModal/ConfirmModal";
import styles from "./ModalOnBtn.module.scss";
import { changePassword } from "@/api";

export default function ModalOnBtn2({ title, ment, password }) {
	const [modalShow, setModalShow] = useState(false);

	const handleButtonClick = async () => {
		try {
			await changePassword(password);
			setModalShow(true);
		} catch (error) {
			alert(error.response.data.message);
		}
	};
	return (
		<div className={`d-grid gap-2`}>
			<Button
				className={styles.modalOnBtn}
				variant="primary"
				size="lg"
				onClick={() => {
					const result = changePassword(password);
					console.log(result);
					setModalShow(true);
				}}
			>
				{title}
			</Button>

			<ConfirmModal
				ment={ment}
				show={modalShow}
				onHide={() => setModalShow(false)}
			/>
		</div>
	);
}
//여기 비밀번호 변경 api 쓰기
