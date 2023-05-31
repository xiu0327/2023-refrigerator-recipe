import Link from "next/link";
import { Button } from "react-bootstrap";
import styles from "./LinkBtn.module.scss";

interface LinkBtnProps {
	title: string;
	link: string;
}

export default function LinkBtn({ title, link }: LinkBtnProps) {
	return (
		<Link legacyBehavior href={link}>
			<Button className={styles.linkButton} variant="primary" size="lg">
				<span>{title}</span>
			</Button>
		</Link>
	);
}
