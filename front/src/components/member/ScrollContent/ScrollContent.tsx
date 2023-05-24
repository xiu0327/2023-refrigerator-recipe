import router from "next/router";
import styles from "./ScrollContent.module.scss";

export default function ScrollContent(props: any) {
	const onClickHandler = () => {};
	const onClickPlusHandler = (content: string) => {
		router.push(`/${content}`);
	};
	return props.content === "star" ? (
		<div className={styles.scroll}>
			{props.starPreview?.map((i) => (
				<div className={styles.content} key={i.scoreId}>
					<button className={styles.recipeBtn} onClick={onClickHandler}>
						<img className={styles.recipeImage} src={i.recipeImage} />
					</button>
					<div className={styles.recipeNameWrapper}>
						<div className={styles.recipeName}>{i.recipeName}</div>
					</div>
				</div>
			))}
			<div className={styles.content}>
				<button
					className={styles.recipeBtn__plus}
					onClick={() => {
						onClickPlusHandler("star");
					}}
				>
					<span>
						<span className={styles.plus}>+</span>
						<span>더보기</span>
					</span>
				</button>
			</div>
		</div>
	) : (
		<div className={styles.scroll}>
			{props.bookPreview?.map((i) => (
				<div className={styles.content} key={i.recipeId}>
					<button className={styles.recipeBtn} onClick={onClickHandler}>
						<img className={styles.recipeImage} src={i.recipeImage} />
					</button>
					<div className={styles.recipeNameWrapper}>
						<div className={styles.recipeName}>{i.recipeName}</div>
					</div>
				</div>
			))}
			<div className={styles.content}>
				<button
					className={styles.recipeBtn__plus}
					onClick={() => {
						onClickPlusHandler("bookmark");
					}}
				>
					<span>
						<span className={styles.plus}>+</span>
						<span>더보기</span>
					</span>
				</button>
			</div>
		</div>
	);
}
