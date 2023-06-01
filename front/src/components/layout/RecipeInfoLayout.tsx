import AppBar from "./AppBar/AppBar";
import { BackIcon } from "./AppBar/AppBarIcons";
import { BookmarkIcon } from "./AppBar/BookmarkIcon";
import styles from "./Layout.module.scss";
import NavBar from "./NavBar/NavBar";

type layoutProps = {
	recipeName: string;
	children: React.ReactNode;
	recipeID: number;
	bookmarkIDs: number[];
	setBookmarkIDs: Function;
	isAppbarAboveImg?: boolean;
};

export default function RecipeInfoLayout({
	recipeName,
	children,
	recipeID,
	bookmarkIDs,
	setBookmarkIDs,
	isAppbarAboveImg,
}: layoutProps) {
	return (
		<div className={styles.layoutContainer}>
			<AppBar title={recipeName} isAboveImg={true}>
				<BackIcon />
				<BookmarkIcon
					recipeID={recipeID}
					bookmarkIDs={bookmarkIDs}
					setBookmarkIDs={setBookmarkIDs}
				/>
			</AppBar>
			<div className={styles.layoutContentWithAppNav}>{children}</div>
			<NavBar activeLabel="레시피" />
		</div>
	);
}
