import { Button, ButtonGroup, Form } from "react-bootstrap";

type dateInputProps = {
	date: Date;
	setDate: Function;
};

export default function DateInput({ date, setDate }: dateInputProps) {
	return (
		<div className="d-flex flex-column gap-2">
			<div className="d-flex gap-2">
				<Form.Control
					type="date"
					value={date.toISOString().substring(0, 10)}
					onChange={(e) => setDate(new Date(e.target.value))}
				/>
				<Button onClick={() => setDate(new Date())}>↪️</Button> {/* input box 안으로! */}
			</div>
			<ButtonGroup>
				{[3, 5, 7, 14].map((day, index) => (
					<Button
						key={index}
						onClick={() => {
							setDate(new Date(date.setDate(date.getDate() + day)));
						}}
					>
						+ {day}일
					</Button>
				))}
			</ButtonGroup>
		</div>
	);
}
