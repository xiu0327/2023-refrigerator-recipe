import { useEffect, useState } from "react";
import styles from "./styles.module.scss";
import { PencilFill } from "react-bootstrap-icons";
import ConfirmCancelModal from "@/components/member/ConfirmCancelModal/ConfirmCancelModal";
import LinkBtn from "@/components/member/LinkBtn/LinkBtn";
import ScrollContent from "@/components/member/ScrollContent/ScrollContent";
import ProfileModal from "@/components/member/ProfileModal/ProfileModal";
import { logout } from "@/api/logout";
import { unregister } from "@/api/unregister";
import {
	profile,
	nickname,
	bookmarkPreview,
	scorePreview,
	getBookmarks,
} from "@/api";
import BackLayout from "@/components/layout/BackLayout";

export default function Mypage() {
	const [nick, setNick] = useState("");
	const [img, setImg] = useState("");
	const [bookPreview, setBookPreview] = useState<Array<object>>([]);
	const [starPreview, setStarPreview] = useState<Array<object>>([]);
	const [bookmarkCount, setBookmarkCount] = useState(0);
	const [starCount, setStarCount] = useState(0);
	const [isModalOpen, setIsModalOpen] = useState(false);

	const openModal = () => {
		setIsModalOpen(true);
	};

	const closeModal = () => {
		setIsModalOpen(false);
	};

	const fetchProfile = async () => {
		try {
			const response = await profile();
			const existingNick = response !== undefined ? response.data.nickname : "";
			setNick(existingNick);
			const existingImage =
				response !== undefined ? response.data.profileImage : "";
			setImg(existingImage);
		} catch (error) {}
	}; // 회원 정보 가져오기

	const fetchBookmarkPreview = async () => {
		try {
			const response = await bookmarkPreview();
			setBookPreview(response.bookmarks);
			setBookmarkCount(response.count);
		} catch (error) {
			console.log(error);
		}
	};

	const fetchScorePreview = async () => {
		try {
			const response = await scorePreview();
			setStarPreview(response.scores);
			setStarCount(response.count);
			//console.log(starPreview);
		} catch (error) {
			console.log(error);
		}
	};

	const handleImgChange = (newImg: string) => {
		setImg(newImg);
	};

	useEffect(() => {
		fetchProfile();
	}, [img]); // 개인 정보 조회 받아오기

	useEffect(() => {
		fetchBookmarkPreview();
		fetchScorePreview();
	}, []);

	const onChangeHandler = (e: any) => {
		setNick(e.target.value);
	};
	const onClickHandler = () => {
		nickname(nick);
	};

	return (
		<BackLayout>
			<div className={styles.mypageContainer}>
				<span className={styles.mypageTitle}>마이페이지</span>
				<div className={styles.mypageMe}>
					<img className={styles.mypageImg} src={img} onClick={openModal} />
					{isModalOpen && (
						<div onClick={closeModal}>
							<div>
								<ProfileModal
									on={true}
									img={img}
									onImgChange={handleImgChange}
								/>
							</div>
						</div>
					)}
					<div className={styles.mypageBackground}>
						<input
							placeholder="닉네임"
							value={nick}
							onChange={onChangeHandler}
						></input>
						<button
							className={styles.mypageNicknameChange}
							onClick={onClickHandler}
						>
							<PencilFill className={styles.mypagePencil} />
						</button>
					</div>
				</div>
				<div className={styles.mypageStar}>
					<span className={styles.mypageStarTitle}>
						내가 남긴 별점 ({starCount})
					</span>
					<ScrollContent content="ratings" starPreview={starPreview} />
				</div>
				<div className={styles.mypageBookmark}>
					<span className={styles.mypageBookmarkTitle}>
						북마크 ({bookmarkCount})
					</span>
					<ScrollContent content="bookmarks" bookPreview={bookPreview} />
				</div>
				<div className={`d-grid gap-2`}>
					<LinkBtn title={"비밀번호 변경"} link={"../member/password/change"} />
				</div>
				<div className={styles.mypageLink}>
					<ConfirmCancelModal
						title="로그아웃"
						ment="로그아웃 하시겠습니까?"
						api={logout}
					/>
					<span>|</span>
					<ConfirmCancelModal
						title="회원탈퇴"
						ment="정말 탈퇴하시겠습니까?"
						api={unregister}
					/>
				</div>
			</div>
		</BackLayout>
	);
}
