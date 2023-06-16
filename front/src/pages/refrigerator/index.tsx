import { useEffect, useRef, useState } from "react";
import { Search } from "react-bootstrap-icons";

import { getIngredients } from "@/api";
import { IngredientBrief, Storage } from "@/types";

import AppNavLayout from "@/components/layout/AppNavLayout";
import StorageTab from "@/components/refrigerator/StorageTab/StorageTab";
import IngredientGrid from "@/components/refrigerator/IngredientGrid/IngredientGrid";
import Switch from "@/components/global/Switch/Switch";

import styles from "@/scss/pages.module.scss";
import { useIntersectionObserver } from "@/hooks";
import Link from "next/link";
import { useDispatch, useSelector } from "react-redux";
import {
	setIsExpired,
	setStorage,
} from "@/store/refrigerator/ingredientSettings";

export default function RefrigeratorPage() {
	const [ingredientData, setIngredientData] = useState<IngredientBrief[]>([]);

	const [page, setPage] = useState(0);
	const [isDataLoaded, setIsDataLoaded] = useState(false);
	const [isScrollEnd, setIsScrollEnd] = useState(false);

	const scrollRef = useRef<HTMLDivElement>(null);

	const storage = useSelector(
		({ ingredientSettings }) => ingredientSettings.storage,
	);
	const storageDispatch = useDispatch();
	const changeStorage = (payload: Storage) =>
		storageDispatch(setStorage(payload));

	const isExpired = useSelector(
		({ ingredientSettings }) => ingredientSettings.isExpired,
	);
	const isExpiredDispatch = useDispatch();
	const toggleIsExpired = (payload: boolean) =>
		storageDispatch(setIsExpired(payload));

	useEffect(() => {
		(async () => {
			setPage(0);
			const data = await getIngredients(0, storage, isExpired);
			setIngredientData(data);
			setIsScrollEnd(false);
			setIsDataLoaded(true);

			scrollRef.current && (scrollRef.current.scrollTop = 0);
		})();
	}, [storage, isExpired]);

	useEffect(() => {
		(async () => {
			if (page != 0 && !isScrollEnd) {
				const data = await getIngredients(page, storage, isExpired);
				data.length !== 0
					? setIngredientData((prev) => [...prev, ...data])
					: setIsScrollEnd(true);
			}
		})();
	}, [page]);

	useIntersectionObserver(setPage, isDataLoaded);

	return (
		<AppNavLayout title="냉장고">
			<div className={styles.fixedContainer}>
				<div className="d-flex align-items-center gap-3">
					<StorageTab storage={storage} setStorage={changeStorage} size="sm" />
					<Link href={`/refrigerator/search`}>
						<Search className={styles.icon} />
					</Link>
				</div>
				<Switch
					label="유통기한 지난 식재료만 보기"
					isOn={isExpired}
					setIsOn={toggleIsExpired}
				/>
			</div>

			<div
				id="scroll-area"
				style={{ marginTop: "90px", height: `calc(100vh - 205px)` }}
				className={styles.scrollContainer}
				ref={scrollRef}
			>
				<IngredientGrid ingredientData={ingredientData} />
				{isDataLoaded && <div id="end-of-list" />}
			</div>
		</AppNavLayout>
	);
}
