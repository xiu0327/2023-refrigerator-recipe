import Link from "next/link";
import { Button } from "react-bootstrap";
import { EmojiSmile, Github, Google } from "react-bootstrap-icons";
import styles from "./LoginBtn.module.scss";

export default function LoginBtn() {
	const LoginBtns = [
		{
			icon: <EmojiSmile />,
			title: "이메일로 로그인하기",
			link: "../login/loginEmail",
		},
		{ icon: <Google />, title: "구글로 로그인하기", link: "#" },
		{ icon: <Github />, title: "깃허브로 로그인하기", link: "#" },
	];
	return (
		<div className={`d-grid gap-2`}>
			{LoginBtns.map((items) => (
				<Link legacyBehavior href={items.link} key={items.title}>
					<Button className={styles.loginBtn} variant="primary" size="lg">
						{items.icon}
						<span>{items.title}</span>
					</Button>
				</Link>
			))}
		</div>
	);
}
