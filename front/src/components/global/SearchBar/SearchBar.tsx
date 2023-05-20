import { Search } from "react-bootstrap-icons";
import Input from "../Input/Input";
import styles from "@/scss/pages.module.scss";

type SearchInputProps = {
	keyword?: string;
	setKeyword?: Function;
	onClick?: Function;
	placeholder?: string;
	disabled?: boolean;
	focus?: boolean;
};

export default function SearchBar({
	keyword,
	setKeyword,
	onClick,
	placeholder,
	disabled,
	focus,
}: SearchInputProps) {
	const onSearchBarClick = () => {
		onClick && onClick();
	};

	return (
		<div className="d-flex align-items-center gap-3" onClick={onSearchBarClick}>
			<Input
				value={keyword}
				setValue={setKeyword}
				placeholder={placeholder}
				usage="search"
				focus={focus}
				disabled={disabled}
			/>
			<Search className={styles.icon} />
		</div>
	);
}
