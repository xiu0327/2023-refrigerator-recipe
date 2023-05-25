import { useEffect, useState } from "react";
import { useRouter } from "next/router";

import { modifyIngredient } from "@/api";
import { calcDday } from "@/utils";

import BackBottomBtnLayout from "@/components/layout/BackBottomBtnLayout";
import FormLabel from "@/components/global/FormLabel/FormLabel";
import StorageTab from "@/components/refrigerator/StorageTab/StorageTab";
import DateInputForm from "@/components/refrigerator/IngredientInputForm/DateInputForm/DateInputForm";
import VolumeInputForm from "@/components/refrigerator/IngredientInputForm/VolumeInputForm";
import BottomBtn from "@/components/global/BottomBtn/BottomBtn";

import styles from "@/scss/pages.module.scss";
import { useValidateIngredient } from "@/hooks/useValidateIngredient";

export default function ModifyIngredientPage() {
	const router = useRouter();
	const { ingredientID, name, registrationDate, unit, ...ingredient } =
		router.query;
	const oldIngredient = { ...ingredient, volume: Number(ingredient.volume) };
	const [newIngredient, setNewIngredient] = useState(oldIngredient);

	const [isIngredientChanged, setIsIngredientChanged] = useState(false);
	const isValid = useValidateIngredient(newIngredient, [newIngredient]);

	useEffect(() => {
		const isSame = Object.keys(newIngredient).every(
			(key) => oldIngredient[key] === newIngredient[key],
		);
		setIsIngredientChanged(!isSame);
	}, [newIngredient]);

	const updateIngredient = (key, value) => {
		setNewIngredient((prev) => ({ ...prev, [key]: value }));
	};

	const onModifyIngredientClick = async () => {
		await modifyIngredient(ingredientID, newIngredient);
		router.back();
	};

	return (
		<BackBottomBtnLayout>
			{newIngredient && (
				<div className={styles.container}>
					<div className="d-flex">
						<div className="d-flex flex-grow-1 flex-column">
							<div className={styles.title_lg}>{name}</div>
							<div className={styles.subtitle}>{registrationDate} 등록</div>
						</div>
						<div className={styles.badge}>수정 중</div>
					</div>

					<div className={styles.ingredientInfoContainer}>
						<FormLabel label="보관 방법">
							<StorageTab
								storage={newIngredient.storage}
								setStorage={(value) => updateIngredient("storage", value)}
							/>
						</FormLabel>

						<FormLabel
							label="소비기한"
							subLabel={calcDday(newIngredient.expirationDate)}
						>
							<DateInputForm
								date={newIngredient.expirationDate}
								setDate={(value) => updateIngredient("expirationDate", value)}
							/>
						</FormLabel>

						<FormLabel label="용량">
							<VolumeInputForm
								volume={newIngredient.volume}
								setVolume={(value: number) => updateIngredient("volume", value)}
								unit={unit}
							/>
						</FormLabel>
					</div>
				</div>
			)}

			<BottomBtn
				label="수정하기"
				onClick={onModifyIngredientClick}
				disabled={!isIngredientChanged || !isValid}
			/>
		</BackBottomBtnLayout>
	);
}
