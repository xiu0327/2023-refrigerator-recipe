import { useEffect, useState } from "react";
import { useRouter } from "next/router";

import { searchRecipe } from "@/api";
import { useIntersectionObserver } from "@/hooks";
import { RecipeBrief } from "@/types";

import BackLayout from "@/components/layout/BackLayout";
import SearchBar from "@/components/global/SearchBar/SearchBar";
import SearchPanel from "@/components/recipe/SearchPanel/SearchPanel";
import SearchSuggestions from "@/components/recipe/SearchSuggestions/SearchSuggestions";
import RecipeList from "@/components/recipe/RecipeList/RecipeList";
import NoResult from "@/components/global/NoResult/NoResult";

import styles from "@/scss/pages.module.scss";

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
				const data = await searchRecipe(0, `&searchWord=${query}`);
				setRecipeResultData(data);
				setIsScrollEnd(false);
				setIsDataLoaded(true);
			})();
	}, [query]);

	useEffect(() => {
		if (page != 0 && !isScrollEnd) {
			(async () => {
				const data = await searchRecipe(page, `&searchWord=${query}`);
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
				{!keyword && !query && <SearchPanel />}
				{keyword && keyword !== query && (
					<SearchSuggestions keyword={keyword} />
				)}
				{keyword && keyword === query && recipeResultData.length !== 0 && (
					<>
						<RecipeList recipeData={recipeResultData} />
						{isDataLoaded && <div id="end-of-list" />}
					</>
				)}
				{keyword && keyword === query && recipeResultData.length === 0 && (
					<NoResult keyword={query} />
				)}
			</div>
		</BackLayout>
	);
}
