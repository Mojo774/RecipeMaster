package sample.Recipe_Package;

import sample.Data.RecipeHandler;

import java.io.*;
import java.util.ArrayList;

public class All_recipes  {
    private static ArrayList<Recipe> recipes = new ArrayList<>();
    private static ArrayList<Recipe> recipesNew = new ArrayList<>();


    public static void addRecipe(Recipe recipe){
        recipesNew.add(recipe);

    }

    public static ArrayList<Recipe> getRecipes(){
        return recipes;
    }

    public static Recipe searchId(int id){
        for (Recipe recipe: recipes){
            if (recipe.getIdR() == id)
                return recipe;
        }

        return null;
    }

    public static Recipe searchIdNew(int id){
        for (Recipe recipe: recipesNew){
            if (recipe.getIdR() == id)
                return recipe;
        }

        return null;
    }

    public static void loudRecipes(){

        for (Recipe recipe: recipesNew){
            RecipeHandler.setRecipe(recipe);
        }

        recipes = RecipeHandler.getRecipes();

        recipesNew = new ArrayList<>();
    }

    public static void printIngredients(){
        for (Recipe recipe: recipes){
            recipe.getIngredients().forEach(x-> System.out.println(x.getString()));
        }
    }

    public static void print(){
        for (Recipe recipe: recipes){
            recipe.Print();
        }
    }

}
