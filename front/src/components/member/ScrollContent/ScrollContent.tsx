import instance from "@/api/interceptors";
import styles from "./ScrollContent.module.scss";
import { useEffect, useState } from "react";

export default function ScrollContent(props: any) {
	const onClickHandler = () => {};
	return props.content === "star" ? (
		<div className={styles.scroll}>
			{props.scorePreview.map((i) => (
				<div className={styles.content} key={i.scoreId}>
					<button className={styles.recipeBtn} onClick={onClickHandler}>
						<img className={styles.recipeImage} src={i.image} />
					</button>
					<div className={styles.recipeName}>{i.scoreName}</div>
				</div>
			))}
		</div>
	) : (
		<div className={styles.scroll}>
			{props.bookmarkPreview.map((i) => (
				<div className={styles.content} key={i.recipeId}>
					<button className={styles.recipeBtn} onClick={onClickHandler}>
						<img className={styles.recipeImage} src={i.image} />
					</button>
					<div className={styles.recipeName}>{i.recipeName}</div>
				</div>
			))}
		</div>
	);
}
