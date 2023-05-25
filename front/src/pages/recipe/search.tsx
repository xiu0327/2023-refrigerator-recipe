import { useEffect, useState } from "react";
import { useRouter } from "next/router";

import { searchRecipe } from "@/api";
import { RecipeBrief } from "@/types";

import BackLayout from "@/components/layout/BackLayout";
import SearchBar from "@/components/global/SearchBar/SearchBar";
import SearchPanel from "@/components/recipe/SearchPanel/SearchPanel";
import RecipeList from "@/components/recipe/RecipeList/RecipeList";
import NoResult from "@/components/global/NoResult/NoResult";

import styles from "@/scss/pages.module.scss";
import { useIntersectionObserver } from "@/hooks";

export default function RecipeSearchPage() {
	const router = useRouter();
	const query = router.query.query;
	const [keyword, setKeyword] = useState<string>("");

	const [recipeResultData, setRecipeResultData] = useState<RecipeBrief[]>([]);
	const [page, setPage] = useState(0);
	const [isDataLoaded, setIsDataLoaded] = useState(false);
	const [isScrollEnd, setIsScrollEnd] = useState(false);

	useEffect(() => {
		query &&
			(async () => {
				setKeyword(query);
				setPage(0);
				const data = await searchRecipe(0, query);
				setRecipeResultData(data);
				setIsScrollEnd(false);
				setIsDataLoaded(true);
			})();
	}, [query]);

	useEffect(() => {
		if (page != 0 && !isScrollEnd) {
			(async () => {
				const data = await searchRecipe(page, query);
				data.length !== 0
					? setRecipeResultData((prev) => [...prev, ...data])
					: setIsScrollEnd(true);
			})();
		}
	}, [page]);

	useIntersectionObserver(setPage, isDataLoaded);

	const onSearchBtnClick = () => {
		router.replace(`search?query=${keyword}`);
	};

	return (
		<BackLayout>
			<div className={styles.fixed}>
				<SearchBar
					keyword={keyword}
					setKeyword={setKeyword}
					handleSubmit={onSearchBtnClick}
					handleSearchBtnClick={onSearchBtnClick}
					placeholder="궁금한 레시피를 검색해보세요!"
					focus
				/>
			</div>

			<div style={{ marginTop: "50px" }}>
				{!query ? (
					<SearchPanel />
				) : recipeResultData.length !== 0 ? (
					<>
						<RecipeList recipeData={recipeResultData} />
						{isDataLoaded && <div id="end-of-list" />}
					</>
				) : (
					<NoResult keyword={keyword} />
				)}
			</div>
		</BackLayout>
	);
}
