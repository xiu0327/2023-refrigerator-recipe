import "@/styles/globals.css";
import type { AppProps } from "next/app";
import "bootstrap/dist/css/bootstrap.min.css";
import { useEffect } from "react";
import { reissueAccessToken } from "@/api/reissueAccessToken";

export default function App({ Component, pageProps }: AppProps) {
	useEffect(() => {
		reissueAccessToken();
	}, [Component]);

	return <Component {...pageProps} />;
}
