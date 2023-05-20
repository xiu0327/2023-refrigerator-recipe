import { useEffect, useState } from "react";
import { CheckLg } from "react-bootstrap-icons";
import { RecipeIngredient } from "@/types";
import styles from "./RecipeInfo.module.scss";

type RecipeIngredientsProps = {
	servings: string;
	ingredients: RecipeIngredient[];
	ownedIngredientIDs: number[];
};

export default function RecipeIngredients({
	servings,
	ingredients,
	ownedIngredientIDs,
}: RecipeIngredientsProps) {
	const TYPE = ["주재료", "부재료", "양념"];
	const ingredientsByType = TYPE.reduce((acc, type) => {
		acc[type] = ingredients.filter((ingredient) => ingredient.type === type);
		return acc;
	}, []);

	return (
		<div className={styles.recipeInfoContainer}>
			<div className="d-flex">
				<div className={styles.recipeInfoTitle}>레시피 재료</div>
				<button className={styles.recipeInfoBtn} disabled>
					{servings}
				</button>
			</div>

			<div className="d-flex flex-column">
				{TYPE.map((type) => {
					if (ingredientsByType[type].length !== 0) {
						return (
							<div key={type} className={styles.recipeTypeIngredients}>
								<div className={styles.recipeIngredientType}>{type}</div>
								<div className="d-flex flex-grow-1 flex-column">
									{ingredientsByType[type].map((ingredient) => (
										<div
											key={ingredient.ingredientID}
											className={styles.recipeIngredientInfo}
										>
											<div>{ingredient.name}</div>
											{ownedIngredientIDs.includes(ingredient.ingredientID) && (
												<CheckLg className={styles.recipeIngredientCheckIcon} />
											)}
											<div className={styles.recipeIngredientVolume}>
												{ingredient.volume}
											</div>
										</div>
									))}
								</div>
							</div>
						);
					}
				})}
			</div>
		</div>
	);
}
