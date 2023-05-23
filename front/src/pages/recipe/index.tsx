import { useEffect, useState } from "react";
import router from "next/router";
import { getRecipeList } from "@/api";
import { RecipePreview } from "@/types/types";

import AppNavLayout from "@/components/layout/AppNavLayout";
import SearchBar from "@/components/global/SearchBar/SearchBar";
import FilterBar from "@/components/recipe/Bar/FilterBar";
import RecipeList from "@/components/recipe/RecipeList/RecipeList";

import styles from "@/scss/pages.module.scss";
import { login } from "@/api/login";

export default function RecipeListPage() {
	const [page, setPage] = useState(0);
	const [recipeData, setRecipeData] = useState<RecipePreview[]>([]);

	const [show, setShow] = useState(false);
	const [filterMenuList, setFilterMenuList] = useState([]);

	useEffect(() => {
		const fetchRecipeData = async () => {
			const data = await getRecipeList(page);
			setRecipeData((prev) => [...prev, ...data]);
		};
		fetchRecipeData();
	}, [page]);

	useEffect(() => {
		const observer = new IntersectionObserver(
			(entries) => {
				const firstEntry = entries[0];
				firstEntry.isIntersecting && setPage((prev) => prev + 1);
			},
			{ threshold: 1 },
		);
		observer.observe(document.querySelector("#end-of-list"));
		return () => observer.disconnect();
	}, []);

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

			<div style={{ marginTop: "90px", padding: "1rem" }}>
				<RecipeList recipeData={recipeData} />
			</div>
			<div id="end-of-list"></div>
		</AppNavLayout>
	);
}
