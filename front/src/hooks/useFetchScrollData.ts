import { useEffect, useState } from "react";

export const useFetchScrollData = (
	fetch: Function,
	args: any[],
	setIsScrollEnd: Function,
	dependencies: any[],
) => {
	const [data, setData] = useState([]);

	console.log("[fetchData] active");

	useEffect(() => {
		console.log("[fetchData async] page :", dependencies);

		const fetchData = async () => {
			try {
				const fetchedData = await fetch(...args);
				fetchedData.length !== 0
					? setData((prev) => [...prev, ...fetchedData])
					: setIsScrollEnd(true);
			} catch (error) {
				console.log(error);
			}
		};
		fetchData();
	}, dependencies);

	return data;
};
