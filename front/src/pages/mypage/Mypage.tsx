import styles from "./styles.module.scss";
import { PencilFill } from "react-bootstrap-icons";
import ConfirmCancelModal from "@/components/member/ConfirmCancelModal/ConfirmCancelModal";
import LinkBtn from "@/components/member/LinkBtn/LinkBtn";
import ScrollContent from "@/components/member/ScrollContent/ScrollContent";
import Link from "next/link";
import { Button } from "react-bootstrap";

export default function Mypage() {
	return (
		<div className={styles.mypageContainer}>
			<span className={styles.mypageTitle}>마이페이지</span>
			<div className={styles.mypageMe}>
				<img
					className={styles.mypageImg}
					src="https://i.pinimg.com/236x/db/33/a6/db33a6bfaf54c9fccd72b7edf619a053.jpg"
				/>
				<span className={styles.mypageBackground}>
					닉네임 <PencilFill className={styles.mypagePencil} />
				</span>
			</div>
			<div className={styles.mypageStar}>
				<span className={styles.mypageStarTitle}>내가 남긴 별점 (11)</span>
				<ScrollContent content="star" />
			</div>
			<div className={styles.mypageBookmark}>
				<span className={styles.mypageBookmarkTitle}>북마크 (26)</span>
				<ScrollContent content="bookmark" />
			</div>
			<div className={`d-grid gap-2`}>
				<LinkBtn title={"공지사항"} link={"../announcement/announcement"} />
				<LinkBtn title={"비밀번호 찾기"} link={"../password/password"} />
			</div>
			<div className={styles.mypageLink}>
				<ConfirmCancelModal title="로그아웃" ment="로그아웃 하시겠습니까?" />
				<span>|</span>
				<Link legacyBehavior href="../unregister/unregister">
					<Button className={styles.mypageUnregister} variant="primary">
						회원탈퇴
					</Button>
				</Link>
			</div>
		</div>
	);
}
