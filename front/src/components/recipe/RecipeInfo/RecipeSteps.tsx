import { getRecipeSteps } from "@/api";
import styles from "./RecipeInfo.module.scss";
import { RecipeStep } from "@/types";
import { useEffect } from "react";

type RecipeInfoProps = {
	recipeID: number;
	recipeSteps: RecipeStep[];
	setRecipeSteps: Function;
	setIsRecipeStepBottomModalShow: Function;
};

export default function RecipeInfo({
	recipeID,
	recipeSteps,
	setRecipeSteps,
	setIsRecipeStepBottomModalShow,
}: RecipeInfoProps) {
	useEffect(() => {
		(async () => {
			const data = await getRecipeSteps(recipeID);
			setRecipeSteps(
				data.map((step) => ({
					...step,
					explanation: step.explanation.replace(/,+$/, ""),
				})),
			);
		})();
	}, []);

	const onCookingBtnClick = () => {
		setIsRecipeStepBottomModalShow(true);
	};

	return (
		<div className={styles.recipeInfoContainer}>
			<div className={styles.recipeInfoHeader}>
				<div>레시피 과정</div>
				<span />
				<button onClick={onCookingBtnClick}>요리하기</button>
			</div>

			<div className={styles.recipeStepTable}>
				{recipeSteps?.map((step) => (
					<div key={step.step}>
						<span className={styles.recipeStepNumber}>
							{step.step.padStart(2, "0")}
						</span>
						<span className={styles.recipeStep}>{step.explanation}</span>
					</div>
				))}
			</div>
		</div>
	);
}
