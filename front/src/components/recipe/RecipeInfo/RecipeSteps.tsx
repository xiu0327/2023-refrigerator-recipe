import { getRecipeSteps } from "@/api";
import { useFetchData } from "@/hooks";
import styles from "./RecipeInfo.module.scss";

export default function RecipeInfo({ recipeID }: { recipeID: number }) {
	const recipeSteps = useFetchData(getRecipeSteps, [recipeID], []);

	const onCookBtnClick = () => {
		// TODO: 요리하기 모달 띄우기
	};

	return (
		<div className={styles.recipeInfoContainer}>
			<div className={styles.recipeInfoHeader}>
				<div>레시피 과정</div>
				<span />
				<button onClick={onCookBtnClick}>요리하기</button>
			</div>

			<div className={styles.recipeStepTable}>
				{recipeSteps?.map((step) => (
					<div key={step.step}>
						<span className={styles.recipeStepNumber}>
							{step.step.padStart(2, "0")}
						</span>
						<span className={styles.recipeStep}>
							{step.explanation.replace(/,+$/, "")}
						</span>
					</div>
				))}
			</div>
		</div>
	);
}
