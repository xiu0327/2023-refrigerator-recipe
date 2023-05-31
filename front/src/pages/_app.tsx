import "@/styles/globals.css";
import type { AppProps } from "next/app";
import "bootstrap/dist/css/bootstrap.min.css";
import Head from "next/head";
import { useEffect, useState } from "react";
import { silentRefresh } from "@/api/login";

export default function App({ Component, pageProps }: AppProps) {
	const [refreshTrigger, setRefreshTrigger] = useState(false);

	useEffect(() => {
		(async () => {
			await silentRefresh();
			setRefreshTrigger(true);
		})();
	}, []);

	return (
		<>
			<Head>
				<title>냉장고를 부탁해</title>
			</Head>
			{refreshTrigger && <Component {...pageProps} />}
		</>
	);
}
