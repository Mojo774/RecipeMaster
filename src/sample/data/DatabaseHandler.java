package sample.data;

import java.sql.SQLException;

public class DatabaseHandler {

    // Handlers
    private Recipe_has_ingredientHandler recipe_has_ingredientHandler;
    private IngredientHandler ingredientHandler;
    private RecipeHandler recipeHandler;
    private UserHandler userHandler;

    public DatabaseHandler() {

        recipe_has_ingredientHandler = new Recipe_has_ingredientHandler(this);
        ingredientHandler = new IngredientHandler(this);
        recipeHandler = new RecipeHandler(this);
        userHandler = new UserHandler(this);


    }

    // удаляет поля во всех таблицах кроме юзера, и сбрасывает инкремент
    public void clear() {

        try {
            ingredientHandler.deleteIngredients();
            recipeHandler.deleteRecipes();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

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

    // static
    public void closeConnection() {
        DatabaseConnector.closeConnection();
    }
}
