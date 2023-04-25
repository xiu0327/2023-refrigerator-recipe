import { Button, Form, InputGroup } from "react-bootstrap";
import styles from "./InputBtn.module.scss";

export default function InputButton(props: {
	formTitle: string;
	btnTitle: string;
}) {
	return (
		<InputGroup className={`${styles.inputBtn} mb-3`}>
			<Form.Control
				placeholder={props.formTitle}
				aria-label="Recipient's username"
				aria-describedby="basic-addon2"
			/>
			<Button variant="outline-secondary" id="button-addon2">
				{props.btnTitle}
			</Button>
		</InputGroup>
	);
}
