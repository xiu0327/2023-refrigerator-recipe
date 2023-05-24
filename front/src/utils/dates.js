import moment from "moment";

export const toYMDString = (date) => {
	const options = { year: "numeric", month: "2-digit", day: "2-digit" };
	const dateStr = date.toLocaleString("ko-KR", options);
	const dateArr = [
		dateStr.slice(0, 4),
		dateStr.slice(6, 8),
		dateStr.slice(10, 12),
	];
	return dateArr.join("-");
};

export const toCreatedDate = (date) => {
	const [year, month, day] = [
		date.slice(0, 4),
		date.slice(5, 7),
		date.slice(8, 10),
	];
	return `${year}년 ${month}월 ${day}일 등록`;
};

export const getRemainDays = (date) => {
	const today = moment();
	const diffDays = date.diff(today, "days");
	return diffDays == 0
		? "오늘"
		: diffDays < 0
		? `D+${diffDays * -1}`
		: `D-${diffDays}`;
};
