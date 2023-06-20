const rewrites = async () => {
	return [
		{
			source: "/:path*",
			destination: "http://118.67.134.32:8080/:path*",
		},
	];
};

module.exports = { rewrites };
