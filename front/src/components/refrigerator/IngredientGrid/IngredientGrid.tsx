import { getDday } from "@/utils";
import { IngredientBrief } from "@/types";
import styles from "./IngredientGrid.module.scss";
import Link from "next/link";

type IngredientGridProps = {
	ingredientData: IngredientBrief[];
};

export default function IngredientGrid({
	ingredientData,
}: IngredientGridProps) {
	return (
		<div className={styles.ingredientGridContainer}>
			{ingredientData.map((ingredient: IngredientBrief) => (
				<div
					key={ingredient.ingredientID}
					className={styles.ingredientElementContainer}
				>
					<Link
						href={`/refrigerator/info?ingredientID=${ingredient.ingredientID}`}
						style={{ textDecoration: "none" }}
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
							<span className={styles.ingredientName}>{ingredient.name}</span>
							<span className={styles.ingredientDays}>
								{getDday(ingredient.remainDays)}
							</span>
						</div>
					</Link>
				</div>
			))}
		</div>
	);
}
