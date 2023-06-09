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
	const [isBookmarkedLoaded, setIsBookmarkedLoaded] = useState(false);

	useEffect(() => {
		setIsBookmarked(bookmarkIDs.includes(recipeID));
		setIsBookmarkedLoaded(true);
	}, []);

	const onAddBookmarkClick = async () => {
		setIsBookmarked(true);
		await addBookmark(recipeID, setBookmarkIDs);
	};

	const onRemoveBookmarkClick = async () => {
		setIsBookmarked(false);
		await removeBookmark(recipeID, setBookmarkIDs);
	};

	return (
		<div>
			{isBookmarkedLoaded && (
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
			)}
		</div>
	);
};
