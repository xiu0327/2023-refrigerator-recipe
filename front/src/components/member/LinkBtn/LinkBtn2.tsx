import Link from "next/link";
import { Button } from "react-bootstrap";
import styles from "./LinkBtn.module.scss";
import { login } from "@/api/login";

export default function LinkBtn2({ title, link, email, password }) {
	return (
		<Link legacyBehavior href={link}>
			<Button
				className={styles.linkButton}
				variant="primary"
				size="lg"
				onClick={() => {
					login(email, password);
				}}
			>
				<span>{title}</span>
			</Button>
		</Link>
	);
}
