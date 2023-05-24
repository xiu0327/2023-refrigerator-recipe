import styles from "./NoResult.module.scss";

export default function NoResult({ keyword }) {
	return (
		<div className={styles.noResultContainer}>
			<div className={styles.noResultKeyword}>{keyword}</div>
			<div>에 관한 검색 결과가 없어요</div>
		</div>
	);
}
