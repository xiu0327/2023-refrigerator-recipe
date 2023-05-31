import { useEffect, useState } from "react";
import styles from "./RecipeInfo.module.scss";
// import { getRecipeCommentPreview, getRecipeSteps } from "@/api";
import { RecipeComment } from "@/types/types";
import { login } from "@/api/login";
import { Heart, HeartFill, ThreeDots } from "react-bootstrap-icons";
import router from "next/router";
import Comment from "../Comment/Comment";
import { getCommentsPreview } from "@/api";
import Link from "next/link";

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

	return (
		<div className={styles.recipeInfoContainer}>
			<div className={styles.recipeInfoHeader}>
				<div>댓글</div>
				<span>{commentNum}</span>
				<Link
					href={`/recipe/comment?recipeID=${recipeID}&recipeName=${recipeName}`}
				>
					<button>댓글 전체 보기</button>
				</Link>
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
