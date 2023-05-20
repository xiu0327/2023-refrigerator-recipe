export interface IngredientBrief {
	ingredientID: number;
	name: string;
	remainDays: string;
	image: string;
	phoneme?: string;
}

export interface IngredientDetail extends IngredientBrief {
	storage: "냉장" | "냉동" | "실온" | "조미료";
	expirationDate: string;
	registrationDate: string;
	volume: number;
	unit: string;
}
