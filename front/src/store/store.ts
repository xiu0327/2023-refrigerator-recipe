import { configureStore } from "@reduxjs/toolkit";
import ingredientSettings from "./refrigerator/ingredientSettings";

export const store = configureStore({
	reducer: {
		ingredientSettings,
	},
});
