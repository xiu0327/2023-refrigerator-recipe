import { useEffect, useState } from "react";
import router from "next/router";
import { Search } from "react-bootstrap-icons";

import { getIngredients } from "@/api";
import { IngredientBrief } from "@/types";

import AppNavLayout from "@/components/layout/AppNavLayout";
import StorageTab from "@/components/refrigerator/StorageTab/StorageTab";
import IngredientGrid from "@/components/refrigerator/IngredientGrid/IngredientGrid";
import Switch from "@/components/global/Switch/Switch";

import styles from "@/scss/pages.module.scss";

export default function RefrigeratorPage() {
	const [ingredientData, setIngredientData] = useState<IngredientBrief[]>([]);
	const [page, setPage] = useState(0);
	const [isScrollEnd, setIsScrollEnd] = useState(false);

	const [storage, setStorage] = useState<string>("냉장");
	const [isExpired, setIsExpired] = useState<boolean>(false);

	// TODO: 스크롤에 따른 페이지 데이터 순서대로 안 받아짐 (1->0->1)

	useEffect(() => {
		setIngredientData([]);
		setPage(0);
		// setIsScrollEnd(false);
		console.log("change the value of storage or isExpired");
	}, [storage, isExpired]);

	useEffect(() => {
		const fetchData = async () => {
			try {
				const data = await getIngredients(page, storage, isExpired);
				data.length !== 0
					? setIngredientData((prev) => [...prev, ...data])
					: setIsScrollEnd(true);
			} catch (error) {
				console.log(error);
			}
		};
		fetchData();
	}, [page]);

	// useEffect(() => {
	// 	const handleIntersection = (entries: { isIntersecting: any }[]) => {
	// 		if (!isScrollEnd && entries[0].isIntersecting) {
	// 			setPage((prev: number) => prev + 1);
	// 			console.log("i find the end of list");
	// 		}
	// 	};
	// 	const options = { threshold: 1 };

	// 	const observer = new IntersectionObserver(handleIntersection, options);
	// 	const target = document.querySelector("#end-of-list");
	// 	target && observer.observe(target);

	// 	return () => {
	// 		target && observer.unobserve(target);
	// 	};
	// }, []);

	// useIntersectionObserver(page, setPage);

	const onSearchBtnClick = () => {
		router.push("/refrigerator/search");
		// console.log("now page :", page);
	};

	return (
		<AppNavLayout title="냉장고">
			<div className={styles.fixed}>
				<div className="d-flex align-items-center gap-3">
					<StorageTab storage={storage} setStorage={setStorage} size="sm" />
					<Search className={styles.icon} onClick={onSearchBtnClick} />
				</div>
				<Switch
					label="소비기한 지난 식재료만 보기"
					isOn={isExpired}
					setIsOn={setIsExpired}
				/>
			</div>

			<div style={{ marginTop: "90px" }}>
				<IngredientGrid ingredientData={ingredientData} />
				<div id="end-of-list"></div>
			</div>
		</AppNavLayout>
	);
}
