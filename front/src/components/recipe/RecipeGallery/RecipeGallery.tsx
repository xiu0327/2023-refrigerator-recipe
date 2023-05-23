import router from "next/router";
import { EyeFill, StarFill } from "react-bootstrap-icons";
import { RecipeBrief, Bookmark } from "@/types";
import Stars from "../Stars/Stars";
import styles from "./RecipeGallery.module.scss";

type RecipeGalleryProps = {
	recipeData: RecipeBrief[] | Bookmark[];
};

export default function RecipeGallery({ recipeData }: RecipeGalleryProps) {
	const onRecipeClick = (recipeID: number) => {
		router.push(`/recipe/info?recipeID=${recipeID}`);
	};

	return (
		<div className={styles.recipeGalleryContainer}>
			{recipeData.map((recipe) => (
				<div
					key={recipe.recipeID}
					className={styles.recipeContainer}
					onClick={() => onRecipeClick(recipe.recipeID)}
				>
					<img src={recipe.image} />
					<div>
						<span>{recipe.recipeName}</span>
						<div>
							<div>
								<EyeFill width="18" height="18" />
								{recipe.views}
							</div>
							{recipe.scoreAvg != 0 && (
								<div>
									<StarFill width="14" height="14" />
									{recipe.scoreAvg}
								</div>
							)}
						</div>
					</div>
				</div>
			))}
		</div>
	);
}
