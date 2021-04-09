package sample.Recipe_Package;

import javax.swing.*;
import java.util.ArrayList;

public class Recipe {
    private Description description;
    private ArrayList<Ingredient> ingredients;
    private Integer idR;
    private tableView view;

    public Recipe(Description description, ArrayList<Ingredient> ingredients, int idR) {
        this.description = description;
        this.ingredients = ingredients;

        this.idR = idR;
        view = new tableView();
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

    public tableView getTableView(){
        return view;
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

    // Класс для отображения рецепта в таблице
    public class tableView {
        private Integer number;
        private String name;
        private String ingredientStr;
        private Integer id;

        public tableView() {
            ingredientStr = "";
            id = idR;

            for (Ingredient x : ingredients) {
                ingredientStr += String.format("%s, ", x.getName());
            }

            ingredientStr = ingredientStr.substring(0, ingredientStr.length() - 2);

            this.name = description.getName();
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

        public Integer getId() {
            return this.id;
        }
    }
}
