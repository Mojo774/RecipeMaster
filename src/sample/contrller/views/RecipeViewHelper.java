package sample.contrller.views;

import sample.recipe_package.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeViewHelper {


    public List<RecipeView> getViews(ArrayList<Recipe> recipes) {
        List<RecipeView> views = new ArrayList<>();

        for (Recipe recipe : recipes) {
            views.add(recipe.getTableView());
        }

        int t = 1;
        for (RecipeView v : views) {
            v.setNumber(t++);
        }

        return views;

    }


}
