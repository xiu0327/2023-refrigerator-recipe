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

export default function RecipeSearchPage() {
	const [keyword, setKeyword] = useState<string>("");

	const router = useRouter();
	const query = router.query.query;
	const [recipeResultData, setRecipeResultData] = useState<RecipeBrief[]>([]);

	useEffect(() => {
		const fetchData = async () => {
			const data = await searchRecipe(0, query);
			setRecipeResultData(data);
		};
		query && fetchData();
	}, [query]);

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

			<div style={{ marginTop: "50px", padding: "1rem" }}>
				{!query ? (
					<SearchPanel />
				) : recipeResultData.length !== 0 ? (
					<RecipeList recipeData={recipeResultData} />
				) : (
					<NoResult keyword={keyword} />
				)}
			</div>
		</BackLayout>
	);
}
