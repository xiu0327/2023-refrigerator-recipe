import { useRouter } from "next/router";
import { useEffect, useState } from "react";

import { getExpiringIngredients } from "@/api";

import BackLayout from "@/components/layout/BackLayout";
import IngredientGrid from "@/components/refrigerator/IngredientGrid/IngredientGrid";

export default function ExpiringIngredientListPage() {
	const router = useRouter();
	const day = Number(router.query.day);

	const [expiringIngredientData, setExpiringIngredientData] = useState([]);

	useEffect(() => {
		(async () => {
			const data = await getExpiringIngredients(day);
			setExpiringIngredientData(data);
		})();
	}, []);

	return (
		<BackLayout title={`소비기한 ${day}일 남은 식재료`}>
			<IngredientGrid ingredientData={expiringIngredientData} />
		</BackLayout>
	);
}
