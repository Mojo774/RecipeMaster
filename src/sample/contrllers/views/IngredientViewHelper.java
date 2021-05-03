package sample.contrllers.views;

import sample.contrllers.views.IngredientView;
import sample.recipe_package.Ingredient;
import sample.recipe_package.Recipe;

import java.util.ArrayList;
import java.util.List;

public class IngredientViewHelper {
    private List<IngredientView> views;

    public IngredientViewHelper() {

    }

    public IngredientViewHelper(Recipe recipe) {
        views = new ArrayList<>();
        int number = 1;

        for (Ingredient ingredient : recipe.getIngredients()) {

            views.add(new IngredientView(number++, ingredient.getName(), ingredient.getSize()));

        }

    }



    public List<IngredientView> addEmptyIngredient(List<IngredientView> viewList) {
        List<IngredientView> views = new ArrayList<>();

        int t = 1;
        for (IngredientView ingredient : viewList) {
            if (!ingredient.getName().equals("")) {
                ingredient.setNumber(t++);
                views.add(ingredient);
            }
        }
        views.add(new IngredientView(t, "", ""));

        return views;
    }


    public List<IngredientView> getViews() {
        return views;
    }


}
