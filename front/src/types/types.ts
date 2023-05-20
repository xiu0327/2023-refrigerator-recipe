export type IngredientInRecipe = {
	ingredientID: number;
	name: string;
	type: string;
	volume: string;
};

export type RecipePreview = {
	recipeID: number;
	recipeName: string;
	image: string;
	scoreAvg: number;
	views: number;
};

export type RecipeDetail = RecipePreview & {
	description: string;
	cookingTime: string;
	kcal: string;
	servings: string;
	difficulty: string;
	bookmarks: number;
	recipeFoodTypeName: string;
	recipeCategoryName: string;
	ingredients: Array<IngredientInRecipe>;
};

export type RecipeSteps = {
	step: string;
	explanation: string;
	image: string;
};

export type RecipeComment = {
	commentId: number;
	nickname: string;
	heart: number;
	date: string;
	modifiedState: boolean;
	content: string;
};

export type Bookmark = {
	bookmarkId: number;
	recipeId: number;
	recipeImage: string;
	recipeName: string;
	scoreAvg: number;
	views: number;
};
