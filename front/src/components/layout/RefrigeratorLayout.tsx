import router from "next/router";
import { Button } from "react-bootstrap";
import AppBar from "../global/AppBar";
import NavBar from "../global/NavBar";

type layoutProps = {
	children: JSX.Element;
};

export default function RefrigeratorLayout({ children }: layoutProps) {
	return (
		<div className="d-flex flex-column" style={{ height: "100vh" }}>
			<AppBar label="냉장고">
				<div className="d-flex flex-fill justify-content-between">
					<Button>🧑🏻</Button>
					<div className="d-flex gap-1">
						<Button>🔔</Button>
						<Button onClick={() => router.push("/refrigerator/ingredient/add/name")}>➕</Button>
					</div>
				</div>
			</AppBar>

			<div className="d-flex flex-column flex-fill">{children}</div>

			<NavBar />
		</div>
	);
}
