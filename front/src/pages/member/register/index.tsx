import styles from "./styles.module.scss";
import { useState } from "react";

import InputBtn from "@/components/member/InputBtn/InputBtn";
import InputContent from "@/components/member/InputContent/InputContent";
import ModalOnBtn from "@/components/member/ModalOnBtn/ModalOnBtn";

import { authenticate } from "@/api/athenticate";
import { duplicate } from "@/api/duplicate";
import { getCheckEmail } from "@/api/getCheckEmail";

export default function Register() {
	const [register, setRegister] = useState(false);
	// 양식에 맞게 작성, 이메일 인증 후 가입이 되게 만드는 ~~
	const [email, setEmail] = useState("");
	const [password, setPassword] = useState("");
	const [nickname, setNickname] = useState("");
	const [checkPw, setCheckPw] = useState("");
	const [checkEmail, setCheckEmail] = useState("");
	const [checkedEmail, setCheckedEmail] = useState(undefined);
	const [clickBtn, setClickBtn] = useState(false);
	const [code, setCode] = useState("");

	const onEmailHandler = (e: any) => {
		setEmail(e.target.value);
	}; // 이메일 작성

	const onPasswordHandler = (e: any) => {
		setPassword(e.target.value);
	}; // 비밀번호 작성

	const onCheckedPwHandler = (e: any) => {
		setCheckPw(e.target.value);
	}; // 비밀번호 확인 작성

	const onNicknameHandler = (e: any) => {
		setNickname(e.target.value);
	};

	const onCheckEmailHandler = (e: any) => {
		setCheckEmail(e.target.value);
	}; // 이메일 인증 작성

	const onAuthenticateHandler = (e: any) => {
		setCode(e.target.value);
	}; // 인증번호 입력 작성

	const onDuplicateCheckClick = () => {
		duplicate(email);
	};

	const onAuthenticateClick = () => {
		authenticate(checkEmail, code);
	}; // 인증번호 확인 클릭

	const onCheckEmailClick = () => {
		getCheckEmail(checkEmail);
		setClickBtn(true);
	}; // 인증번호 전송 버튼 클릭 시 실행

	return (
		<form className={styles.registerContainer}>
			<span className={styles.registerTitle}>회원가입</span>
			<div>
				<InputBtn
					formTitle="이메일"
					btnTitle="중복 확인"
					onClick={onDuplicateCheckClick}
					onChange={onEmailHandler}
				/>

				{/* <div className={styles.checkEmail}>
					{checkedEmail === undefined ? (
						""
					) : checkedEmail ? (
						<span className={styles.checkEmailTrue}>
							사용가능한 이메일입니다.
						</span>
					) : (
						<span className={styles.checkEmailFalse}>중복된 이메일입니다.</span>
					)}
				</div> */}
			</div>
			<div>
				<InputContent
					title="비밀번호"
					type="password"
					onChange={onPasswordHandler}
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
					title="비밀번호 확인"
					type="password"
					onChange={onCheckedPwHandler}
				/>
				<div className={styles.checkPw}>
					{password === checkPw ? (
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
				<InputContent title="닉네임" type="text" onChange={onNicknameHandler} />
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
			<div className={`d-grid gap-2`}>
				<ModalOnBtn
					title="가입하기"
					ment="가입"
					email={email}
					password={password}
					nickname={nickname}
				/>
			</div>
		</form>
	);
}
