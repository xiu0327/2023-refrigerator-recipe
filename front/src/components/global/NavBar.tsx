import router from "next/router";

export default function NavBar() {
	const navItems = [
		{
			icon: "/images/placeholder.png",
			label: "냉장고",
			route: "/refrigerator",
		},
		{ icon: "/images/placeholder.png", label: "레시피", route: "/recipe" },
		{ icon: "/images/placeholder.png", label: "북마크", route: "/bookmark" },
	];

	return (
		<div className="d-flex">
			{navItems.map((item, index) => (
				<div
					key={index}
					className="py-2 gap-2 d-flex flex-grow-1 flex-column bg-primary"
					onClick={() => router.replace(item.route)}
				>
					<div className="d-flex justify-content-center">
						<img src={item.icon} style={{ height: 24, width: 24 }} />
					</div>
					<div className="text-center" style={{ fontSize: 14 }}>
						{item.label}
					</div>
				</div>
			))}
		</div>
	);
}
