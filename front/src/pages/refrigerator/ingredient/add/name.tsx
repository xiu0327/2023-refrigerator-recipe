import router from "next/router";
import { useEffect, useState } from "react";
import { Button, Form } from "react-bootstrap";

import BottomBtn from "@/components/global/BottomBtn";
import SearchBar from "@/components/global/SearchBar";
import BackLayout from "@/components/layout/BackLayout";
import IngredientList from "@/components/refrigerator/IngredientsList";
import RequestIngredientBtn from "@/components/refrigerator/RequestIngredientBtn";

export default function AddIngredientNamePage() {
	const [keyword, setKeyword] = useState<String>("");
	const [selectedIngredient, selectIngredient] = useState<Number>(-1);
	const [matchedIngredients, setMatchedIngredients] = useState<Array<String>>([]);

	useEffect(() => {
		selectIngredient(-1);
		// 유아이 작동 확인 위한 임의 코드
		// 원래는 api 호출해서 자동완성 값 받아내야됨
		// 입력이 몇 초 이상 멈추면 그때 api를 호출하는 방법도 생각해봤는데 배민은 안그럼
		// 이거 나중에 뺄 수 있나? ...
		keyword
			? setMatchedIngredients(["사골", "사과", "사탕수수", "살구", "삼겹살"])
			: setMatchedIngredients([]);
	}, [keyword]);

	return (
		<BackLayout>
			<div className="d-flex flex-column flex-fill">
				<div className="p-4 pt-5 d-flex flex-column flex-fill gap-4">
					<h3>추가할 식재료를 선택해주세요</h3>
					<SearchBar placeholder="추가할 식재료를 입력해주세요" setKeyword={setKeyword} />
					<IngredientList
						ingredientData={matchedIngredients}
						selectIngredient={selectIngredient}
						selectedIngredient={selectedIngredient}
					/>
					{keyword && !matchedIngredients ? <div>검색 결과가 없습니다</div> : <div />}
					{keyword ? <RequestIngredientBtn keyword={keyword} /> : <div />}
				</div>

				<BottomBtn
					label="추가하기"
					onClick={() => router.replace("./info")}
					disabled={selectedIngredient == -1}
				/>
			</div>
		</BackLayout>
	);
}
