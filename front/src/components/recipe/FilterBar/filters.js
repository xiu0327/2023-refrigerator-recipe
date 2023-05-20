import {
	getRecipeFoodType,
	getRecipeIngredientCategory,
	getRecipeRecipeType,
	getRecipeDifficulty,
} from "@/api";

export const FILTERS = [
	{ name: "요리 유형", fetchFilterMenuList: getRecipeFoodType },
	{ name: "주재료 유형", fetchFilterMenuList: getRecipeIngredientCategory },
	{ name: "분류", fetchFilterMenuList: getRecipeRecipeType },
	{ name: "난이도", fetchFilterMenuList: getRecipeDifficulty },
	// { name: "조리시간", fetchList: getRecipeCookTime },
	// { name: "별점", fetchList: getRecipeStar },
];
