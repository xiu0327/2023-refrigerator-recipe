import Modal from "@/components/global/Modal/Modal";
import styles from "./IngredientDeleteModal.module.scss";
import { deleteIngredient } from "@/api";
import router from "next/router";

type modalProps = {
	show: boolean;
	onHide: Function;
	ingredientID: number;
	ingredientName: string;
};

export default function IngredientDeleteModal({
	show,
	onHide,
	ingredientID,
	ingredientName,
}: modalProps) {
	const onDeleteIngredientClick = async () => {
		await deleteIngredient(ingredientID);
		router.back();
	};

	return (
		<Modal show={show} onHide={onHide}>
			<Modal.Header title="식재료 삭제" />

			<Modal.Body>
				<div className={styles.messageContainer}>
					<div className={styles.ingredientName}>{ingredientName}</div>
					해당 식재료를 삭제하시겠어요?
				</div>
			</Modal.Body>

			{/* 버튼 취소, 삭제 특징적인 거 뽑아서 컴포넌트화 modal+bottomsheet */}
			<Modal.Footer>
				<button onClick={onHide}>취소</button>
				<button onClick={onDeleteIngredientClick}>삭제</button>
			</Modal.Footer>
		</Modal>
	);
}
