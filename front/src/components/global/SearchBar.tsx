import router from "next/router";
import { Button, Form } from "react-bootstrap";

type searchbarType = {
	placeholder: string | undefined;
	setKeyword: Function;
};

export default function SearchBar({ placeholder, setKeyword }: searchbarType) {
	// 돋보기 아이콘! 입력 시 한 번에 지울 수 있는 엑스 버튼!
	return (
		<div className="d-flex gap-1" style={{ height: 42 }}>
			<Form.Control
				type="text"
				placeholder={placeholder}
				id="searchbox"
				onChange={(e) => setKeyword(e.target.value)}
			/>
		</div>
	);
}
