import { useEffect, useState } from "react";

import SearchBar from "@/components/global/SearchBar";
import BackLayout from "@/components/layout/BackLayout";
import IngredientGrid from "@/components/refrigerator/IngredientGrid";

export default function SearchIngredientPage() {
	// 화면 넘어오자마자 searchbar에 focus 됐으면

	const [keyword, setKeyword] = useState<String>("");
	const [ingredientData, setIngredientData] = useState([]); // ingredientType은 따로 빼서 써야될 듯
	// 키워드 바뀔 때마다 자동완성 식재료 목록 반환하는 api로 값 받음
	// IngredientGrid에 위 값 넘겨서 재렌더링

	// useEffect(() => {
	// 	// 키워드에 따른 자동완성 식재료 목록 반환하는 API나 util 호출
	// }, [keyword]);

	return (
		<BackLayout>
			<div className="p-2">
				<SearchBar
					placeholder="궁금한 식재료를 입력해주세요"
					setKeyword={setKeyword}
				/>
				<IngredientGrid ingredientData={ingredientData} />
			</div>
		</BackLayout>
	);
}
