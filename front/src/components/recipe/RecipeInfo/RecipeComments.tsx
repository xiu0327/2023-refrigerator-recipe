import { useEffect, useState } from "react";
import styles from "./RecipeInfo.module.scss";
import {
	deleteComment,
	getCommentsByDate,
	getCommentsByLike,
	getRecipeCommentPreview,
	getRecipeSteps,
	likeComment,
	modifyComment,
	unlikeComment,
	setCommentID,
} from "@/api";
import { RecipeComment } from "@/types/types";
import { login } from "@/api/login";
import { Heart, HeartFill, Link, ThreeDots } from "react-bootstrap-icons";
import router from "next/router";

export default function RecipeComments({
	recipeID,
	commentData,
	setCommentData,
	sortType,
	heartCommentIds,
	setHeartCommentIds,
	setComment,
	setModifyMode,
	setCommentID,
}) {
	const onLikeCommentBtnClick = (commentID) => {
		likeComment(commentID);
		setHeartCommentIds((prev) => [...prev, commentID]);
		setCommentData((prevData) =>
			prevData.map((item) =>
				item.commentID === commentID
					? { ...item, heart: item.heart + 1 }
					: item,
			),
		);
	};

	const onUnlikeCommentBtnClick = (commentID) => {
		unlikeComment(commentID);
		setHeartCommentIds((prevIds) => prevIds.filter((id) => id !== commentID));
		setCommentData((prevData) =>
			prevData.map((item) =>
				item.commentID === commentID
					? { ...item, heart: item.heart - 1 }
					: item,
			),
		);
	};

	const onDeleteTextClick = (commentID) => {
		// TODO: 삭제 확인 모달 띄우기
		deleteComment(commentID);
		setCommentData((prev) =>
			prev.filter((comment) => comment.commentID !== commentID),
		);
	};

	const onModifyTextClick = (comment) => {
		setCommentID(comment.commentID);
		setModifyMode(true);
		setComment(comment.content);
	};

	// TODO: RecipeComments Component 빼기
	// TODO: 내가 작성한 댓글만 수정, 삭제 적용
	return (
		<div className="d-flex flex-column">
			{commentData &&
				commentData.map((comment) => (
					<div key={comment.commentID} className={styles.recipeComments}>
						<div className={styles.commentNickname}>
							<div className="flex-grow-1">{comment.nickname}</div>
							{/* <ThreeDots
								className={styles.commentIcon}
								onClick={onCommentMenuBtnClick}
							/> */}
							{comment.isMyComment && (
								<div>
									<button
										className={styles.commentDeleteBtn}
										onClick={() => onModifyTextClick(comment)}
									>
										수정
									</button>
									<button
										className={styles.commentModifyBtn}
										onClick={() => onDeleteTextClick(comment.commentID)}
									>
										삭제
									</button>
								</div>
							)}
						</div>
						<div className={styles.commentContent}>{comment.content}</div>
						<div className={styles.commentExtraInfo}>
							<div>{comment.date}</div>
							{comment.modifiedState && <div>(수정됨)</div>}
							<div> </div>
							{heartCommentIds.includes(comment.commentID) ? (
								<HeartFill
									className={styles.commentIcon}
									onClick={() => onUnlikeCommentBtnClick(comment.commentID)}
								/>
							) : (
								<Heart
									className={styles.commentIcon}
									onClick={() => onLikeCommentBtnClick(comment.commentID)}
								/>
							)}
							<div>{comment.heart}</div>
						</div>
					</div>
				))}
		</div>
	);
}
