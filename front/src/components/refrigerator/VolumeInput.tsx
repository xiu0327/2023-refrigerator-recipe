import { Button, ButtonGroup, Form } from "react-bootstrap";

type volumeInputType = {
	setVolume: Function;
	unit: String;
};

export default function VolumeInput({ setVolume, unit }: volumeInputType) {
	return (
		<div className="d-flex align-items-center gap-3">
			<Form.Control type="number" onChange={(e) => setVolume(e.target.value)} /> {unit}
		</div>
	);
}
