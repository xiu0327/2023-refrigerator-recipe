import { Form } from "react-bootstrap";
import Feedback from "react-bootstrap/esm/Feedback";

type propsType = {
	label: String;
};

export default function Switch({ label }: propsType) {
	return (
		<Form className="py-3 d-flex gap-2 justify-content-end">
			<label>{label}</label>
			<Form.Switch id="switch" />
		</Form>
	);
}
