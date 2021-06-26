package sample.model;

import sample.Main;
import sample.User;
import sample.controller.Controllers;
import sample.data.DatabaseHandler;
import sample.recipe_package.Recipe;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

class DatabaseProcess {
    // handlers
    protected DatabaseHandler databaseHandler;

    // Logger
    private static final Logger logger = Logger.getLogger(DatabaseProcess.class.getName());
    static {
        logger.addHandler(Main.fileHandler);
        logger.setUseParentHandlers(false);
    }


    protected DatabaseProcess() {
        databaseHandler = new DatabaseHandler();
    }


    public ArrayList<Recipe> getRecipesUser(int id) {
        logger.info("getRecipesUser");
        return databaseHandler.getRecipeHandler().getRecipesUser(id);
    }

    public Recipe getRecipe(int idR) {
        logger.info("getRecipe");
        return databaseHandler.getRecipeHandler().getRecipe(idR);
    }

    public void addRecipe(Recipe recipe, int id) {
        logger.info("addRecipe");
        databaseHandler.getRecipeHandler().addRecipe(recipe, id);
    }

    public void deleteRecipe(int id) {
        logger.info("deleteRecipe");
        databaseHandler.getRecipeHandler().deleteRecipes(id);
    }

    public int getLastId() {
        logger.info("getLastId");
        return databaseHandler.getRecipeHandler().getLastId();
    }

    public boolean findRecipe(int idR) {
        logger.info("findRecipe");
        return databaseHandler.getRecipeHandler().findRecipe(idR);
    }

    // User

    public void addUser(String nameUser, String password) {
        logger.info("addUser");
        databaseHandler.getUserHandler().addUser(nameUser, password);
    }

    public User getUser(String name, String password) {
        logger.info("getUser");
        return databaseHandler.getUserHandler().getUser(name, password);
    }

    public boolean findUser(String name, String password) {
        logger.info("findUser");
        return databaseHandler.getUserHandler().findUser(name, password);
    }

    // static
    public void closeConnection() {
        logger.info("closeConnection");
        databaseHandler.closeConnection();
    }
}
