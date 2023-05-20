import styles from "./styles.module.scss";
import { PencilFill } from "react-bootstrap-icons";
import ConfirmCancelModal from "@/components/member/ConfirmCancelModal/ConfirmCancelModal";
import LinkBtn from "@/components/member/LinkBtn/LinkBtn";
import ScrollContent from "@/components/member/ScrollContent/ScrollContent";
import Link from "next/link";
import { Button } from "react-bootstrap";
import { useEffect, useState } from "react";
import instance from "@/api/interceptors";
import ProfileModal from "@/components/member/ProfileModal/ProfileModal";
import { getProfile } from "@/api/getProfile";
import { login } from "@/api/login";

export default function Mypage(props: any) {
	//login("member@naver.com", "member123!");
	const [nick, setNick] = useState("닉네임");
	const [image, setImage] = useState("");
	const [bookmarkPreview, setBookmarkPreview] = useState<Array<object>>([]);
	const [scorePreview, setScorePreview] = useState<Array<object>>([]);
	const [recipe, setRecipe] = useState<Array<object>>([]);
	const [isModalOpen, setIsModalOpen] = useState(false);

	const openModal = () => {
		setIsModalOpen(true);
	};

	const closeModal = () => {
		setIsModalOpen(false);
	};

	useEffect(() => {
		const fetchNick = async () => {
			try {
				const response = await getProfile();
				const existingNick =
					response !== undefined ? response.data.nickname : "";
				setNick(existingNick);
				const existingImage =
					response !== undefined ? response.data.profileImage : "";
				setImage(existingImage);
			} catch (error) {}
		}; // 닉네임 가져오기

		// const fetchBookmark = async () => {
		// 	try {
		// 		const response = await getBookmark();
		// 	} catch (error) {
		// 		console.log(error);
		// 	}
		// };

		// const fetchStarPreview = async () => {
		// 	try {
		// 		const response = await getScorePreview();
		// 	} catch (error) {
		// 		console.log(error);
		// 	}
		// };

		const getScorePreview = () => {
			const url = `/api/my-score/preview?size=5`;
			instance
				.get(url)
				.then((response) => {
					setScorePreview(response.data.data);
					console.log(typeof scorePreview);
				})
				.catch((error) => {
					console.log(error);
				});
		}; // 별점 미리보기

		const getRecipeList = () => {
			const url = `/api/recipe?page=0`;
			instance
				.get(url)
				.then((response) => {
					setRecipe(response.data.data);
					//console.log(recipe);
				})
				.catch((error) => {
					console.log(error);
				});
		}; // 레시피

		const getBookmarkPreview = () => {
			const url = `/api/my-bookmark/preview?size=5`;
			instance
				.get(url)
				.then((response) => {
					console.log(response);
				})
				.catch((error) => {
					console.log(error);
				});
		}; // 북마크 미리보기

		fetchNick();
		//getRecipeList();
		//getBookmarkPreview();
	}, []); // 개인 정보 조회 받아오기
	const onChangeHandler = (e: any) => {
		setNick(e.target.value);
	};
	const onClickHandler = () => {
		postNickname(nick);
	};
	const postNickname = (nick: any) => {
		instance.put("/api/members/nickname", {
			nickname: nick,
		});
	}; // 닉네임 변경
	return (
		<div className={styles.mypageContainer}>
			<span className={styles.mypageTitle}>마이페이지</span>
			<div className={styles.mypageMe}>
				<img className={styles.mypageImg} src={image} onClick={openModal} />
				{isModalOpen && (
					<div onClick={closeModal}>
						<div>
							<ProfileModal on={true} />
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
					내가 남긴 별점 ({scorePreview.length})
				</span>
				<ScrollContent content="star" scorePreview={scorePreview} />
			</div>
			<div className={styles.mypageBookmark}>
				<span className={styles.mypageBookmarkTitle}>
					북마크 ({bookmarkPreview.length})
				</span>
				<ScrollContent content="bookmark" bookmarkPreview={bookmarkPreview} />
			</div>
			<div className={`d-grid gap-2`}>
				<LinkBtn title={"공지사항"} link={"../announcement"} />
				<LinkBtn title={"비밀번호 변경"} link={"../member/password/change"} />
			</div>
			<div className={styles.mypageLink}>
				<ConfirmCancelModal title="로그아웃" ment="로그아웃 하시겠습니까?" />
				<span>|</span>
				<Link legacyBehavior href="../member/unregister">
					<Button className={styles.mypageUnregister} variant="primary">
						회원탈퇴
					</Button>
				</Link>
			</div>
		</div>
	);
}
