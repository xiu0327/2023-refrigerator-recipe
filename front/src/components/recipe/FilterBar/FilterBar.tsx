import styles from "./FilterBar.module.scss";
import { FILTERS } from "./filters";

export default function FilterBar({ setFilterMenuList }) {
	const onFilterBtnClick = async (fetchFilterMenuList) => {
		const data = await fetchFilterMenuList();
		setFilterMenuList(["전체", ...data]);
	};

	return (
		<div className={styles.filterbarContainer}>
			{FILTERS.map((filter) => (
				<button
					key={filter.name}
					className={styles.filterBtn}
					onClick={() => onFilterBtnClick(filter.fetchFilterMenuList)}
				>
					{filter.name}
				</button>
			))}
		</div>
	);
}
