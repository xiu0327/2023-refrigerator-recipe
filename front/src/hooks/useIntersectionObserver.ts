import { useEffect } from "react";

export const useIntersectionObserver = (page, setPage: Function) => {
	console.log("[intersectionObserver] active");

	useEffect(() => {
		const handleIntersection = (entries: { isIntersecting: any }[]) => {
			if (entries[0].isIntersecting) {
				setPage((prev: number) => prev + 1);
				console.log("[intersectionObserver] I observe end of list");
				console.log("[intersectionObserver] page + 1 :", page);
			}
		};
		const options = { threshold: 1 };

		const observer = new IntersectionObserver(handleIntersection, options);
		const target = document.querySelector("#end-of-list");
		target && observer.observe(target);

		return () => {
			target && observer.unobserve(target);
		};
	}, []);
};
