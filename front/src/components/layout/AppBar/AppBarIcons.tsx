import router from "next/router";
import styles from "./AppBar.module.scss";
import { List, PlusCircle, ChevronLeft, Bell } from "react-bootstrap-icons";

// TODO: 프로젝트 합친 후 router 설정

export const MyPageIcon = () => (
	<List className={styles.appbarIcon} /*onClick={router.push("/mypage")}*/ />
);

export const NotificationIcon = () => (
	<Bell
		className={styles.appbarIcon} /*onClick={router.push("/notification")}*/
	/>
);

export const AddIngredientIcon = () => (
	<PlusCircle
		className={styles.appbarIcon}
		onClick={() => router.push("/refrigerator/add")}
	/>
);

export const BackIcon = () => (
	<ChevronLeft className={styles.appbarIcon} onClick={() => router.back()} />
);
