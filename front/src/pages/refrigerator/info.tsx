import { useRouter } from "next/router";
import { PencilSquare } from "react-bootstrap-icons";

import { getIngredientInfo, deleteIngredient } from "@/api";
import { getDday } from "@/utils";
import { useFetchData } from "@/hooks";
import { IngredientDetail } from "@/types";

import BackBottomBtnLayout from "@/components/layout/BackBottomBtnLayout";
import FormLabel from "@/components/global/FormLabel/FormLabel";
import Input from "@/components/global/Input/Input";
import VolumeInputForm from "@/components/refrigerator/IngredientInputForm/VolumeInputForm";
import BottomBtn from "@/components/global/BottomBtn/BottomBtn";

import styles from "@/scss/pages.module.scss";

export default function IngredientInfoPage() {
	const router = useRouter();
	const ingredientID = Number(router.query.ingredientID);
	const ingredient: IngredientDetail | null = useFetchData(
		getIngredientInfo,
		[ingredientID],
		[],
	);

	const onEditBtnClick = () => {
		const { remainDays, image, ...params } = ingredient;

		router.push({
			pathname: `/refrigerator/modify`,
			query: params,
		});
	};

	const onDeleteIngredientClick = () => {
		// TODO: 삭제 확인 모달 띄우기
		deleteIngredient(ingredientID);
		router.back();
	};

	return (
		<BackBottomBtnLayout>
			{ingredient && (
				<div className={styles.container}>
					<div className="d-flex">
						<div className="d-flex flex-grow-1 flex-column">
							<div className={styles.title_lg}>{ingredient.name}</div>
							<div className={styles.subtitle}>
								{ingredient.registrationDate} 등록
							</div>
						</div>
						<PencilSquare className={styles.icon} onClick={onEditBtnClick} />
					</div>

					<div className={styles.ingredientInfoContainer}>
						<FormLabel label="보관 방법">
							<Input value={ingredient.storage} disabled />
						</FormLabel>

						<FormLabel
							label="소비기한"
							subLabel={getDday(ingredient.remainDays)}
						>
							<Input value={ingredient.expirationDate} disabled />
						</FormLabel>

						<FormLabel label="용량">
							<VolumeInputForm
								volume={ingredient.volume}
								unit={ingredient.unit}
								disabled
							/>
						</FormLabel>
					</div>
				</div>
			)}

			<BottomBtn label="삭제하기" onClick={onDeleteIngredientClick} />
		</BackBottomBtnLayout>
	);
}
