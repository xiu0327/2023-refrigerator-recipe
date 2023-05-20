import { useState } from "react";
import styles from "./CommentBox.module.scss";
import {
	addComment,
	getCommentsByLike,
	getMyComments,
	modifyComment,
} from "@/api";
import { login } from "@/api/login";
import { X } from "react-bootstrap-icons";

export default function CommentBox({
	recipeID,
	setCommentData,
	comment,
	setComment,
	modifyMode,
	setModifyMode,
	commentID,
}) {
	const onCommentBtnClick = async () => {
		modifyMode
			? await modifyComment(commentID, comment)
			: await addComment(recipeID, comment);
		// const data = await getCommentsByLike(recipeID, 0, 10);
		const myData = await getMyComments(recipeID);
		setCommentData(myData);
		setComment("");
		setModifyMode(false);
	};

	const onCommentSubmit = (event) => {
		event.preventDefault();
		onCommentBtnClick();
	};

	const onXBtnClick = () => {
		setComment("");
		setModifyMode(false);
	};

	return (
		<div className={styles.commentBoxContainer}>
			{modifyMode && (
				<div className={styles.commentStatusBar}>
					<div className={styles.commentStatus}>댓글 수정 중 ...</div>
					<X onClick={onXBtnClick} />
				</div>
			)}
			<div className={styles.commentInputContainer}>
				<form className="d-flex flex-grow-1" onSubmit={onCommentSubmit}>
					<input
						className={styles.commentBox}
						placeholder="레시피에 댓글을 남겨보세요"
						onChange={(e) => setComment(e.target.value)}
						value={comment}
					/>
				</form>
				<button
					className={styles.commentBtn}
					disabled={!Boolean(comment)}
					onClick={onCommentBtnClick}
				>
					{modifyMode ? "수정" : "등록"}
				</button>
			</div>
		</div>
	);
}
