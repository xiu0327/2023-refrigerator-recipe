import Link from "next/link";
import { Button } from "react-bootstrap";
import { EmojiSmile, HouseFill, Google } from "react-bootstrap-icons";
import styles from "./LoginBtn.module.scss";
import { useEffect, useRef } from "react";
import { useRouter } from "next/router";

export default function LoginBtn() {
	const LoginBtns = [
		{
			icon: <EmojiSmile />,
			title: "이메일로 로그인하기",
			link: "../member/email",
		},
		{
			icon: <Google />,
			title: "구글로 로그인하기",
			link: "http://localhost:8080/oauth2/authorization/google",
		},
		{
			icon: <HouseFill />,
			title: "네이버로 로그인하기",
			link: "http://localhost:8080/oauth2/authorization/naver",
		},
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
