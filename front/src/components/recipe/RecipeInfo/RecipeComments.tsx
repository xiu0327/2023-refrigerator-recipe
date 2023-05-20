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
import Comment from "../Comment/Comment";

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
			{commentData?.map((comment) => (
				<Comment
					comment={comment}
					isLiked={heartCommentIds.includes(comment.commentID)}
				/>
			))}
		</div>
	);
}
