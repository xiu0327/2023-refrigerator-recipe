type ingredientListType = {
	ingredientData: Array<String>;
	selectIngredient: Function;
	selectedIngredient: Number;
};

export default function IngredientList({
	ingredientData,
	selectedIngredient,
	selectIngredient,
}: ingredientListType) {
	return (
		<div>
			{ingredientData.map((ingredientName, index) => (
				<div key={index} className="px-2" onClick={() => selectIngredient(index)}>
					<div className="d-flex">
						<div className="d-flex flex-fill">{ingredientName}</div>
						<img
							src="/images/placeholder.png"
							style={{
								width: 18,
								height: 18,
								backgroundColor: index == selectedIngredient ? "skyblue" : "white",
							}}
						/>
					</div>
					<hr style={{ height: 1 }} />
				</div>
			))}
		</div>
	);
}
