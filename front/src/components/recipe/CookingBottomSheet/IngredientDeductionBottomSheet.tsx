import BottomSheet from "@/components/global/BottomSheet/BottomSheet";
import styles from "./CookingBottomSheet.module.scss";
import { useEffect, useState } from "react";
import { deductIngredient, getRecipeIngredients } from "@/api";
import { CheckCircle, CheckCircleFill } from "react-bootstrap-icons";
import { RecipeCalculatedIngredient } from "@/types";

type IngredientDeductionBottomSheetProps = {
	show: boolean;
	onHide: Function;
	onNextShow: Function;
	recipeID: number;
	ownedIngredientIDs: number[];
};

export default function IngredientDeductionBottomSheet({
	show,
	onHide,
	onNextShow,
	recipeID,
	ownedIngredientIDs,
}: IngredientDeductionBottomSheetProps) {
	const [ingredients, setIngredients] = useState<RecipeCalculatedIngredient[]>(
		[],
	);
	const [isDeductionAvailable, setIsDeductionAvailable] = useState<number[]>(
		[],
	);
	const [isDeductionSelected, setIsDeductionSelected] = useState<number[]>([]);

	useEffect(() => {
		(async () => {
			const data = await getRecipeIngredients(recipeID);
			setIngredients(data);

			const filteredData = data
				.filter((ingredient: RecipeCalculatedIngredient) =>
					ownedIngredientIDs.includes(ingredient.recipeIngredientId),
				)
				.map(
					(ingredient: RecipeCalculatedIngredient) =>
						ingredient.recipeIngredientId,
				);
			setIsDeductionAvailable(filteredData);
			setIsDeductionSelected(filteredData);
		})();
	}, []);

	const onNextBtnClick = () => {
		const selectedIngredients = ingredients
			.filter((ingredient: RecipeCalculatedIngredient) =>
				isDeductionSelected.includes(ingredient.recipeIngredientId),
			)
			.map(({ name, volume, unit }) => ({ name, volume, unit }));
		deductIngredient(selectedIngredients);
		onNextShow();
		onHide();
	};

	const onCancelBtnClick = () => {
		onNextShow();
		onHide();
	};

	const onIngredientClick = (recipeIngredientId: number) => {
		if (isDeductionAvailable.includes(recipeIngredientId)) {
			isDeductionSelected.includes(recipeIngredientId)
				? setIsDeductionSelected((prev) =>
						prev.filter((id) => id !== recipeIngredientId),
				  )
				: setIsDeductionSelected((prev) => [...prev, recipeIngredientId]);
		}
	};

	return (
		<BottomSheet show={show} onHide={onHide} style={{ height: "100%" }}>
			<BottomSheet.Header title="식재료 차감하기" onHide={() => onHide()} />

			<BottomSheet.Body>
				<div className={styles.container}>
					<div className={styles.descriptionContainer}>
						<span>차감하지 않을 식재료가 있나요?</span>
						선택된 식재료는 자동으로 용량이 차감됩니다 <br />
						차감하지 않을 식재료를 선택 취소해주세요
					</div>

					<div className={styles.ingredientList}>
						{ingredients.map(({ recipeIngredientId, ...ingredient }) => {
							if (ingredient.volume && ingredient.unit) {
								return (
									<div
										key={ingredient.name}
										className={
											isDeductionSelected.includes(recipeIngredientId)
												? styles.selected
												: undefined
										}
										onClick={() => onIngredientClick(recipeIngredientId)}
									>
										{isDeductionSelected.includes(recipeIngredientId) ? (
											<CheckCircleFill className={styles.icon} />
										) : (
											isDeductionAvailable.includes(recipeIngredientId) && (
												<CheckCircle />
											)
										)}

										<span>{ingredient.name}</span>
										{`${ingredient.volume} ${ingredient.unit}`}
									</div>
								);
							}
						})}
					</div>
				</div>
			</BottomSheet.Body>

			<BottomSheet.Footer>
				<button onClick={onCancelBtnClick}>취소</button>
				<button onClick={onNextBtnClick}>확인</button>
			</BottomSheet.Footer>
		</BottomSheet>
	);
}
