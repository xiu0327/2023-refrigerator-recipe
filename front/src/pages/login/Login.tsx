import Link from "next/link";
import styles from "./styles.module.css";
import Button from "react-bootstrap/Button";
import { Google, Github, EmojiSmile } from "react-bootstrap-icons";

export default function Login() {
	return (
		<div className={styles.loginContainer}>
			<img
				alt="냉장고를 부탁해"
				src="https://velog.velcdn.com/images/j-kyung99/post/64842983-2c78-4362-ba99-365d867017bd/image.jpg"
			/>
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
				<Link legacyBehavior href="../login/loginEmail">
					<Button className={styles.loginBtn} variant="secondary" size="lg">
						<EmojiSmile />
						<span>이메일로 로그인하기</span>
					</Button>
				</Link>
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
