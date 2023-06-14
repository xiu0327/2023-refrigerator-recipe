import { createSlice } from "@reduxjs/toolkit";
import { Storage } from "@/types";

interface IngredientSettingsState {
	storage: Storage;
	isExpired: boolean;
}

const initialState: IngredientSettingsState = {
	storage: "냉장",
	isExpired: false,
};

const counterSlice = createSlice({
	name: "ingredientSettings",
	initialState,
	reducers: {
		setStorage: (state, action) => {
			state.storage = action.payload;
		},
		setIsExpired: (state, action) => {
			state.isExpired = action.payload;
		},
	},
});

export const { setStorage, setIsExpired } = counterSlice.actions;
export default counterSlice.reducer;
