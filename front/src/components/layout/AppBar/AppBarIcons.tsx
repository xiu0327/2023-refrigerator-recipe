import router from "next/router";
import styles from "./AppBar.module.scss";
import { PlusCircle, ChevronLeft, Person } from "react-bootstrap-icons";
import Link from "next/link";

export const MyPageIcon = () => (
	<Person
		className={styles.appbarIcon}
		onClick={() => router.push("/mypage")}
	/>
);

export const AddIngredientIcon = () => (
	<Link href={`/refrigerator/add`}>
		<PlusCircle className={styles.appbarIcon} />
	</Link>
);

export const BackIcon = () => (
	<ChevronLeft className={styles.appbarIcon} onClick={() => router.back()} />
);
