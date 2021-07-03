package sample.data;

public class DatabaseController {

    // Handlers
    private Recipe_has_ingredientHandler recipe_has_ingredientHandler = new Recipe_has_ingredientHandler(this);
    private IngredientHandler ingredientHandler = new IngredientHandler(this);
    private RecipeHandler recipeHandler = new RecipeHandler(this);
    private UserHandler userHandler = new UserHandler(this);


    public Recipe_has_ingredientHandler getRecipe_has_ingredientHandler() {
        return recipe_has_ingredientHandler;
    }

    public IngredientHandler getIngredientHandler() {
        return ingredientHandler;
    }

    public RecipeHandler getRecipeHandler() {
        return recipeHandler;
    }

    public UserHandler getUserHandler() {
        return userHandler;
    }

}
