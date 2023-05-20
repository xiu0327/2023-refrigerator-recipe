import styles from "./styles.module.scss";
import { useState } from "react";
import InputContent from "@/components/member/InputContent/InputContent";
import ModalOnBtn2 from "@/components/member/ModalOnBtn/ModalOnBtn2";
import InputBtn from "@/components/member/InputBtn/InputBtn";
import { authenticate } from "@/api/athenticate";
import { getCheckEmail } from "@/api/getCheckEmail";
import { Button } from "react-bootstrap";
import ChangePwModal from "@/components/member/ChangePwModal/ChangePwModal";

export default function change() {
	const [showModal, setShowModal] = useState(false);
	const [email, setEmail] = useState("");

	const handleClose = () => setShowModal(false);
	const handleShow = () => setShowModal(true);

	const onEmailHandler = (e: any) => {
		setEmail(e.target.value);
	}; // 이메일 작성

	return (
		<div className={styles.passwordContainer}>
			<span className={styles.passwordTitle}>비밀번호 찾기</span>
			<InputContent title="이메일" type="email" onChange={onEmailHandler} />
			<Button
				className={styles.linkButton}
				variant="primary"
				onClick={handleShow}
			>
				다음으로
			</Button>
			{/* 비밀번호 변경 화면으로 이동 */}
		</div>
	);
}
