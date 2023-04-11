import { Button } from "react-bootstrap";
import AppBar from "../global/AppBar";
import router from "next/router";

type layoutProps = {
	children: JSX.Element;
};

export default function SearchIngredientLayout({ children }: layoutProps) {
	return (
		<div className="d-flex flex-column" style={{ height: "100vh" }}>
			<AppBar>
				<Button onClick={() => router.back()}>🔙</Button>
			</AppBar>
			<div className="d-flex flex-column flex-fill">{children}</div>
		</div>
	);
}
