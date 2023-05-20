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
			<div className="d-flex">
				<div className={styles.recipeInfoTitle}>레시피 과정</div>
				<button className={styles.recipeInfoBtn} onClick={onCookBtnClick}>
					요리하기
				</button>
			</div>

			<div className="d-flex flex-column">
				{recipeSteps &&
					recipeSteps.map((step) => (
						<div key={step.step} className={styles.recipeSteps}>
							<div className={styles.recipeStepNumber}>
								{step.step.padStart(2, "0")}
							</div>
							{/* ERROR: 레시피 과정 image url 안됨 */}
							{/* {step.image && <img src={step.image} />} */}
							<div className={styles.recipeExplanation}>
								{step.explanation.replace(/,+$/, "")}
							</div>
						</div>
					))}
			</div>
		</div>
	);
}
