import InputContent from "@/components/member/InputContent/InputContent";
import styles from "./unregister.module.scss";
import { Button } from "react-bootstrap";
import { useState } from "react";
import UnregisterModal from "@/components/member/UnregisterModal/UnregisterModal";

export default function Unregister() {
	const [showModal, setShowModal] = useState(false);

	const handleClose = () => setShowModal(false);
	const handleShow = () => setShowModal(true);

	return (
		<div className={styles.loginEmailContainer}>
			<span className={styles.loginEmailTitle}>회원탈퇴</span>
			<InputContent title="비밀번호" />
			<Button
				className={styles.linkButton}
				variant="primary"
				onClick={handleShow}
			>
				다음으로
			</Button>
			{/* 비밀번호가 맞으면 모달 띄우기 */}
			<UnregisterModal show={showModal} handleClose={handleClose} />
		</div>
	);
}
