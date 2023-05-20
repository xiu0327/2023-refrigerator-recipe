import router from "next/router";

export default function AutoComplete({ setKeyword }) {
	const keywordData = ["가지", "가지볶음", "감자", "감자탕", "감자볶음"];
	return (
		<div>
			{keywordData.map((keyword, index) => (
				<div
					key={index}
					className="p-2 d-flex flex-column"
					onClick={() => {
						setKeyword(keyword);
						router.replace("/recipe/search/result");
					}}
				>
					<div>{keyword}</div>
					<hr style={{ marginTop: 10, marginBottom: 0 }} />
				</div>
			))}
		</div>
	);
}
