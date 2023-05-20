import { useRouter } from "next/router";
import { useEffect, useRef, useState } from "react";

import { getBookmarkIDs, getOwnedIngredientIDs, getRecipe } from "@/api";
import { useFetchData } from "@/hooks";

import RecipeInfoLayout from "@/components/layout/RecipeInfoLayout";
import RecipeDescription from "@/components/recipe/RecipeInfo/RecipeDescription";
import RecipeIngredients from "@/components/recipe/RecipeInfo/RecipeIngredients";
import RecipeSteps from "@/components/recipe/RecipeInfo/RecipeSteps";
import RecipeCommentsPreview from "@/components/recipe/RecipeInfo/RecipeCommentsPreview";

import styles from "@/scss/pages.module.scss";

export default function RecipeInfoPage() {
	const router = useRouter();
	const recipeID = Number(router.query.recipeID);

	const recipe = useFetchData(getRecipe, [recipeID], []);
	const [ownedIngredientIDs, setOwnedIngredientIDs] = useState([]);
	const [bookmarkIDs, setBookmarkIDs] = useState([]);

	useEffect(() => {
		(async () => {
			const bookmarkIDsData = await getBookmarkIDs();
			setBookmarkIDs(bookmarkIDsData);
			const ownedIngredientIDsData = await getOwnedIngredientIDs(recipeID);
			setOwnedIngredientIDs(ownedIngredientIDsData);
		})();
	}, []);

	return (
		<div>
			{recipe && (
				<RecipeInfoLayout
					recipeID={recipeID}
					bookmarkIDs={bookmarkIDs}
					setBookmarkIDs={setBookmarkIDs}
					recipeName={recipe.recipeName}
				>
					<img src={recipe.image} className={styles.backgroundImg} />
					<div className={styles.recipeInfoContainer}>
						<RecipeDescription recipe={recipe} />
						<RecipeIngredients
							servings={recipe.servings}
							ingredients={recipe.ingredients}
							ownedIngredientIDs={ownedIngredientIDs}
						/>
						<RecipeSteps recipeID={recipeID} />
						<RecipeCommentsPreview recipeID={recipeID} />
					</div>
				</RecipeInfoLayout>
			)}
		</div>
	);
}
