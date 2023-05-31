import { useEffect, useState } from "react";
import { getRecommendedRecipes } from "@/api";

import { MatchedRecipe } from "@/types";

import BackLayout from "@/components/layout/BackLayout";
import RecipeGalleryWithMatch from "@/components/recipe/RecipeGallery/RecipeGalleryWithMatch";

export default function RecommendedRecipeListPage() {
	const [recommendedRecipeData, setRecommendedRecipeData] = useState<
		MatchedRecipe[]
	>([]);

	useEffect(() => {
		(async () => {
			const data = await getRecommendedRecipes();
			setRecommendedRecipeData(data);
		})();
	}, []);

	return (
		<BackLayout title="추천 레시피">
			<RecipeGalleryWithMatch recipeData={recommendedRecipeData} />
		</BackLayout>
	);
}
