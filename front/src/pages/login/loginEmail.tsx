import Link from "next/link";
import { Button, Form, InputGroup } from "react-bootstrap";
import styles from "./styles.module.css";

export default function LoginEmail() {
	return (
		<div className={styles.loginEmailContainer}>
			<span className={styles.loginEmailTitle}>로그인</span>
			<InputGroup className={`${styles.loginEmailInput} mb-3`}>
				<Form.Control
					placeholder="이메일"
					aria-label="Recipient's username"
					aria-describedby="basic-addon2"
				/>
			</InputGroup>
			<InputGroup className={`${styles.loginEmailInput} mb-3`}>
				<Form.Control
					placeholder="비밀번호"
					aria-label="Recipient's username"
					aria-describedby="basic-addon2"
				/>
			</InputGroup>
			<Link legacyBehavior href="/">
				<Button className={styles.loginEmailBtn} variant="primary" size="lg">
					<span>이메일로 로그인하기</span>
				</Button>
			</Link>
		</div>
	);
}
