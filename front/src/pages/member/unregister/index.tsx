import InputContent from "@/components/member/InputContent/InputContent";
import styles from "./unregister.module.scss";
import { Button } from "react-bootstrap";
import { useState } from "react";
import UnregisterModal from "@/components/member/UnregisterModal/UnregisterModal";
import { login } from "@/api/login";

export default function Unregister() {
	const [password, setPassword] = useState();
	const [showModal, setShowModal] = useState(false);

	const handleClose = () => setShowModal(false);
	const handleShow = () => setShowModal(true);
	//login("gmail@gmail.com", "gmail123!!");

	const onPasswordHandler = (e: any) => {
		setPassword(e.target.value);
	};

	return (
		<div className={styles.loginEmailContainer}>
			<span className={styles.loginEmailTitle}>회원탈퇴</span>
			<InputContent
				title="비밀번호"
				type="password"
				onChange={onPasswordHandler}
			/>
			<Button
				className={styles.linkButton}
				variant="primary"
				onClick={handleShow}
			>
				다음으로
			</Button>
			{/* 비밀번호가 맞으면 모달 띄우기 */}
			<UnregisterModal
				show={showModal}
				handleClose={handleClose}
				password={password}
			/>
		</div>
	);
}
