import Tab from "react-bootstrap/Tab";
import Tabs from "react-bootstrap/Tabs";

type propsType = {
	setStorage: Function;
};

export default function StorageTab({ setStorage }: propsType) {
	const labels = ["냉동", "냉장", "실온", "조미료"];

	return (
		<Tabs
			className="flex-grow-1"
			defaultActiveKey={0}
			id="tabs"
			transition={false}
			justify
			onSelect={(eventKey) => {
				setStorage(eventKey);
			}}
		>
			{labels.map((label, index) => (
				<Tab key={index} eventKey={index} title={label} />
			))}
		</Tabs>
	);
}
