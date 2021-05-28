package sample.app_service;

import sample.User;
import sample.data.DatabaseHandler;
import sample.recipe_package.Recipe;

import java.util.ArrayList;

class DatabaseProcess {
    // handlers
    protected DatabaseHandler databaseHandler;

    public DatabaseProcess(){
        databaseHandler = new DatabaseHandler();
    }

    // Recipe
    // Нужен ли var ?
    public ArrayList<Recipe> getRecipesUser(int id) {
        return databaseHandler.getRecipeHandler().getRecipesUser(id);
    }

    public Recipe getRecipe(int idR){
        return databaseHandler.getRecipeHandler().getRecipe(idR);
    }

    public void addRecipe(Recipe recipe, int id) {
        databaseHandler.getRecipeHandler().addRecipe(recipe, id);
    }

    public void deleteRecipe(int id){
        databaseHandler.getRecipeHandler().deleteRecipes(id);
    }

    public int getLastId() {
        return databaseHandler.getRecipeHandler().getLastId();
    }

    public boolean findRecipe(int idR){
        return databaseHandler.getRecipeHandler().findRecipe(idR);
    }

    // User

    public void addUser(String nameUser, String password) {
        databaseHandler.getUserHandler().addUser(nameUser, password);
    }

    public User getUser(String name, String password) {
        return databaseHandler.getUserHandler().getUser(name, password);
    }

    public boolean findUser(String name, String password) {
        return databaseHandler.getUserHandler().findUser(name, password);
    }

}
