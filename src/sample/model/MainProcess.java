package sample.model;

import sample.User;
import sample.controller.views.IngredientView;
import sample.recipe_package.Description;
import sample.recipe_package.Ingredient;
import sample.recipe_package.Recipe;

import java.util.ArrayList;
import java.util.List;

public class MainProcess extends DatabaseProcess {
    private Recipe recipe;

    private UserProcess userProcess;


    public void createRecipe(String textName, String textDescription, List<IngredientView> views, int changeId) {

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
            deleteRecipe(changeId);
            recipe = new Recipe(description, ingredients, changeId);
        } else {
            recipe = new Recipe(description, ingredients, getNewIdR());
        }


        // Добавление рецепта в БД
        addRecipe(recipe, userProcess.getIdUser());

    }

    public boolean setUser(String name, String password) {
        if (findUser(name, password)) {
            User user = getUser(name, password);
            userProcess = new UserProcess(user);

            return true;
        }
        return false;
    }


    public int getIdUser() {
        return userProcess.getIdUser();
    }

    // Создание id для рецепта
    // Чтобы внести рецепт в БД нужно его сначало создать
    // А чтобы создать его нужно присвоить ему id
    // Поэтому я обращаюсь к БД беру последний (самый большой) id и присваиваю его рецепту
    private int getNewIdR() {
        int idR = getLastId() + 1;

        // Если база данных пустая и мы начнем создавать новые рецепты
        // метод сверху будет всегда возвращать 0 пока мы не внесем
        // хотя бы один рецепт в базу данных
        while (findRecipe(idR)) {
            idR++;
        }


        return idR;
    }
}
