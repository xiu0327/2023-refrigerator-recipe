import { Form } from "react-bootstrap";

type fixedFormProps = {
	value: string;
	label?: String;
};

export default function FixedForm({ value, label }: fixedFormProps) {
	return (
		<div className="d-flex align-items-center gap-3">
			<Form.Control className="text-center" value={value} disabled />
			{label ? <div>{label}</div> : <></>}
		</div>
	);
}
