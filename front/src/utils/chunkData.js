export default function chunkData(data, size) {
	const array = [];
	for (let i = 0; i < data.length; i += size) {
		array.push(data.slice(i, i + size));
	}
	return array;
}
