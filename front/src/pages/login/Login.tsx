import Link from "next/link";
import styles from "./styles.module.css";
import Button from "react-bootstrap/Button";
import { Google, Github, EmojiSmile } from "react-bootstrap-icons";

export default function Login() {
	return (
		<div className={styles.loginContainer}>
			<img src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/02627984-7272-438d-8692-9d1b52ced39e/%EB%83%89%EB%B6%80%ED%95%B41.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20230327%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20230327T061420Z&X-Amz-Expires=86400&X-Amz-Signature=3ae587a34dba7283049bfb5888818891bd39c31ff26ecc21b918c8fd9182e38f&X-Amz-SignedHeaders=host&response-content-disposition=filename%3D%22%25EB%2583%2589%25EB%25B6%2580%25ED%2595%25B41.jpg%22&x-id=GetObject" />
			<div className={styles.loginLink}>
				<span>
					아직 회원이 아니신가요?
					<Link legacyBehavior href="../register/Register">
						<a>회원가입</a>
					</Link>
				</span>
				<span>
					비밀번호를 잊으셨나요?
					<Link legacyBehavior href="../password/Password">
						<a>비밀번호 찾기</a>
					</Link>
				</span>
			</div>
			<div className={styles.loginBtnBars}>
				<span className={styles.loginBtnBar}></span>
				<span>로그인</span>
				<span className={styles.loginBtnBar}></span>
			</div>
			<div className={`d-grid gap-2`}>
				<Button className={styles.loginBtn} variant="secondary" size="lg">
					<EmojiSmile />
					<span>이메일로 로그인하기</span>
				</Button>
				<Button className={styles.loginBtn} variant="primary" size="lg">
					<Google />
					<span>구글로 로그인하기</span>
				</Button>
				<Button className={styles.loginBtn} variant="secondary" size="lg">
					<Github />
					<span>깃허브로 로그인하기</span>
				</Button>
			</div>
		</div>
	);
}
