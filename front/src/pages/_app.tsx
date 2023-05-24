import "@/styles/globals.css";
import type { AppProps } from "next/app";
import "bootstrap/dist/css/bootstrap.min.css";
import Head from "next/head";

export default function App({ Component, pageProps }: AppProps) {
	return (
		<>
			<Head>
				<title>냉장고를 부탁해</title>
			</Head>
			<Component {...pageProps} />
		</>
	);
}
