import { RecipeComment } from "@/types";
import styles from "./Comment.module.scss";
import { Heart, HeartFill } from "react-bootstrap-icons";

type CommentProps = {
	comment: RecipeComment;
	isLiked: boolean;
	preview?: boolean;
};

export default function Comment({ comment, isLiked, preview }: CommentProps) {
	const onModifyTextClick = () => {};
	const onDeleteTextClick = () => {};

	const onUnlikeCommentClick = () => {};
	const onLikeCommentClick = () => {};

	return (
		<div key={comment.commentID} className={styles.commentContainer}>
			<div className={styles.commentHeader}>
				<div className={styles.commentNickname}>마가렛 퀄리</div>
				{comment.isMyComment && !preview && (
					<div className={styles.commentMenu}>
						<div onClick={onModifyTextClick}>수정</div>
						<div onClick={onDeleteTextClick}>삭제</div>
					</div>
				)}
			</div>

			<div className={styles.commentContent}>{comment.content}</div>

			<div className={styles.commentInfo}>
				<div>{comment.date}</div>
				{comment.modifiedState && <div>(수정됨)</div>}
				<div> </div>
				{isLiked ? (
					<HeartFill
						className={styles.commentIcon}
						onClick={onUnlikeCommentClick}
					/>
				) : (
					<Heart className={styles.commentIcon} onClick={onLikeCommentClick} />
				)}
				<div>{comment.heart}</div>
			</div>
		</div>
	);
}
