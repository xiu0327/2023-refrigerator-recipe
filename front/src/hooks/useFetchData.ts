import { useEffect, useState } from "react";

export const useFetchData = (
	fetch: Function,
	args: any[],
	dependencies: any[],
) => {
	const [data, setData] = useState(null);

	useEffect(() => {
		const fetchData = async () => {
			try {
				const fetchedData = await fetch(...args);
				setData(fetchedData);
			} catch (error) {
				console.error(error);
			}
		};
		fetchData();
	}, dependencies);

	return data;
};
