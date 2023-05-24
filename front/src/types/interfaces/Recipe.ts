export interface RecipeBrief {
	recipeID: number;
	recipeName: string;
	image: string;
	scoreAvg: number;
	views: number;
}

export interface RecipeDetail extends RecipeBrief {
	description: string;
	cookingTime: string;
	kcal: string;
	servings: string;
	difficulty: string;
	bookmarks: number;
	recipeFoodTypeName: string;
	recipeCategoryName: string;
	ingredients: RecipeIngredient[];
}

export interface RecipeIngredient {
	ingredientID: number;
	name: string;
	type: string;
	volume: string;
}

export interface RecipeStep {
	step: string;
	explanation: string;
	image: string;
}

export interface RecipeComment {
	commentID: number;
	nickname: string;
	content: string;
	heart: number;
	date: string;
	modifiedState: boolean;
	isMyComment: boolean;
}

export interface Bookmark extends RecipeBrief {
	bookmarkID: number;
}
