import { useEffect, useState } from "react";
import styles from "./RecipeInfo.module.scss";
// import { getRecipeCommentPreview, getRecipeSteps } from "@/api";
import { RecipeComment } from "@/types/types";
import { login } from "@/api/login";
import { Heart, HeartFill, Link, ThreeDots } from "react-bootstrap-icons";
import router from "next/router";
import Comment from "../Comment/Comment";
import { getCommentsPreview } from "@/api";

export default function RecipeCommentsPreview({ recipeID, recipeName }) {
	const [commentData, setCommentData] = useState([]);
	const [commentNum, setCommentNum] = useState(0);

	useEffect(() => {
		(async () => {
			const data = await getCommentsPreview(recipeID);
			setCommentData(data.comments);
			setCommentNum(data.count);
		})();
	}, []);

	const onMoreCommentsBtnClick = () => {
		router.push(
			`/recipe/comment?recipeID=${recipeID}&recipeName=${recipeName}`,
		);
	};

	return (
		<div className={styles.recipeInfoContainer}>
			<div className={styles.recipeInfoHeader}>
				<div>댓글</div>
				<span>{commentNum}</span>
				<button onClick={onMoreCommentsBtnClick}>댓글 전체 보기</button>
			</div>

			<div className={styles.recipeCommentList}>
				{commentData?.map((comment) => (
					<div key={comment.commentID}>
						<Comment comment={comment} isLiked={false} preview />
					</div>
				))}
			</div>
		</div>
	);
}
