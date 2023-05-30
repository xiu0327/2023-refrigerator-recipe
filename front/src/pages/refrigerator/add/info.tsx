import { useState } from "react";
import { useRouter } from "next/router";
import moment from "moment";

import { addIngredient, getIngredientUnit } from "@/api";
import { calcDday } from "@/utils";
import { useFetchData, useValidateIngredient } from "@/hooks";

import BackBottomBtnLayout from "@/components/layout/BackBottomBtnLayout";
import FormLabel from "@/components/global/FormLabel/FormLabel";
import StorageTab from "@/components/refrigerator/StorageTab/StorageTab";
import DateInputForm from "@/components/refrigerator/IngredientInputForm/DateInputForm/DateInputForm";
import VolumeInputForm from "@/components/refrigerator/IngredientInputForm/VolumeInputForm";
import BottomBtn from "@/components/global/BottomBtn/BottomBtn";

import styles from "@/scss/pages.module.scss";

export default function AddIngredientInfoPage({ name }) {
	const router = useRouter();
	const [ingredient, setIngredient] = useState({
		name: name,
		storage: "냉장",
		expirationDate: moment().format("YYYY-MM-DD"),
		volume: "",
	});
	const unit = useFetchData(getIngredientUnit, [ingredient.name], []);

	const isValid = useValidateIngredient(ingredient, [ingredient]);

	const updateIngredient = (key, value) => {
		setIngredient((prev) => ({ ...prev, [key]: value }));
	};

	const onAddIngredientBtnClick = async () => {
		await addIngredient(ingredient);
		// TODO: 식재료 추가 완료 토스트메시지
		router.back();
	};

	return (
		<BackBottomBtnLayout>
			<div className={styles.container}>
				<div className="d-flex flex-grow-1 flex-column">
					<div className={styles.title_lg}>{ingredient.name}</div>
					<div className={styles.subtitle}>추가할 식재료에 대해 알려주세요</div>
				</div>

				<div className={styles.ingredientInfoContainer}>
					<FormLabel label="보관 방법">
						<StorageTab
							storage={ingredient.storage}
							setStorage={(value) => updateIngredient("storage", value)}
						/>
					</FormLabel>

					<FormLabel
						label="유통기한"
						subLabel={calcDday(ingredient.expirationDate)}
					>
						<DateInputForm
							date={ingredient.expirationDate}
							setDate={(value) => updateIngredient("expirationDate", value)}
						/>
					</FormLabel>

					<FormLabel label="용량">
						<VolumeInputForm
							volume={ingredient.volume}
							setVolume={(value: number) => updateIngredient("volume", value)}
							unit={unit}
						/>
					</FormLabel>
				</div>
			</div>

			<BottomBtn
				label="추가하기"
				onClick={onAddIngredientBtnClick}
				disabled={!isValid}
			/>
		</BackBottomBtnLayout>
	);
}

export async function getServerSideProps(context) {
	const name = context.query.ingredientName;

	return {
		props: {
			name,
		},
	};
}
