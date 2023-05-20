import Link from "next/link";
import { Button } from "react-bootstrap";
import styles from "./LinkBtn.module.scss";
import { login } from "@/api/login";

export default function LinkBtn({ title, link }) {
	return (
		<Link legacyBehavior href={link}>
			<Button className={styles.linkButton} variant="primary" size="lg">
				<span>{title}</span>
			</Button>
		</Link>
	);
}
