package sample.recipe_service;

import sample.data.ConstDb;
import sample.data.DatabaseHandler;
import sample.data.RecipeHandler;
import sample.recipe_package.Recipe;

import java.util.ArrayList;

public class DatabaseProcess {
    // handlers
    protected DatabaseHandler databaseHandler;

    public DatabaseProcess(){
        databaseHandler = new DatabaseHandler();
    }

    // Recipe
    // Нужен ли var ?
    public ArrayList<Recipe> getRecipes() {
        return databaseHandler.recipeHandler.getRecipes();
    }

    public void addRecipe(Recipe recipe) {
        databaseHandler.recipeHandler.setRecipe(recipe);
    }

    public void deleteRecipe(int id){
        databaseHandler.recipeHandler.deleteRecipes(id);
    }

    public int getLastId() {
        return databaseHandler.recipeHandler.getLastId();
    }

    // User

    public void addUser(String nameUser, String password) {
        databaseHandler.userHandler.addUser(nameUser, password);
    }

    public boolean setUser(String name, String password) {
        return databaseHandler.userHandler.setUser(name, password);
    }

    public boolean findUser(String name, String password) {
        return databaseHandler.userHandler.findUser(name, password);
    }
}
