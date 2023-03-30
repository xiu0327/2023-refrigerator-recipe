import Link from "next/link";
import { Button, Modal } from "react-bootstrap";

export default function CheckModal(props: any) {
	return (
		<Modal
			{...props}
			size="lg"
			aria-labelledby="contained-modal-title-vcenter"
			centered
		>
			<Modal.Body>
				<p>{props.ment}</p>
			</Modal.Body>
			<Modal.Footer>
				<Link legacyBehavior href="/">
					<Button onClick={props.onHide}>확인</Button>
				</Link>
			</Modal.Footer>
		</Modal>
	);
}
