import { useState } from "react";
import { Button } from "react-bootstrap";
import ConfirmModal from "../ConfirmModal/ConfirmModal";
import styles from "./ModalOnBtn.module.scss";
import { putPassword } from "@/api/putPassword";

export default function ModalOnBtn2({ title, ment, password }) {
	const [modalShow, setModalShow] = useState(false);
	return (
		<div className={`d-grid gap-2`}>
			<Button
				className={styles.modalOnBtn}
				variant="primary"
				size="lg"
				onClick={() => {
					putPassword(password);
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
