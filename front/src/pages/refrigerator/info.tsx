import router, { useRouter } from "next/router";
import { Trash3 } from "react-bootstrap-icons";

import { getIngredientInfo, deleteIngredient } from "@/api";
import { getDday } from "@/utils";
import { IngredientDetail } from "@/types";

import BackBottomBtnLayout from "@/components/layout/BackBottomBtnLayout";
import FormLabel from "@/components/global/FormLabel/FormLabel";
import Input from "@/components/global/Input/Input";
import VolumeInputForm from "@/components/refrigerator/IngredientInputForm/VolumeInputForm";
import BottomBtn from "@/components/global/BottomBtn/BottomBtn";

import styles from "@/scss/pages.module.scss";
import Link from "next/link";
import { useEffect, useState } from "react";

type IngredientInfoPageProps = {
	ingredientID: number;
};

export default function IngredientInfoPage({
	ingredientID,
}: IngredientInfoPageProps) {
	const [ingredient, setIngredient] = useState<IngredientDetail | undefined>(
		undefined,
	);

	useEffect(() => {
		(async () => {
			const data = await getIngredientInfo(ingredientID);
			setIngredient(data);
		})();
	}, []);

	const onDeleteIngredientClick = async () => {
		// TODO: 삭제 확인 모달 띄우기
		await deleteIngredient(ingredientID);
		router.back();
	};

	return (
		<BackBottomBtnLayout>
			{ingredient && (
				<>
					<div className={styles.container}>
						<div className="d-flex">
							<div className="d-flex flex-grow-1 flex-column">
								<div className={styles.title_lg}>{ingredient.name}</div>
								<div className={styles.subtitle}>
									{ingredient.registrationDate} 등록
								</div>
							</div>
							<Trash3
								className={styles.icon}
								onClick={onDeleteIngredientClick}
							/>
						</div>

						<div className={styles.ingredientInfoContainer}>
							<FormLabel label="보관 방법">
								<Input value={ingredient.storage} disabled />
							</FormLabel>

							<FormLabel
								label="유통기한"
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

					<Link
						href={`/refrigerator/modify?ingredientID=${ingredient.ingredientID}&name=${ingredient.name}&storage=${ingredient.storage}&expirationDate=${ingredient.expirationDate}&registrationDate=${ingredient.registrationDate}&volume=${ingredient.volume}&unit=${ingredient.unit}`}
					>
						<BottomBtn label="수정하기" />
					</Link>
				</>
			)}
		</BackBottomBtnLayout>
	);
}

export async function getServerSideProps(context: any) {
	const ingredientID = Number(context.query.ingredientID);

	return {
		props: {
			ingredientID,
		},
	};
}
