import { useRef, useState } from "react";
import styles from "./styles.module.css";
import { GripVertical, PencilFill, PencilSquare } from "react-bootstrap-icons";
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
				<div className={styles.mypageStarScroll}>
					<div className={styles.mypageStarContent}>사진 1</div>
					<div className={styles.mypageStarContent}>사진 2</div>
					<div className={styles.mypageStarContent}>사진 3</div>
					<div className={styles.mypageStarContent}>사진 4</div>
					<div className={styles.mypageStarContent}>사진 5</div>
					<div className={styles.mypageStarContent}>더보기(링크)</div>
				</div>
			</div>
			<div className={styles.mypageBookmark}>
				<span className={styles.mypageBookmarkTitle}>북마크 (26)</span>
				<div className={styles.mypageBookmarkScroll}>
					<div className={styles.mypageBookmarkContent}>사진 1</div>
					<div className={styles.mypageBookmarkContent}>사진 2</div>
					<div className={styles.mypageBookmarkContent}>사진 3</div>
					<div className={styles.mypageBookmarkContent}>사진 4</div>
					<div className={styles.mypageBookmarkContent}>사진 5</div>
					<div className={styles.mypageBookmarkContent}>더보기(링크)</div>
				</div>
			</div>
			<div className={`d-grid gap-2`}>
				<Link legacyBehavior href="../announcement/Announcement">
					<Button className={styles.loginBtn} variant="primary" size="lg">
						<span>공지사항</span>
					</Button>
				</Link>
				<Link legacyBehavior href="../password/Password">
					<Button className={styles.loginBtn} variant="primary" size="lg">
						<span>비밀번호 변경</span>
					</Button>
				</Link>
			</div>
			<div className={styles.mypageLink}>
				<a href="../logout/Logout">로그아웃</a>
				<span>|</span>
				<a href="../secession/Secession">회원탈퇴</a>
			</div>
		</div>
	);
}
