import { useEffect, useState } from "react";
import { getBookmarks } from "@/api";

import { Bookmark } from "@/types";

import AppNavLayout from "@/components/layout/AppNavLayout";
import RecipeGallery from "@/components/recipe/RecipeGallery/RecipeGallery";

export default function BookmarkPage() {
	const [page, setPage] = useState(0);
	const [bookmarkData, setBookmarkData] = useState<Bookmark[]>([]);

	useEffect(() => {
		(async () => {
			const data = await getBookmarks(page);
			setBookmarkData((prev) => [...prev, ...data]);
		})();
	}, [page]);

	useEffect(() => {
		const observer = new IntersectionObserver(
			(entries) => {
				const firstEntry = entries[0];
				firstEntry.isIntersecting && setPage((prev) => prev + 1);
			},
			{ threshold: 1 },
		);
		observer.observe(document.querySelector("#end-of-list"));
		return () => observer.disconnect();
	}, []);

	return (
		<AppNavLayout title="북마크">
			<RecipeGallery recipeData={bookmarkData} />
			<div id="end-of-list"></div>
		</AppNavLayout>
	);
}
