import router from "next/router";
import { MouseEventHandler } from "react";
import { Button } from "react-bootstrap";

type btnProps = {
	label: String;
	onClick?: MouseEventHandler<HTMLButtonElement> | undefined;
	disabled?: boolean | undefined;
};

export default function BottomBtn({ label, onClick, disabled = false }: btnProps) {
	return (
		<div className="d-flex flex-column">
			<Button className="p-3" onClick={onClick} disabled={disabled}>
				{label}
			</Button>
		</div>
	);
}
