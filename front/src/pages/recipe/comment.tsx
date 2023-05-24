import { login } from "@/api/login";
// import RecipeCommentLayout from "@/components/layout/RecipeCommentLayout";
import RecipeComments from "@/components/recipe/RecipeInfo/RecipeComments";
// import SortBar from "@/components/recipe/FilterBar/SortBar";
import { Dispatch, SetStateAction, useEffect, useState } from "react";
import CommentBox from "@/components/recipe/CommentBox/CommentBox";
import { useRouter } from "next/router";
import {
	likeComment,
	getCommentsByLike,
	getCommentsByDate,
	getMyComments,
	getHeartCommentIDs,
} from "@/api";
import BackLayout from "@/components/layout/BackLayout";
import SortBar from "@/components/recipe/FilterBar/SortBar";

export default function RecipeCommentPage() {
	const router = useRouter();
	const recipeID = Number(router.query.recipeID);

	const [sortType, setSortType] = useState("좋아요순");
	const [commentData, setCommentData] = useState([]);
	const [myCommentData, setMyCommentData] = useState([]);

	const [heartCommentIDs, setHeartCommentIDs] = useState([]);
	const [comment, setComment] = useState("");
	const [modifyMode, setModifyMode] = useState(false);

	const [commentID, setCommentID] = useState(0);

	useEffect(() => {
		// TODO: sortType, heartData 분리하기
		(async () => {
			const data =
				sortType === "좋아요순"
					? await getCommentsByLike(recipeID, 0)
					: await getCommentsByDate(recipeID, 0);
			const myData = await getMyComments(recipeID);
			setCommentData(data);
			setMyCommentData(myData);

			const heartData = await getHeartCommentIDs();
			setHeartCommentIDs(heartData);
		})();
	}, [sortType]);

	return (
		<BackLayout title="댓글">
			{/* <SortBar sortType={sortType} setSortType={setSortType} /> */}
			<RecipeComments
				sortType={sortType}
				commentData={[...myCommentData, ...commentData]}
				setCommentData={setMyCommentData}
				recipeID={recipeID}
				heartCommentIds={heartCommentIDs}
				setHeartCommentIds={setHeartCommentIDs}
				setComment={setComment}
				setModifyMode={setModifyMode}
				setCommentID={setCommentID}
			/>
			{/* <CommentBox
				recipeID={recipeID}
				setCommentData={setMyCommentData}
				comment={comment}
				setComment={setComment}
				modifyMode={modifyMode}
				setModifyMode={setModifyMode}
				commentID={commentID}
			/> */}
		</BackLayout>
	);
}
