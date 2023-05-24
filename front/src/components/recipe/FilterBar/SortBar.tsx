import styles from "./FilterGroup.module.scss";

export default function SortBar({ sortType, setSortType }) {
	const onSortBtnClick = () => {
		setSortType(sortType === "좋아요순" ? "최신순" : "좋아요순");
	};

	return (
		<div className={styles.sortbarContainer}>
			<button className={styles.filterBtn} onClick={onSortBtnClick}>
				{sortType}
			</button>
		</div>
	);
}
