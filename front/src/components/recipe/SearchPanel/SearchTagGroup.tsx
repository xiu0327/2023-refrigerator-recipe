import router from "next/router";
import styles from "./SearchPanel.module.scss";
import { removeLateSearch } from "@/api";

type Props = {
	data: Array<string>;
	closeBtn?: boolean;
};

export default function SearchTagGroup({ data, closeBtn }: Props) {
	const onSearchTagClick = (tag: string) => {
		router.replace(`/recipe/search?query=${tag}`);
	};

	return (
		<div className={styles.tagsContainer}>
			{data.map((tag) => (
				<button key={tag} className={styles.tag}>
					<div onClick={() => onSearchTagClick(tag)}>{tag}</div>
					{closeBtn && (
						<div
							onClick={() => removeLateSearch(tag)}
							className={styles.closeBtn}
						>
							×
						</div>
					)}
				</button>
			))}
		</div>
	);
}
