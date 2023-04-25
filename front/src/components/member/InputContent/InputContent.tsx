import { Form, InputGroup } from "react-bootstrap";
import styles from "./InputContent.module.scss";

export default function InputContent(props: { title: string }) {
	return (
		<InputGroup className={`${styles.loginEmailInput} mb-3`}>
			<Form.Control
				placeholder={props.title}
				aria-label="Recipient's username"
				aria-describedby="basic-addon2"
			/>
		</InputGroup>
	);
}
