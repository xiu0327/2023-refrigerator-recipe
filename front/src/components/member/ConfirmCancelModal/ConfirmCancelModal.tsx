import Link from "next/link";
import { useState } from "react";
import { Button, Modal } from "react-bootstrap";
import styles from "./ConfirmCancelModal.module.scss";
import { logout } from "@/api/logout";

export default function ConfirmCancelModal(props: {
	title: string;
	ment: string;
}) {
	const [show, setShow] = useState(false);

	const handleClose = () => setShow(false);
	const handleShow = () => setShow(true);

	return (
		<>
			<Button className={styles.button} variant="primary" onClick={handleShow}>
				{props.title}
			</Button>

			<Modal show={show} onHide={handleClose}>
				<Modal.Header>
					<Modal.Title>{props.title}</Modal.Title>
				</Modal.Header>
				<Modal.Body>{props.ment}</Modal.Body>
				<Modal.Footer>
					{/* <Link legacyBehavior href="/"> */}
					<Button onClick={logout}>확인</Button>
					{/* </Link> */}
					<Button variant="secondary" onClick={handleClose}>
						취소
					</Button>
				</Modal.Footer>
			</Modal>
		</>
	);
}
