import { useEffect, useRef, useState } from "react";
import { useRouter } from "next/router";

import {
	getCommentsByLike,
	getCommentsByDate,
	getMyComments,
	getHeartCommentIDs,
} from "@/api";

import RecipeCommentLayout from "@/components/layout/RecipeCommentLayout";
import SortBar from "@/components/recipe/Bar/SortBar";
import Comment from "@/components/recipe/Comment/Comment";
import CommentInputForm from "@/components/recipe/Comment/CommentInputForm";

import styles from "@/scss/pages.module.scss";

export default function RecipeCommentPage() {
	const router = useRouter();
	const recipeID = Number(router.query.recipeID);
	const recipeName = router.query.recipeName;

	const [sortType, setSortType] = useState<"좋아요순" | "최신순">("좋아요순");
	const [myCommentData, setMyCommentData] = useState([]);
	const [otherCommentData, setOtherCommentData] = useState([]);

	const [modifyMode, setModifyMode] = useState(false);
	const [heartCommentIDs, setHeartCommentIDs] = useState([]);

	const [comment, setComment] = useState("");
	const [commentID, setCommentID] = useState(0);
	const commentInputRef = useRef(null);

	useEffect(() => {
		(async () => {
			const myData = await getMyComments(recipeID);
			setMyCommentData(myData);

			// NOTE: localstorage에 저장할지 고민 중
			const heartData = await getHeartCommentIDs();
			setHeartCommentIDs(heartData);
		})();
	}, []);

	useEffect(() => {
		(async () => {
			const otherData =
				sortType === "좋아요순"
					? await getCommentsByLike(recipeID, 0)
					: await getCommentsByDate(recipeID, 0);
			setOtherCommentData(otherData);
		})();
	}, [sortType]);

	return (
		<RecipeCommentLayout title={recipeName}>
			<div className={styles.fixed}>
				<SortBar sortType={sortType} setSortType={setSortType} />
			</div>
			<div
				className={styles.commentListContainer}
				style={{ marginTop: "46px" }}
			>
				{[...myCommentData, ...otherCommentData]?.map((comment) => (
					<div key={comment.commentID}>
						<Comment
							comment={comment}
							isLiked={heartCommentIDs.includes(comment.commentID)}
							setComment={setComment}
							setCommentID={setCommentID}
							setModifyMode={setModifyMode}
							setMyCommentData={setMyCommentData}
							setOtherCommentData={setOtherCommentData}
							setHeartCommentIDs={setHeartCommentIDs}
						/>
					</div>
				))}
			</div>
			<CommentInputForm
				recipeID={recipeID}
				setMyCommentData={setMyCommentData}
				comment={comment}
				setComment={setComment}
				modifyMode={modifyMode}
				setModifyMode={setModifyMode}
				commentID={commentID}
				commentInputRef={commentInputRef}
			/>
		</RecipeCommentLayout>
	);
}
