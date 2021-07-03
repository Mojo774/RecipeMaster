package sample.model;

import sample.Main;
import sample.User;
import sample.data.DatabaseController;
import sample.recipe_package.Recipe;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

class DatabaseProcess {
    // handlers
    protected DatabaseController dataBaseController;

    // Logger
    private static final Logger logger = Logger.getLogger(DatabaseProcess.class.getName());

    static {
        logger.addHandler(Main.fileHandler);
        logger.setUseParentHandlers(false);
    }


    protected DatabaseProcess() {
        dataBaseController = new DatabaseController();
    }


    public ArrayList<Recipe> getRecipesUser(int id) {
        logger.info("getRecipesUser");
        try {
            return dataBaseController.getRecipeHandler().getRecipesUser(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }

    }

    public Recipe getRecipe(int idR) {
        logger.info("getRecipe");
        try {
            return dataBaseController.getRecipeHandler().getRecipe(idR);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public void addRecipe(Recipe recipe, int id) {
        logger.info("addRecipe");
        try {
            dataBaseController.getRecipeHandler().addRecipe(recipe, id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void deleteRecipe(int id) {
        logger.info("deleteRecipe");
        try {
            dataBaseController.getRecipeHandler().deleteRecipes(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public int getLastIdRecipe() {
        logger.info("getLastId");
        try {
            return dataBaseController.getRecipeHandler().getLastId();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return -1;
        }
    }

    public boolean findRecipe(int idR) {
        logger.info("findRecipe");
        try {
            return dataBaseController.getRecipeHandler().findRecipe(idR);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    // User

    public void addUser(String nameUser, String password) {
        logger.info("addUser");
        dataBaseController.getUserHandler().addUser(nameUser, password);
    }

    public User getUser(String name, String password) {
        logger.info("getUser");
        try {
            return dataBaseController.getUserHandler().getUser(name, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public boolean findUser(String name, String password) {
        logger.info("findUser");
        try {
            return dataBaseController.getUserHandler().findUser(name, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public void close(){
        try {
            dataBaseController.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
