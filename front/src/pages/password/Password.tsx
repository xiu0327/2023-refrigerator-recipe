import styles from "./styles.module.scss";
import { useState } from "react";
import LoginInput from "@/components/member/InputContent/InputContent";
import InputButton from "@/components/member/InputBtn/InputBtn";
import ModalOnBtn from "@/components/member/ModalOnBtn/ModalOnBtn";

export default function Password() {
	const [checkedPw, setCheckedPw] = useState(true);
	const [clickBtn, setClickBtn] = useState(true);
	return (
		<div className={styles.passwordContainer}>
			<span className={styles.passwordTitle}>비밀번호 변경</span>
			<LoginInput title={"기존 비밀번호"} />
			<LoginInput title={"새 비밀번호"} />
			<div>
				<LoginInput title={"새 비밀번호 확인"} />
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
			<div>
				<InputButton formTitle="이메일 인증" btnTitle="인증번호 전송" />
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
			<InputButton formTitle="인증번호 입력" btnTitle="인증번호 입력" />
			<ModalOnBtn title="변경하기" ment="변경" />
		</div>
	);
}
