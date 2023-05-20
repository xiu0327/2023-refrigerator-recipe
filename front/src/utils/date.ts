import moment from "moment";

export const getDday = (remainDays: string) => {
	return remainDays === "0" ? `D-Day` : `D${remainDays}`;
};

export const calcDday = (date: string) => {
	const today = moment();
	const diffDays = moment(date).diff(today, "days");
	return diffDays == 0
		? "오늘"
		: diffDays < 0
		? `D+${diffDays * -1}`
		: `D-${diffDays}`;
};
