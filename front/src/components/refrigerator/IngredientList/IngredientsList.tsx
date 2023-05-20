import { useEffect } from "react";
import { Check } from "react-bootstrap-icons";
import { getMatchedIngredients } from "@/api";
import { useFetchData } from "@/hooks";
import styles from "./IngredientList.module.scss";

type IngredientListProps = {
	keyword: string;
	selectedIngredient: string;
	setSelectedIngredient: Function;
};

export default function IngredientList({
	keyword,
	selectedIngredient,
	setSelectedIngredient,
}: IngredientListProps) {
	const matchedIngredients = useFetchData(
		getMatchedIngredients,
		[keyword],
		[keyword],
	);

	useEffect(() => {
		setSelectedIngredient("");
	}, [keyword]);

	const onIngredientClick = (ingredient: string) => {
		selectedIngredient == ingredient
			? setSelectedIngredient("")
			: setSelectedIngredient(ingredient);
	};

	return (
		<div>
			{matchedIngredients &&
				matchedIngredients.length !== 0 &&
				matchedIngredients.map((ingredient) => {
					return (
						<div
							key={ingredient}
							className={
								selectedIngredient === ingredient
									? styles.ingredientlist_selected
									: styles.ingredientlist
							}
							onClick={() => onIngredientClick(ingredient)}
						>
							<div className="flex-grow-1">{ingredient}</div>
							{selectedIngredient === ingredient && (
								<Check className={styles.ingredientlistCheck} />
							)}
						</div>
					);
				})}
		</div>
	);
}
