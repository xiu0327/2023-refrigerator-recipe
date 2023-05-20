import { RecipeDetail } from "@/types";
import Stars from "../Stars/Stars";
import styles from "./RecipeInfo.module.scss";
import { useEffect } from "react";

type RecipeDescriptionProps = {
	recipe: RecipeDetail;
};

export default function RecipeDescription({ recipe }: RecipeDescriptionProps) {
	const recipeExtraInfo = {
		난이도: recipe.difficulty,
		조리시간: recipe.cookingTime,
		칼로리: recipe.kcal !== "0kcal" && recipe.kcal,
		별점: recipe.scoreAvg,
	};

	// TODO: 별점 세로 가운데 정렬
	// HACK: 별점 NaN 에러 - Error: <rect> attribute width: Expected length, "NaN".

	return (
		<div className={styles.recipeInfoTopContainer}>
			<div className={styles.recipeNameInbun}>
				<div className={styles.recipeName}>{recipe.recipeName} </div>
				<div className={styles.recipeInbun}>{recipe.servings}</div>
			</div>

			<div className={styles.recipeDescription}>{recipe.description}</div>

			<div className={styles.recipeExtraInfo}>
				{Object.entries(recipeExtraInfo).map(([key, value]) => {
					if (value) {
						return (
							<div key={key} className="d-flex">
								<div className={styles.recipeInfoKey}>{key}</div>
								{key === "별점" && <Stars score={Number(value)} />}
								<div className={styles.recipeInfoValue}>
									{key === "별점" && value ? Number(value).toFixed(1) : value}
								</div>
							</div>
						);
					}
				})}
			</div>
		</div>
	);
}
