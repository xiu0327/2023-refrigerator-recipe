import Link from "next/link";
import { Button } from "react-bootstrap";
import styles from "./LinkBtn.module.scss";

export default function LinkButton(props: { title: string; link: string }) {
	return (
		<Link legacyBehavior href={props.link}>
			<Button className={styles.linkButton} variant="primary" size="lg">
				<span>{props.title}</span>
			</Button>
		</Link>
	);
}
