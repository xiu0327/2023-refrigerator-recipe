import { Search } from "react-bootstrap-icons";
import Input from "../Input/Input";
import styles from "@/scss/pages.module.scss";

type SearchInputProps = {
	keyword?: string;
	setKeyword?: Function;
	handleSubmit?: Function;
	handleClick?: Function;
	handleSearchBtnClick?: Function;
	placeholder?: string;
	disabled?: boolean;
	focus?: boolean;
};

export default function SearchBar({
	keyword,
	setKeyword,
	handleSubmit,
	handleClick,
	handleSearchBtnClick,
	placeholder,
	disabled,
	focus,
}: SearchInputProps) {
	const onSearchBarClick = () => {
		handleClick && handleClick();
	};

	const onSearchBtnClick = () => {
		handleSearchBtnClick && handleSearchBtnClick();
	};

	const onSearchSubmit = (e) => {
		e.preventDefault();
		handleSubmit && handleSubmit();
	};

	return (
		<form
			className="d-flex align-items-center gap-3"
			onClick={onSearchBarClick}
			onSubmit={(e) => onSearchSubmit(e)}
		>
			<Input
				value={keyword}
				setValue={setKeyword}
				placeholder={placeholder}
				usage="search"
				focus={focus}
				disabled={disabled}
			/>
			<Search className={styles.icon} onClick={onSearchBtnClick} />
		</form>
	);
}
