import { configureStore } from "@reduxjs/toolkit";
import ingredientData from "./refrigerator/ingredientData";
import ingredientInfo from "./refrigerator/ingredientInfo";

export const store = configureStore({
	reducer: {
		ingredientData,
		ingredientInfo,
	},
});
