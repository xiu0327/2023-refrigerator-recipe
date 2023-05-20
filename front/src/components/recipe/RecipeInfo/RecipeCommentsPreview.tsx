import { useEffect, useState } from "react";
import styles from "./RecipeInfo.module.scss";
import { getRecipeCommentPreview, getRecipeSteps } from "@/api";
import { RecipeComment } from "@/types/types";
import { login } from "@/api/login";
import { Heart, HeartFill, Link, ThreeDots } from "react-bootstrap-icons";
import router from "next/router";
import Comment from "../Comment/Comment";

export default function RecipeCommentsPreview({ recipeID }) {
	const [recipeComments, setRecipeComments] = useState<Array<RecipeComment>>(
		[],
	);
	const [recipeCommentNum, setRecipeCommentNum] = useState(0);

	useEffect(() => {
		getRecipeCommentPreview(
			recipeID,
			3,
			setRecipeComments,
			setRecipeCommentNum,
		);
	}, []);

	const onMoreCommentsBtnClick = () => {
		router.push(`/recipe/comment?recipeID=${recipeID}`);
	};
	const onCommentMenuBtnClick = () => {
		// BottomSheet
	};

	// TODO: RecipeComments Component 빼기
	return (
		<div className={styles.recipeInfoContainer}>
			<div className="d-flex align-items-end">
				<div className={styles.recipeInfoTitleNoFlex}>댓글</div>
				<div className={styles.recipeInfoSub}>{recipeCommentNum}</div>
				<button
					className={styles.recipeInfoBtn}
					onClick={onMoreCommentsBtnClick}
				>
					댓글 전체 보기
				</button>
			</div>

			<div className="d-flex flex-column">
				{recipeComments?.map((comment) => (
					<Comment comment={comment} />
				))}
			</div>
		</div>
	);
}
