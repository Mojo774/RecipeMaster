package sample.model;

import sample.Main;
import sample.User;
import sample.controller.Controllers;
import sample.data.DatabaseHandler;
import sample.recipe_package.Recipe;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;
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
        try {
            return databaseHandler.getRecipeHandler().getRecipesUser(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }

    }

    public Recipe getRecipe(int idR) {
        logger.info("getRecipe");
        try {
            return databaseHandler.getRecipeHandler().getRecipe(idR);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public void addRecipe(Recipe recipe, int id) {
        logger.info("addRecipe");
        try {
            databaseHandler.getRecipeHandler().addRecipe(recipe, id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void deleteRecipe(int id) {
        logger.info("deleteRecipe");
        try {
            databaseHandler.getRecipeHandler().deleteRecipes(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public int getLastId() {
        logger.info("getLastId");
        try {
            return databaseHandler.getRecipeHandler().getLastId();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return -1;
        }
    }

    public boolean findRecipe(int idR) {
        logger.info("findRecipe");
        try {
            return databaseHandler.getRecipeHandler().findRecipe(idR);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    // User

    public void addUser(String nameUser, String password) {
        logger.info("addUser");
        databaseHandler.getUserHandler().addUser(nameUser, password);
    }

    public User getUser(String name, String password) {
        logger.info("getUser");
        try {
            return databaseHandler.getUserHandler().getUser(name, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public boolean findUser(String name, String password) {
        logger.info("findUser");
        try {
            return databaseHandler.getUserHandler().findUser(name, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    // static
    public void closeConnection() {
        logger.info("closeConnection");
        databaseHandler.closeConnection();
    }
}
