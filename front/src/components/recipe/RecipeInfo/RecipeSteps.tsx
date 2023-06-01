import { getRecipeSteps } from "@/api";
import styles from "./RecipeInfo.module.scss";
import { RecipeStep } from "@/types";
import { useEffect } from "react";
import { QuestionCircle } from "react-bootstrap-icons";
import Tooltip from "@/components/global/Tooltip/Tooltip";

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
	const tooltipMessageLines = [
		`레시피 과정을 상세히 볼 수 있어요!`,
		`모든 과정을 끝내면 사용한 식재료를 차감하고,`,
		`레시피에 대한 별점을 남길 수 있어요!`,
	];

	useEffect(() => {
		(async () => {
			const data = await getRecipeSteps(recipeID);
			setRecipeSteps(
				data.map((step: RecipeStep) => ({
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
				<Tooltip
					messageLines={tooltipMessageLines}
					style={{ top: "40px", left: "-185px", width: "300px" }}
				>
					<QuestionCircle className={styles.questionIcon} />
				</Tooltip>
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
