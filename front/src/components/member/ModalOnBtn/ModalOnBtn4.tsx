import { useEffect, useState } from "react";
import { Button } from "react-bootstrap";
import ConfirmModal from "../ConfirmModal/ConfirmModal";
import styles from "./ModalOnBtn.module.scss";
import axios from "axios";
import instance from "@/api/interceptors";
import { login } from "@/api/login";

export default function ModalOnBtn4() {
	const [showModal, setShowModal] = useState(false);
	const [imageList, setImageList] = useState([]);
	const [image, setImage] = useState("");

	const onProfileClick = (event) => {
		event.preventDefault();
		let src = event.target.src;
		let imageName = src.substring(src.lastIndexOf("/") + 1);
		setImage(imageName);
		console.log(image);
	};

	const profile = (image: string) => {
		instance
			.put("/api/members/init", {
				imageName: image,
			})
			.then(function (response) {
				console.log(response);
				setShowModal(true);
			})
			.catch(function (error) {
				console.log(error);
				setShowModal(false);
				alert(error.response.data.message);
			});
	};

	useEffect(() => {
		const fetchImage = () => {
			instance
				.get("/api/members/profile/list")
				.then(function (response) {
					const images = response.data.data.map((i) => i.imageUrl);
					setImageList(images);
				})
				.catch(function (error) {
					console.log(error);
				});
		};

		fetchImage();
	}, []);

	return (
		<div className={styles.profileImages}>
			{imageList.map((url, index) => (
				<button key={index} onClick={onProfileClick}>
					<img src={url} alt={`Image ${index}`} />
				</button>
			))}
			<div className={`d-grid gap-2`}>변경하기</div>
		</div>
	);
}
