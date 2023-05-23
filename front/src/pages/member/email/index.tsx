import styles from "./styles.module.scss";
import InputContent from "@/components/member/InputContent/InputContent";
import LinkBtn2 from "@/components/member/LinkBtn/LinkBtn2";
// import LinkBtn2 from "@/components/member/LinkBtn/LinkBtn"
import { useState } from "react";

export default function LoginEmail() {
	const [email, setEmail] = useState("");
	const [password, setPassword] = useState("");
	const onEmailHandler = (e) => {
		setEmail(e.target.value);
	};

	const onPasswordHandler = (e) => {
		setPassword(e.target.value);
	};
	return (
		<div className={styles.loginEmailContainer}>
			<span className={styles.loginEmailTitle}>로그인</span>
			<InputContent title="이메일" onChange={onEmailHandler} />
			<InputContent
				type="password"
				title="비밀번호"
				onChange={onPasswordHandler}
			/>
			<LinkBtn2
				title={"로그인"}
				link={"/refrigerator"}
				email={email}
				password={password}
			/>
			{/* 링크에 회원 냉장고로 이동하게 만들기 */}
		</div>
	);
}
