import { useEffect, useState } from "react";

import {
	getBookmarkIDs,
	getCommentsPreview,
	getOwnedIngredientIDs,
	getRecipe,
} from "@/api";
import { RecipeComment, RecipeDetail, RecipeStep } from "@/types";

import RecipeInfoLayout from "@/components/layout/RecipeInfoLayout";
import RecipeDescription from "@/components/recipe/RecipeInfo/RecipeDescription";
import RecipeIngredients from "@/components/recipe/RecipeInfo/RecipeIngredients";
import RecipeSteps from "@/components/recipe/RecipeInfo/RecipeSteps";
import RecipeCommentsPreview from "@/components/recipe/RecipeInfo/RecipeCommentsPreview";

import RecipeStepBottomSheet from "@/components/recipe/CookingBottomSheet/RecipeStepBottomSheet";
import IngredientDeductionBottomSheet from "@/components/recipe/CookingBottomSheet/IngredientDeductionBottomSheet";
import RatingBottomSheet from "@/components/recipe/CookingBottomSheet/RatingBottomSheet";

import styles from "@/scss/pages.module.scss";

type RecipeInfoPageProps = {
	recipeID: number;
};

export default function RecipeInfoPage({ recipeID }: RecipeInfoPageProps) {
	const [recipe, setRecipe] = useState<RecipeDetail | null>();
	const [recipeSteps, setRecipeSteps] = useState<RecipeStep[]>([]);
	const [bookmarkIDs, setBookmarkIDs] = useState([]);
	const [ownedIngredientIDs, setOwnedIngredientIDs] = useState([]);
	const [isOwnedDataLoaded, setIsOwnedDataLoaded] = useState(false);

	const [commentData, setCommentData] = useState<RecipeComment[]>([]);
	const [commentNum, setCommentNum] = useState(0);

	const [isRecipeStepBottomSheetShow, setIsRecipeStepBottomSheetShow] =
		useState(false);
	const [
		isIngredientDeductionBottomSheetShow,
		setIsIngredientDeductionBottomSheetShow,
	] = useState(false);
	const [isRatingBottomSheetShow, setIsRatingBottomSheetShow] = useState(false);

	useEffect(() => {
		(async () => {
			const recipeData = await getRecipe(recipeID);
			setRecipe(recipeData);
			const bookmarkIDsData = await getBookmarkIDs();
			setBookmarkIDs(bookmarkIDsData);
			const ownedIngredientIDsData = await getOwnedIngredientIDs(recipeID);
			setOwnedIngredientIDs(ownedIngredientIDsData);
			setIsOwnedDataLoaded(true);

			const data = await getCommentsPreview(recipeID);
			setCommentData(data.comments);
			setCommentNum(data.count);
		})();
	}, []);

	return (
		<>
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
						<RecipeSteps
							recipeID={recipeID}
							recipeSteps={recipeSteps}
							setRecipeSteps={setRecipeSteps}
							setIsRecipeStepBottomModalShow={setIsRecipeStepBottomSheetShow}
						/>
						<RecipeCommentsPreview
							recipeID={recipeID}
							recipeName={recipe.recipeName}
							commentData={commentData}
							commentNum={commentNum}
							setCommentData={setCommentData}
						/>
					</div>
				</RecipeInfoLayout>
			)}

			{recipe && recipeSteps.length !== 0 && (
				<RecipeStepBottomSheet
					show={isRecipeStepBottomSheetShow}
					onHide={() => setIsRecipeStepBottomSheetShow(false)}
					onNextShow={() => setIsIngredientDeductionBottomSheetShow(true)}
					recipeName={recipe.recipeName}
					recipeSteps={recipeSteps}
				/>
			)}

			{isOwnedDataLoaded && (
				<IngredientDeductionBottomSheet
					show={isIngredientDeductionBottomSheetShow}
					onHide={() => setIsIngredientDeductionBottomSheetShow(false)}
					onNextShow={() => setIsRatingBottomSheetShow(true)}
					recipeID={recipeID}
					ownedIngredientIDs={ownedIngredientIDs}
				/>
			)}

			<RatingBottomSheet
				show={isRatingBottomSheetShow}
				onHide={() => setIsRatingBottomSheetShow(false)}
				recipeID={recipeID}
			/>
		</>
	);
}

export async function getServerSideProps(context: any) {
	const recipeID = Number(context.query.recipeID);

	return {
		props: {
			recipeID,
		},
	};
}
