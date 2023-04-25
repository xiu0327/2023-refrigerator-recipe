import { useState } from "react";
import { Button } from "react-bootstrap";
import ConfirmModal from "../ConfirmModal/ConfirmModal";
import styles from "./ModalOnBtn.module.scss";

export default function ModalOnBtn(props: { title: string; ment: string }) {
	const [modalShow, setModalShow] = useState(false);
	return (
		<div className={`d-grid gap-2`}>
			<Button
				className={styles.modalOnBtn}
				variant="primary"
				size="lg"
				onClick={() => setModalShow(true)}
			>
				{props.title}
			</Button>

			<ConfirmModal
				ment={props.ment}
				show={modalShow}
				onHide={() => setModalShow(false)}
			/>
		</div>
	);
}
