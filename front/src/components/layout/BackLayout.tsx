import AppBar from "./AppBar/AppBar";
import styles from "./Layout.module.scss";
import { BackIcon } from "./AppBar/AppBarIcons";

type layoutProps = {
	title?: string;
	children: React.ReactNode;
};

export default function BackLayout({ title, children }: layoutProps) {
	return (
		<div className={styles.layoutContainer}>
			<AppBar title={title}>
				<BackIcon />
			</AppBar>
			<div className={styles.layoutContentWithApp}>{children}</div>
		</div>
	);
}
