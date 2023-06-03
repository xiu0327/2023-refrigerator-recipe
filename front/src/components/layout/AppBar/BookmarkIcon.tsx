import { useEffect, useState } from "react";
import { Bookmark, BookmarkStarFill } from "react-bootstrap-icons";
import { addBookmark, removeBookmark } from "@/api";
import styles from "./AppBar.module.scss";

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
		console.log(bookmarkIDs);
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
					className={styles.appbarIcon}
					onClick={onRemoveBookmarkClick}
				/>
			) : (
				<Bookmark className={styles.appbarIcon} onClick={onAddBookmarkClick} />
			)}
		</div>
	);
};
