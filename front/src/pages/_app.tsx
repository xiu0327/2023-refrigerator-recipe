import "@/styles/globals.css";
import type { AppProps } from "next/app";
import "bootstrap/dist/css/bootstrap.min.css";
import { useEffect, useState } from "react";
import { reissueAccessToken } from "@/api/reissueAccessToken";
import { silentRefresh } from "@/api/login";

export default function App({ Component, pageProps }: AppProps) {
	const [refreshTrigger, setRefreshTrigger] = useState(false);

	useEffect(() => {
		(async () => {
			await silentRefresh();
			setRefreshTrigger(true);
		})();
	}, []);

	return <>{refreshTrigger && <Component {...pageProps} />}</>;
}
