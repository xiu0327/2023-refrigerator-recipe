type appbarProps = {
	label?: String;
	children: JSX.Element;
};

export default function AppBar({ label, children }: appbarProps) {
	return (
		<div
			className="p-2 d-flex justify-content-between align-items-center"
			style={{ height: "60px" }}
		>
			<div
				style={{
					position: "absolute",
					left: "50%",
					translate: "-50%",
				}}
			>
				{label}
			</div>
			{children}
		</div>
	);
}
