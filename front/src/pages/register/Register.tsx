import styles from "./styles.module.scss";
import { useState } from "react";
import InputBtn from "@/components/member/InputBtn/InputBtn";
import InputContent from "@/components/member/InputContent/InputContent";
import ModalOnBtn from "@/components/member/ModalOnBtn/ModalOnBtn";

export default function Register() {
	const [checkedEmail, setCheckedEmail] = useState(false);
	const [checkedPw, setCheckedPw] = useState(true);
	const [clickBtn, setClickBtn] = useState(true);
	return (
		<div className={styles.registerContainer}>
			<span className={styles.registerTitle}>회원가입</span>
			<div>
				<InputBtn formTitle="이메일" btnTitle="중복 확인" />
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
			<InputContent title="비밀번호" />
			<div>
				<InputContent title="비밀번호 확인" />
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
			<InputContent title="닉네임" />
			<div>
				<InputBtn formTitle="이메일 인증" btnTitle="인증번호 전송" />
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
			<InputBtn formTitle="인증번호 입력" btnTitle="인증번호 확인" />
			<div className={`d-grid gap-2`}>
				<ModalOnBtn title="가입하기" ment="가입" />
			</div>
		</div>
	);
}
