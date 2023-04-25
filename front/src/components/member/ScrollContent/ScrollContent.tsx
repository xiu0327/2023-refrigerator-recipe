import styles from "./ScrollContent.module.scss";

export default function ScrollContent(props: { content: string }) {
	const starContent = [
		{
			title: "s사진1",
		},
		{
			title: "s사진2",
		},
		{
			title: "s사진3",
		},
		{
			title: "s사진4",
		},
		{
			title: "s사진5",
		},
		{
			title: "더보기(링크)",
		},
	];
	const bookmark = [
		{
			title: "b사진1",
		},
		{
			title: "b사진2",
		},
		{
			title: "b사진3",
		},
		{
			title: "b사진4",
		},
		{
			title: "b사진5",
		},
		{
			title: "더보기(링크)",
		},
	];
	return props.content === "star" ? (
		<div className={styles.scroll}>
			{starContent.map((items) => (
				<div className={styles.content} key={items.title}>
					{items.title}
				</div>
			))}
		</div>
	) : (
		<div className={styles.scroll}>
			{bookmark.map((items) => (
				<div className={styles.content} key={items.title}>
					{items.title}
				</div>
			))}
		</div>
	);
}
