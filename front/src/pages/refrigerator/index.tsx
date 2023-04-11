import { useState } from "react";
import router from "next/router";
import { Button } from "react-bootstrap";

import RefrigeratorLayout from "@/components/layout/RefrigeratorLayout";
import TabMenu from "@/components/refrigerator/StorageTab";
import Switch from "@/components/global/Switch";
import IngredientGrid from "@/components/refrigerator/IngredientGrid";

export default function RefrigeratorPage() {
	const [ingredientData, setIngredientData] = useState([
		// 임의값, 원래는 식재료 목록 조회 api 호출
		{ name: "딸기", remainDays: 5 },
		{ name: "사과", remainDays: 10 },
		{ name: "우유", remainDays: 11 },
		{ name: "치즈", remainDays: 12 },
		{ name: "청경채", remainDays: 13 },
	]);
	const [storage, setStorage] = useState<number>(0);

	return (
		<RefrigeratorLayout>
			<div className="p-2">
				<div className="d-flex gap-1" style={{ height: 42 }}>
					<TabMenu setStorage={setStorage} />
					<Button onClick={() => router.push("/refrigerator/search")}>🔍</Button>
				</div>

				{/* 위로 스크롤하면 사라졌다가 아래로 스크롤하면 나오게 구현하고싶음
						안되면 그냥 IngredientGrid 묶어서 Scroll에 넣기 */}
				<Switch label="소비기한 지난 식재료만 보기" />

				{/* 스크롤 부분 구현 해야됨 */}
				<div>
					<IngredientGrid ingredientData={ingredientData} />
				</div>
			</div>
		</RefrigeratorLayout>
	);
}
