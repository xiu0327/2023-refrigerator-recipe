import { useEffect, useState } from "react";
import styles from "./RecipeInfo.module.scss";
import { RecipeComment } from "@/types";
import { getCommentsPreview } from "@/api";
import Comment from "../Comment/Comment";
import Link from "next/link";

type RecipeCommentsPreviewProps = {
	recipeID: number;
	recipeName: string;
	commentID?: number;
};

export default function RecipeCommentsPreview({
	recipeID,
	recipeName,
	commentID,
}: RecipeCommentsPreviewProps) {
	const [commentData, setCommentData] = useState<RecipeComment[]>([]);
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
