import { configureStore } from "@reduxjs/toolkit";
import ingredientData from "./refrigerator/ingredientData";

export const store = configureStore({
	reducer: {
		ingredientData,
	},
});
