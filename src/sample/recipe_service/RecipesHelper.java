package sample.recipe_service;

import sample.contrllers.views.IngredientView;
import sample.data.RecipeHandler;
import sample.recipe_package.Description;
import sample.recipe_package.Ingredient;
import sample.recipe_package.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipesHelper {
    private Recipe recipe;

    // handlers
    private RecipeHandler recipeHandler = new RecipeHandler();

    public void addRecipe(Recipe recipe) {
        recipeHandler.setRecipe(recipe);

    }

    // Нужен ли var ?
    public ArrayList<Recipe> getRecipes() {
        ArrayList<Recipe> recipes;

        recipes = recipeHandler.getRecipes();

        return recipes;
    }

    public ArrayList<Recipe> getRecipes(int id) {
        ArrayList<Recipe> recipes = new ArrayList<>();


        //Выводи весь список
        //EntityFramework c#

        // Выбрать из БД запись с пришедшим нам ИД, FirstOrDefault - ограничиться одной запись.
        // resipes = recipes.Where(recipe => recipe.id == id).FirstOrDefault()

        var list = new RecipesHelper().getRecipes();
        for (Recipe recipe : list) {
            if (recipe.getIdR() == id)
                recipes.add(recipe);
        }

        return recipes;
    }


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
            recipeHandler.deleteRecipes(changeId);
            recipe = new Recipe(description, ingredients, changeId);
        } else {
            recipe = new Recipe(description, ingredients, getIdR());
        }


        // Добавление рецепта в БД
        addRecipe(recipe);

    }


    // Создание id для рецепта
    // Чтобы внести рецепт в БД нужно его сначало создать
    // А чтобы создать его нужно присвоить ему id
    // Поэтому я обращаюсь к БД беру последний (самый большой) id и присваиваю его рецепту
    private int getIdR() {
        int idR = recipeHandler.getLastId() + 1;

        // Если база данных пустая и мы начнем создавать новые рецепты
        // метод сверху будет всегда возвращать 0 пока мы не внесем
        // хотя бы один рецепт в базу данных
        while (new RecipesHelper().getRecipes(idR).size() > 0) {
            if (new RecipesHelper().getRecipes(idR).get(0) != null)
                idR++;
        }


        return idR;
    }

}
