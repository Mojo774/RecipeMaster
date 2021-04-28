package sample.Recipe_Package;

import sample.Data.RecipeHandler;

import java.util.ArrayList;

public class recipesHelper {

    public  void addRecipe(Recipe recipe) {
        RecipeHandler.setRecipe(recipe);

    }

    // Нужен ли var ?
    public ArrayList<Recipe> getRecipes() {
        ArrayList<Recipe> recipes;

        recipes = RecipeHandler.getRecipes();

        return recipes;
    }

    public ArrayList<Recipe> getRecipes(int id) {
        ArrayList<Recipe> recipes = new ArrayList<>();


        //Выводи весь список
        //EntityFramework c#

        // Выбрать из БД запись с пришедшим нам ИД, FirstOrDefault - ограничиться одной запись.
        // resipes = recipes.Where(recipe => recipe.id == id).FirstOrDefault()

        var list = new recipesHelper().getRecipes();
        for (Recipe recipe : list) {
            if (recipe.getIdR() == id)
                recipes.add(recipe);
        }

        return recipes;
    }


}
