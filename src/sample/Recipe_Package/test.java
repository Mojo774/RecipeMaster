package sample.Recipe_Package;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class test {
    public static void main(String[] args) {
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        Ingredient ingredient1 = new Ingredient("adfasdf","1");
        Ingredient ingredient2 = new Ingredient("zczxc","1");
        Ingredient ingredient3 = new Ingredient("sdfsdf","1");
        Ingredient ingredient4 = new Ingredient("sdfsdf","1");
        Collections.addAll(ingredients, ingredient1, ingredient2, ingredient3, ingredient4);

        String first8ingredient = "";
        int t = 0;

        for (Ingredient x : ingredients) {
            first8ingredient += String.format("%s, ", x.getName());
            t++;
            if (t == 8)
                break;
        }
        first8ingredient = first8ingredient.substring(0, first8ingredient.length() - 2);
        System.out.println(first8ingredient);
    }
}
