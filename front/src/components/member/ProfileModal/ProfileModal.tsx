import InputContent from "@/components/member/InputContent/InputContent";

import instance from "@/api/interceptors";
import styles from "./ProfileModal.module.scss";
import { useEffect, useState } from "react";
import { getProfileList } from "@/api/getProfileList";
import { login } from "@/api/login";
import ModalOnBtn3 from "@/components/member/ModalOnBtn/ModalOnBtn3";
import { Button, Modal } from "react-bootstrap";
import { putProfile } from "@/api/putProfile";

export default function ProfileModal({ on }) {
	const [image, setImage] = useState("");
	const [show, setShow] = useState(on);
	const [imageList, setImageList] = useState([]);

	const handleClose = () => setShow(false);
	//const handleShow = () => setShow(true);

	const onProfileClick = (event) => {
		let src = event.target.src;
		let imageName = src.substring(src.lastIndexOf("/") + 1);
		console.log(imageName);
		setImage(imageName);
		console.log(image);
		putProfile(image);
	};

	useEffect(() => {
		const fetchImage = async () => {
			try {
				const images = await getProfileList();
				if (images) {
					setImageList(images);
				}
			} catch (error) {
				console.log("프로필 이미지 접근 에러:", error);
			}
		};
		fetchImage();
	}, []);

	return (
		<>
			<Modal show={show} onHide={handleClose}>
				<Modal.Header closeButton>
					<Modal.Title>프로필 변경</Modal.Title>
				</Modal.Header>
				<Modal.Body className={styles.profileImages}>
					{" "}
					{imageList.map((url, index) => (
						<button key={index} onClick={onProfileClick}>
							<img src={url} alt={`Image ${index}`} />
						</button>
					))}
				</Modal.Body>
				{/* <Modal.Footer>
					<Button variant="secondary" onClick={handleClose}>
						취소
					</Button>
					<Button variant="primary" onClick={handleClose}>
						변경하기
					</Button>
				</Modal.Footer> */}
			</Modal>
		</>
		// <form className={styles.profileContainer}>
		// 	<span className={styles.profileTitle}>프로필 설정</span>
		// 	<div className={styles.profileImages}>
		// {imageList.map((url, index) => (
		// 	<button key={index} onClick={onProfileClick}>
		// 		<img src={url} alt={`Image ${index}`} />
		// 	</button>
		// ))}
		// 	</div>
		// 	<div className={`d-grid gap-2`}>
		// 		<ModalOnBtn3 title="설정하기" ment="설정" image={image} />
		// 	</div>
		// </form>
	);
}
