package sample.model;

import sample.Main;
import sample.User;
import sample.controller.views.IngredientView;
import sample.data.DatabaseHandler;
import sample.recipe_package.Description;
import sample.recipe_package.Ingredient;
import sample.recipe_package.Recipe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class MainProcess  extends DatabaseProcess
{
    private Recipe recipe;
    private UserProcess userProcess = new UserProcess();
    private DatabaseProcess databaseProcess = new DatabaseProcess();

    // Logger
    private static final Logger logger = Logger.getLogger(MainProcess.class.getName());

    static {
        logger.addHandler(Main.fileHandler);
        logger.setUseParentHandlers(false);
    }

    public void createRecipe(String textName, String textDescription, List<IngredientView> views, int changeId) {
        logger.info("Start createRecipe");


        ArrayList<Ingredient> ingredients = new ArrayList<>();
        Description description = new Description(textDescription, textName);

        // Замена в views пустых строк на null
        views.forEach(x -> {
            if (x.getSize() != null) {
                if (x.getSize().equals(""))
                    x.setSize(null);
            }
            Ingredient ingredient = new Ingredient(x.getName(), x.getSize());
            ingredients.add(ingredient);
        });


        // Если рецепт отредактирован - удалить старый
        if (changeId != -1) {
            databaseProcess.deleteRecipe(changeId);
            recipe = new Recipe(description, ingredients, changeId);
        } else {
            recipe = new Recipe(description, ingredients, getNewIdR());
        }


        // Добавление рецепта в БД
        databaseProcess.addRecipe(recipe, userProcess.getIdUser());

        logger.info("END createRecipe");
    }

    public void setUser(String name, String password) {
        logger.info("setUser");


        User user = databaseProcess.getUser(name, password);
        userProcess.setUser(user);


    }


    public int getIdUser() {
        logger.info("getIdUser");
        return userProcess.getIdUser();
    }

    // Создание id для рецепта
    // Чтобы внести рецепт в БД нужно его сначало создать
    // А чтобы создать его нужно присвоить ему id
    // Поэтому я обращаюсь к БД беру последний (самый большой) id и присваиваю его рецепту
    private int getNewIdR() {
        logger.info("getNewIdR");

        int idR = databaseProcess.getLastId() + 1;

        // Если база данных пустая и мы начнем создавать новые рецепты
        // метод сверху будет всегда возвращать 0 пока мы не внесем
        // хотя бы один рецепт в базу данных
        while (databaseProcess.findRecipe(idR)) {
            idR++;
        }


        return idR;
    }
}
