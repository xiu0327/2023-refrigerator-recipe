import styles from "./styles.module.scss";
import InputContent from "@/components/member/InputContent/InputContent";
import LinkButton from "@/components/member/LinkBtn/LinkBtn";

export default function LoginEmail() {
	const inputs = ["이메일", "비밀번호"];
	return (
		<div className={styles.loginEmailContainer}>
			<span className={styles.loginEmailTitle}>로그인</span>
			{inputs.map((items) => (
				<InputContent key={items} title={items} />
			))}
			<LinkButton title={"로그인"} link={"/"} />
		</div>
	);
}
