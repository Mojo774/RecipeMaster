package sample.recipe_package;

import sample.controller.views.RecipeView;

import java.util.ArrayList;

public class Recipe {
    private Description description;
    private ArrayList<Ingredient> ingredients;
    private Integer idR;

    public Recipe(Description description, ArrayList<Ingredient> ingredients, int idR) {
        this.description = description;
        this.ingredients = ingredients;

        this.idR = idR;
    }


    public void setDescription(Description description) {
        this.description = description;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Description getDescription() {
        return description;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public Integer getIdR() {
        return idR;
    }

    public void setIdR(Integer id) {
        this.idR = id;
    }

    public RecipeView getTableView() {
        return new RecipeView(this);
    }

    // Вывод ингредиентов
    public void PrintIngredients() {
        this.ingredients.forEach(x -> {
            System.out.println(x.getString());
        });
        System.out.println();
    }

    public void PrintDescription() {
        this.description.Print();
    }

    public void Print() {
        PrintIngredients();
        PrintDescription();
    }


}
