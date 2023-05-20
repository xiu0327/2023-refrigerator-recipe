import { unregister } from "@/api/unregister";
import Link from "next/link";
import { Button, Modal } from "react-bootstrap";

// interface ModalsProps {
// 	show: boolean;
// 	handleClose: () => void;
// }

export default function ChangePwModal({ show, handleClose, email }) {
	return (
		<Modal show={show} onHide={handleClose}>
			<Modal.Header>
				<Modal.Title>회원탈퇴</Modal.Title>
			</Modal.Header>
			<Modal.Body>정말 탈퇴하시겠습니까?</Modal.Body>
			<Modal.Footer>
				<Link legacyBehavior href="/">
					<Button
						onClick={() => {
							password;
							console.log("비밀번호 변경 완.");
						}}
					>
						확인
					</Button>
				</Link>
				<Button variant="secondary" onClick={handleClose}>
					취소
				</Button>
			</Modal.Footer>
		</Modal>
	);
}
