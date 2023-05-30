import styles from "./notification.module.scss";
import {
	HandThumbsUpFill,
	CupStraw,
	ExclamationTriangleFill,
} from "react-bootstrap-icons";

export default function Notification() {
	const STATE = [
		{
			title: "유통기한",
			icon: <ExclamationTriangleFill />,
		},
		{
			title: "좋아요",
			icon: <HandThumbsUpFill />,
		},
		{
			title: "식재료",
			icon: <CupStraw />,
		},
	];
	return (
		<div className={styles.noficiationContainer}>
			<div className={styles.notificationTitle}>알림</div>
			{/* 클릭 시 유통기한 하루 남은 식재료 리스트 보여주기 */}
			<div className={styles.notificationList}>
				<span className={styles.icon}>{STATE[0].icon}</span>
				<span className={styles.ment}>
					<span className={styles.state}>[{STATE[0].title}]</span>
					냉장고에 사과 외 8개의 식재료 소비기한이 1일 남았어요.
				</span>
			</div>
			{/* 클릭 시 유통기한 3일 남은 식재료 리스트 보여주기 */}
			<div className={styles.notificationList}>
				<span className={styles.icon}>{STATE[0].icon}</span>
				<span className={styles.ment}>
					<span className={styles.state}>[{STATE[0].title}]</span>
					냉장고에 상추 외 3개의 식재료 소비기한이 3일 남았어요.
				</span>
			</div>
			{/* 클릭 시 댓글 화면으로 넘어가기 */}
			<div className={styles.notificationDays}>
				<div className={styles.notificationDay}>
					<span>오늘</span>
				</div>
				<div className={styles.notificationList}>
					<span className={styles.icon}>{STATE[1].icon}</span>
					<span className={styles.ment}>
						<span className={styles.state}>[{STATE[1].title}]</span>
						'이냉장'님이 회원님의 댓글을 좋아해요.
						<span className={styles.day}>1시간 전</span>
					</span>
				</div>
			</div>
			{/* 클릭 시 식재료 등록 화면으로 넘어가기 */}
			<div className={styles.notificationDays}>
				<div className={styles.notificationDay}>
					<span>어제</span>
				</div>
				<div className={styles.notificationList}>
					<span className={styles.icon}>{STATE[2].icon}</span>
					<span className={styles.ment}>
						<span className={styles.state}>[{STATE[2].title}]</span>
						'당근' 친구가 새롭게 등록 되었어요.
					</span>
				</div>
			</div>
			<div className={styles.notificationDays}>
				<div className={styles.notificationDay}>
					<span>이번 주</span>
				</div>
				<div className={styles.notificationList}>
					<span className={styles.icon}>{STATE[2].icon}</span>
					<span className={styles.ment}>
						<span className={styles.state}>[{STATE[2].title}]</span>
						'오이' 친구가 새롭게 등록 되었어요.
						<span className={styles.day}>3일전</span>
					</span>
				</div>
				<div className={styles.notificationList}>
					<span className={styles.icon}>{STATE[1].icon}</span>
					<span className={styles.ment}>
						<span className={styles.state}>[{STATE[1].title}]</span>
						'김모씨'님이 회원님의 댓글을 좋아해요.
						<span className={styles.day}>4일전</span>
					</span>
				</div>
			</div>
			<div className={styles.notificationDays}>
				<div className={styles.notificationDay}>
					<span>지난 주</span>
				</div>
				<div className={styles.notificationList}>
					<span className={styles.icon}>{STATE[2].icon}</span>
					<span className={styles.ment}>
						<span className={styles.state}>[{STATE[2].title}]</span>
						'가지' 친구가 새롭게 등록 되었어요.
						<span className={styles.day}>12일전</span>
					</span>
				</div>
			</div>
		</div>
	);
}
// 각 리스트 클릭 시 링크 연결되게 만들기
