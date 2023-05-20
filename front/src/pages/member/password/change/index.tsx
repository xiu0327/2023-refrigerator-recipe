import styles from "./styles.module.scss";
import { useState } from "react";
import InputContent from "@/components/member/InputContent/InputContent";
import ModalOnBtn2 from "@/components/member/ModalOnBtn/ModalOnBtn2";
import InputBtn from "@/components/member/InputBtn/InputBtn";
import { authenticate } from "@/api/athenticate";
import { getCheckEmail } from "@/api/getCheckEmail";

export default function change() {
	const [clickBtn, setClickBtn] = useState(false);
	const [newPassword, setNewPassword] = useState("");
	const [checkPw, setCheckPw] = useState("");
	const [checkEmail, setCheckEmail] = useState("");
	const [code, setCode] = useState("");

	const onNewPasswordHandler = (e: any) => {
		setNewPassword(e.target.value);
	};

	const onNewCheckedPwHandler = (e: any) => {
		setCheckPw(e.target.value);
	};

	const onCheckEmailHandler = (e: any) => {
		setCheckEmail(e.target.value);
	}; // 이메일 인증 작성

	const onAuthenticateHandler = (e: any) => {
		setCode(e.target.value);
	}; // 인증번호 입력 작성

	const onAuthenticateClick = () => {
		authenticate(checkEmail, code);
	}; // 인증번호 확인 클릭

	const onCheckEmailClick = () => {
		getCheckEmail(checkEmail);
		setClickBtn(true);
	}; // 인증번호 전송 버튼 클릭 시 실행

	return (
		<div className={styles.passwordContainer}>
			<span className={styles.passwordTitle}>비밀번호 변경</span>
			<div>
				<InputContent
					title="새 비밀번호"
					type="password"
					onChange={onNewPasswordHandler}
				/>
				<div>
					<span className={styles.regularPw}>
						<span>문자, 숫자, 특수 문자(@$!%*#?&)의 조합으로 이루어진</span>
						<span>길이 4이상 16이하 문자</span>
					</span>
				</div>
			</div>
			<div>
				<InputContent
					title="새 비밀번호 확인"
					type="password"
					onChange={onNewCheckedPwHandler}
				/>
				<div className={styles.checkPw}>
					{newPassword === checkPw ? (
						checkPw === "" ? (
							""
						) : (
							<span className={styles.checkPwTrue}>비밀번호가 일치합니다.</span>
						)
					) : (
						<span className={styles.checkPwFalse}>
							비밀번호가 일치하지 않습니다.
						</span>
					)}
				</div>
			</div>

			<div>
				<InputBtn
					formTitle="이메일 인증"
					btnTitle="인증번호 전송"
					onChange={onCheckEmailHandler}
					onClick={onCheckEmailClick}
				/>
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
			<InputBtn
				formTitle="인증번호 입력"
				btnTitle="인증번호 확인"
				onChange={onAuthenticateHandler}
				onClick={onAuthenticateClick}
			/>
			<ModalOnBtn2 title="변경하기" ment="변경" password={newPassword} />
		</div>
	);
}
