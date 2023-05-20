import { useEffect, useMemo, useState } from "react";
import { Search } from "react-bootstrap-icons";

import { getAllIngredients } from "@/api";
import { toPhoneme } from "@/utils";
import { useFetchData } from "@/hooks";

import BackLayout from "@/components/layout/BackLayout";
import SearchBar from "@/components/global/SearchBar/SearchBar";
import IngredientGrid from "@/components/refrigerator/IngredientGrid/IngredientGrid";
import NoResult from "@/components/global/NoResult/NoResult";

import styles from "@/scss/pages.module.scss";

export default function SearchIngredientPage() {
	const [keyword, setKeyword] = useState<string>("");
	const ingredientData = useFetchData(getAllIngredients, [], []);
	const [myIngredients, setMyIngredients] = useState([]);

	useEffect(() => {
		if (ingredientData) {
			const myIngredientsWithPhoneme = ingredientData.map((ingredient) => ({
				...ingredient,
				phoneme: toPhoneme(ingredient.name),
			}));
			setMyIngredients(myIngredientsWithPhoneme);
		}
	}, [ingredientData]);

	const filteredIngredients = useMemo(() => {
		const keywordPhoneme = toPhoneme(keyword);
		return myIngredients.filter((ingredient) =>
			ingredient.phoneme.includes(keywordPhoneme),
		);
	}, [myIngredients, keyword]);

	return (
		<BackLayout>
			<div className={styles.fixed}>
				<SearchBar
					keyword={keyword}
					setKeyword={setKeyword}
					placeholder="궁금한 식재료를 검색해보세요!"
					focus={false}
				/>
			</div>

			<div style={{ marginTop: "70px" }}>
				{keyword &&
					(filteredIngredients.length > 0 ? (
						<IngredientGrid ingredientData={filteredIngredients} />
					) : (
						<NoResult keyword={keyword} />
					))}
			</div>
		</BackLayout>
	);
}
