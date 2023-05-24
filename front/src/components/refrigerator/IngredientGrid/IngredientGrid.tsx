import router from "next/router";
import { getDday } from "@/utils";
import { IngredientBrief } from "@/types";
import styles from "./IngredientGrid.module.scss";

type IngredientGridProps = {
	ingredientData: IngredientBrief[];
};

export default function IngredientGrid({
	ingredientData,
}: IngredientGridProps) {
	const onIngredientClick = (ingredientID: number) => {
		router.push(`/refrigerator/info?ingredientID=${ingredientID}`);
	};

	return (
		<div className={styles.ingredientGridContainer}>
			{ingredientData.map((ingredient: IngredientBrief) => (
				<div
					key={ingredient.ingredientID}
					className={styles.ingredientElementContainer}
					onClick={() => onIngredientClick(ingredient.ingredientID)}
				>
					<img
						src={ingredient.image}
						className={
							ingredient.remainDays.includes("+")
								? styles.ingredientIcon_expired
								: styles.ingredientIcon
						}
					/>
					<div className={styles.ingredientInfo}>
						<div className={styles.ingredientName}>{ingredient.name}</div>
						<div className={styles.ingredientDays}>
							{getDday(ingredient.remainDays)}
						</div>
					</div>
				</div>
			))}
		</div>
	);
}
