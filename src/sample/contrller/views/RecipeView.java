package sample.contrller.views;

import sample.recipe_package.Ingredient;
import sample.recipe_package.Recipe;


// Класс для отображения рецепта в таблице
public class RecipeView {

    private Integer number;
    private String name;
    private String ingredientStr;
    private Integer idR;


    public RecipeView() {

    }
    public RecipeView(Recipe recipe) {
        ingredientStr = "";
        idR = recipe.getIdR();

        for (Ingredient x : recipe.getIngredients()) {
            ingredientStr += String.format("%s, ", x.getName());
        }

        ingredientStr = ingredientStr.substring(0, ingredientStr.length() - 2);

        this.name = recipe.getDescription().getName();
    }

    public String getIngredientStr() {
        return this.ingredientStr;
    }

    public void setIngredientStr(String ingredientStr) {
        this.ingredientStr = ingredientStr;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return this.number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getIdR() {
        return this.idR;
    }
}
