package sample.app_service;

import sample.User;
import sample.data.DatabaseHandler;
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
    public ArrayList<Recipe> getRecipes(int id) {
        return databaseHandler.recipeHandler.getRecipes(id);
    }

    public void addRecipe(Recipe recipe, int id) {
        databaseHandler.recipeHandler.addRecipe(recipe, id);
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

    public User getUser(String name, String password) {
        return databaseHandler.userHandler.getUser(name, password);
    }

    public boolean findUser(String name, String password) {
        return databaseHandler.userHandler.findUser(name, password);
    }
}
