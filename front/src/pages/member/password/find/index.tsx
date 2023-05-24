import { useState } from "react";
import router from "next/router";
import { Button } from "react-bootstrap";
import styles from "./styles.module.scss";
import InputContent from "@/components/member/InputContent/InputContent";
import { findPassword } from "@/api/password";

export default function change() {
	const [email, setEmail] = useState("");

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
				onClick={() => {
					findPassword(email)
						.then(() => {
							router.push("/member/password/change");
						})
						.catch((error) => {
							alert(error.data.message);
							location.reload();
						});
				}}
			>
				다음으로
			</Button>

			{/* 비밀번호 변경 화면으로 이동 */}
		</div>
	);
}
