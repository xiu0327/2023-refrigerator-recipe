import BackLayout from "@/components/layout/BackLayout";
import SearchPanel from "@/components/recipe/SearchPanel/SearchPanel";
import Input from "@/components/refrigerator/IngredientInputForm/Input";
import router, { useRouter } from "next/router";
import { useEffect, useState } from "react";
import styles from "@/scss/pages.module.scss";
import { Search } from "react-bootstrap-icons";
import { getRecipeList, searchRecipe } from "@/api";
import { RecipePreview } from "@/types/types";
import { login } from "@/api/login";
import RecipeList from "@/components/recipe/RecipeList/RecipeList";
import instance from "@/api/interceptors";

export default function RecipeSearchPage() {
	const [keyword, setKeyword] = useState<string>("");

	const router = useRouter();
	const { query } = router.query;
	const searchData = { searchWord: query };
	const [recipeResultData, setRecipeResultData] = useState<RecipePreview[]>([]);

	// login();

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
			<div className={styles.refrigeratorBar}>
				<Input
					placeholder="궁금한 레시피를 검색해보세요!"
					data={keyword}
					setData={setKeyword}
					focus
				/>
				<Search
					height="20"
					width="20"
					color="#616161"
					onClick={onSearchBtnClick}
				/>
			</div>
			{query && recipeResultData.length !== 0 ? (
				<RecipeList recipeData={recipeResultData} />
			) : (
				<SearchPanel />
			)}
		</BackLayout>
	);
}
