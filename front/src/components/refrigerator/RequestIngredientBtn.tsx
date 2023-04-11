import { Button } from "react-bootstrap";

type RequestIngredientBtnProps = {
	keyword: String;
};

export default function RequestIngredientBtn({ keyword }: RequestIngredientBtnProps) {
	return (
		<div className="pt-3 d-flex flex-column justify-content-center gap-2">
			<div className="text-muted text-center">추가하고 싶은 재료가 없다면</div>
			<Button onClick={() => "모달띄우기,이때keyword넘겨야됨"}>
				지금 입력한 식재료 추가 요청하기
			</Button>
		</div>
	);
}
