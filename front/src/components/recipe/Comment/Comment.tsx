import { RecipeComment } from "@/types";
import styles from "./Comment.module.scss";

type CommentProps = {
	comment: RecipeComment;
};

export default function Comment({ comment }: CommentProps) {
	const onModifyTextClick = () => {};
	const onDeleteTextClick = () => {};

	return (
		<div key={comment.commentID} className={styles.recipeComments}>
			<div className={styles.commentHeader}>
				<div className={styles.commentNickname}>마가렛 퀄리</div>
				{comment.isMyComment && (
					<div className={styles.commentMenu}>
						<div onClick={onModifyTextClick}>수정</div>
						<div onClick={onDeleteTextClick}>삭제</div>
					</div>
				)}
			</div>

			<div className={styles.commentContent}>{comment.content}</div>
			<div className={styles.commentExtraInfo}>
				<div>{comment.date}</div>
				{comment.modifiedState && <div>(수정됨)</div>}
				<div> </div>
				{/* {heartCommentIds.includes(comment.commentID) ? (
					<HeartFill
						className={styles.commentIcon}
						onClick={() => onUnlikeCommentBtnClick(comment.commentID)}
					/>
				) : (
					<Heart
						className={styles.commentIcon}
						onClick={() => onLikeCommentBtnClick(comment.commentID)}
					/>
				)} */}
				<div>{comment.heart}</div>
			</div>
		</div>
	);
}
