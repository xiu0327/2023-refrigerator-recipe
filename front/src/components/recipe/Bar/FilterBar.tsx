import { FILTERS } from "./filters";
import styles from "./Bar.module.scss";

type FilterBarProps = {
	setFilterMenuList: Function;
};

export default function FilterBar({ setFilterMenuList }: FilterBarProps) {
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
