import SearchTagGroup from "./SearchTagGroup";
import styles from "./SearchPanel.module.scss";
import { getLastSearches, getRecommendationSearches } from "@/api";
import { useEffect, useState } from "react";
import { login } from "@/api/login";

export default function SearchPanel() {
	// NOTE: 변수에 저장하면 오류가 나서 useState를 쓰는데 이유를 모르겠음
	const [lastSearches, setLastSearches] = useState([]);
	const [recommendationSearches, setRecommendationSearches] = useState([]);

	// TEMP: html 오류 반환 오류 뜨면 login 실행 후 다시 호출
	// login();

	useEffect(() => {
		getLastSearches(setLastSearches);
		getRecommendationSearches(setRecommendationSearches);
	}, []);

	return (
		<div className={styles.panelContainer}>
			{lastSearches && lastSearches.length !== 0 && (
				<div className={styles.partContainer}>
					<div className={styles.title}>최근 검색어</div>
					<SearchTagGroup data={lastSearches} closeBtn />
				</div>
			)}

			{recommendationSearches && recommendationSearches.length !== 0 && (
				<div className={styles.partContainer}>
					<div>
						<div className={styles.title}>추천 검색어</div>
						<div className={styles.description}>
							소비기한이 얼마남지 않은 식재료에요!
						</div>
					</div>
					<SearchTagGroup data={recommendationSearches} />
				</div>
			)}
		</div>
	);
}
