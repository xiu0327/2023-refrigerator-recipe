import { useEffect, useState } from "react";
import router from "next/router";

import { getRecipes } from "@/api";
import { RecipeBrief } from "@/types";

import AppNavLayout from "@/components/layout/AppNavLayout";
import SearchBar from "@/components/global/SearchBar/SearchBar";
import FilterBar from "@/components/recipe/Bar/FilterBar";
import RecipeList from "@/components/recipe/RecipeList/RecipeList";

import styles from "@/scss/pages.module.scss";
import { useIntersectionObserver } from "@/hooks";

export default function RecipeListPage() {
	const [recipeData, setRecipeData] = useState<RecipeBrief[]>([]);
	const [page, setPage] = useState(0);
	const [isDataLoaded, setIsDataLoaded] = useState(false);
	const [isScrollEnd, setIsScrollEnd] = useState(false);

	const [show, setShow] = useState(false);
	const [filterMenuList, setFilterMenuList] = useState([]);

	useEffect(() => {
		(async () => {
			if (!isScrollEnd) {
				const data = await getRecipes(page);
				data.length !== 0
					? setRecipeData((prev) => [...prev, ...data])
					: setIsScrollEnd(true);
				setIsDataLoaded(true);
			}
		})();
	}, [page]);

	useIntersectionObserver(setPage, isDataLoaded);

	return (
		<AppNavLayout title="레시피">
			<div className={styles.fixed}>
				<SearchBar
					placeholder="궁금한 레시피를 검색해보세요!"
					handleClick={() => router.push("/recipe/search")}
					disabled
				/>
				<FilterBar setFilterMenuList={setFilterMenuList} />
			</div>

			<div style={{ marginTop: "90px" }}>
				<RecipeList recipeData={recipeData} />
				{isDataLoaded && <div id="end-of-list"></div>}
			</div>
		</AppNavLayout>
	);
}