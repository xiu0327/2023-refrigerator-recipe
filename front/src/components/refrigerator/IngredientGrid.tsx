import router from "next/router";
import chunk from "../../utils/chunkData";

type ingredientType = {
	name: String;
	remainDays: Number;
};

type ingredientDataProps = {
	ingredientData: Array<ingredientType>;
};

type ingredientChunkProps = {
	ingredientChunk: Array<ingredientType>;
};

type ingredientProps = {
	ingredient: ingredientType;
};

export default function IngredientGrid({ ingredientData }: ingredientDataProps) {
	const ingredientChunks: Array<Array<ingredientType>> = chunk(ingredientData, 3);

	return (
		<div className="flex-column">
			{ingredientChunks.map((ingredientChunk, index) => (
				<IngredientRow key={index} ingredientChunk={ingredientChunk} />
			))}
		</div>
	);
}

function IngredientRow({ ingredientChunk }: ingredientChunkProps) {
	// 식재료 요소 정렬 구현
	return (
		<div className="m-4 d-flex justify-content-between">
			{ingredientChunk.map((ingredient, index) => (
				<Ingredient key={index} ingredient={ingredient} />
			))}
		</div>
	);
}

function Ingredient({ ingredient }: ingredientProps) {
	return (
		<div onClick={() => router.push("/refrigerator/ingredient/info")}>
			<img src="/images/ingredient.png" />
			<div className="pt-3 text-center">
				<div>{ingredient?.name}</div>
				<div>D-{String(ingredient?.remainDays)}</div>
			</div>
		</div>
	);
}
