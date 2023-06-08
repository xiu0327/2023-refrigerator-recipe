import { useEffect, useState } from "react";
import { Bookmark, BookmarkStarFill } from "react-bootstrap-icons";
import { addBookmark, removeBookmark } from "@/api";
import styles from "./RecipeInfo.module.scss";

type BookmarkProps = {
	recipeID: number;
	bookmarkIDs: number[];
	setBookmarkIDs: Function;
};

export const BookmarkIcon = ({
	recipeID,
	bookmarkIDs,
	setBookmarkIDs,
}: BookmarkProps) => {
	const [isBookmarked, setIsBookmarked] = useState(false);

	useEffect(() => {
		setIsBookmarked(bookmarkIDs.includes(recipeID));
	}, []);

	const onAddBookmarkClick = async () => {
		await addBookmark(recipeID, setBookmarkIDs);
		setIsBookmarked(true);
	};

	const onRemoveBookmarkClick = async () => {
		await removeBookmark(recipeID, setBookmarkIDs);
		setIsBookmarked(false);
	};

	return (
		<div>
			{isBookmarked ? (
				<BookmarkStarFill
					className={styles.bookmarkedIcon}
					onClick={onRemoveBookmarkClick}
				/>
			) : (
				<Bookmark
					className={styles.bookmarkIcon}
					onClick={onAddBookmarkClick}
				/>
			)}
		</div>
	);
};
