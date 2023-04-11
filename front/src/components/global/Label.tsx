type propsType = {
	label: String;
	sub?: String;
	children?: JSX.Element;
};

export default function Label({ label, sub, children }: propsType) {
	return (
		<div className="d-flex flex-column gap-2">
			<div className="d-flex">
				<div className="flex-fill">{label}</div>
				<div>{sub}</div>
			</div>
			{children}
		</div>
	);
}
