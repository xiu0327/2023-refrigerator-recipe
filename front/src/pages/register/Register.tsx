import styles from "./styles.module.css";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import InputGroup from "react-bootstrap/InputGroup";
import Modal from "react-bootstrap/Modal";
import { useState } from "react";
import Link from "next/link";

function MyVerticallyCenteredModal(props: any) {
	return (
		<Modal
			{...props}
			size="lg"
			aria-labelledby="contained-modal-title-vcenter"
			centered
		>
			<Modal.Body>
				<p>가입이 완료되었습니다.</p>
			</Modal.Body>
			<Modal.Footer>
				<Link legacyBehavior href="/">
					<Button onClick={props.onHide}>확인</Button>
				</Link>
			</Modal.Footer>
		</Modal>
	);
}

export default function Register() {
	const [checkedEmail, setCheckedEmail] = useState(false);
	const [checkedPw, setCheckedPw] = useState(true);
	const [clickBtn, setClickBtn] = useState(true);
	const [modalShow, setModalShow] = useState(false);
	return (
		<div className={styles.registerContainer}>
			<span className={styles.registerTitle}>회원가입</span>
			<div>
				<InputGroup className={`${styles.registerInput} mb-3`}>
					<Form.Control
						placeholder="이메일"
						aria-label="Recipient's username"
						aria-describedby="basic-addon2"
					/>
					<Button variant="outline-secondary" id="button-addon2">
						중복 확인
					</Button>
				</InputGroup>
				<div className={styles.checkEmail}>
					{checkedEmail ? (
						<span className={styles.checkEmailTrue}>
							사용가능한 이메일입니다.
						</span>
					) : (
						<span className={styles.checkEmailFalse}>중복된 이메일입니다.</span>
					)}
				</div>
			</div>
			<InputGroup className={`${styles.registerInput} mb-3`}>
				<Form.Control
					placeholder="비밀번호"
					aria-label="Recipient's username"
					aria-describedby="basic-addon2"
				/>
			</InputGroup>
			<div>
				<InputGroup className={`${styles.registerInput} mb-3`}>
					<Form.Control
						placeholder="비밀번호 확인"
						aria-label="Recipient's username"
						aria-describedby="basic-addon2"
					/>
				</InputGroup>
				<div className={styles.checkPw}>
					{checkedPw ? (
						<span className={styles.checkPwTrue}>비밀번호가 일치합니다.</span>
					) : (
						<span className={styles.checkPwFalse}>
							비밀번호가 일치하지 않습니다.
						</span>
					)}
				</div>
			</div>
			<InputGroup className={`${styles.registerInput} mb-3`}>
				<Form.Control
					placeholder="닉네임"
					aria-label="Recipient's username"
					aria-describedby="basic-addon2"
				/>
			</InputGroup>
			<div>
				<InputGroup className={`${styles.registerInput} mb-3`}>
					<Form.Control
						placeholder="이메일 인증"
						aria-label="Recipient's username"
						aria-describedby="basic-addon2"
					/>
					<Button variant="outline-secondary" id="button-addon2">
						인증번호 전송
					</Button>
				</InputGroup>
				<div className={styles.clickBtn}>
					{clickBtn ? (
						<span className={styles.clickTrue}>
							<span>
								인증번호가 전송되었습니다. 30분 이내에 입력 부탁드리며,
							</span>
							<span>메일이 오지 않을 경우 스팸 메일함을 확인해주세요!</span>
						</span>
					) : (
						<span className={styles.clickFalse}></span>
					)}
				</div>
			</div>
			<InputGroup className={`${styles.registerInput} mb-3`}>
				<Form.Control
					placeholder="인증번호 입력"
					aria-label="Recipient's username"
					aria-describedby="basic-addon2"
				/>
				<Button variant="outline-secondary" id="button-addon2">
					인증번호 입력
				</Button>
			</InputGroup>
			<div className={`d-grid gap-2`}>
				<Button
					className={styles.registerBtn}
					variant="primary"
					size="lg"
					onClick={() => setModalShow(true)}
				>
					가입하기
				</Button>

				<MyVerticallyCenteredModal
					show={modalShow}
					onHide={() => setModalShow(false)}
				/>
			</div>
		</div>
	);
}
