package sample.data;

import sample.recipe_package.Description;
import sample.recipe_package.Ingredient;
import sample.recipe_package.Recipe;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RecipeHandler extends DatabaseConnector {

    /* Список методов:

    Получить лист рецептов юзера
    ArrayList<Recipe> getRecipes()

    Получить ингредиенты из рецепта idR
    ArrayList<Ingredient> getIngredients(int idR)

    Получить ингредиент по id с его размером в конкретном рецете
    Ingredient getOneIngredient(int id, String size)

    Получить id последнего репта из БД
    int getLastId()

    Добавление рецепта в БД
    void setRecipe(Recipe recipe)

    Проверка наличия рецепта по id
    boolean FindRecipe(int id)

    Удалить все рецепты (имена) из БД (только из таблицы рецептов)
    void deleteRecipes()

    Удалить рецепт по id из БД вместе с ингредиентами
    void deleteRecipes(int id)

    */
    DatabaseHandler databaseHandler;
    public RecipeHandler (DatabaseHandler databaseHandler){
        this.databaseHandler = databaseHandler;
    }


    // Получить рецепт по idR
    public Recipe getRecipe(int idR){
        Recipe recipe = null;

        try {
            String command = String.format("SELECT * FROM %s WHERE %s = ?",
                    ConstDb.RECIPE_TABLE, ConstDb.RECIPE_ID);
            preparedStatement = getPreparedStatement(command);
            preparedStatement.setInt(1, idR);

            ResultSet resultSet1 = preparedStatement.executeQuery();


            if (resultSet1.next()) {
                String name = resultSet1.getString(ConstDb.RECIPE_NAME);
                String description = resultSet1.getString(ConstDb.RECIPE_DESCRIPTION);

                Description description1 = new Description(description, name);

                recipe = new Recipe(description1, getIngredients(idR), idR);


            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return recipe;

    }

    // Получить лист рецептов текущего юзера
    public ArrayList<Recipe> getRecipesUser(int id) {
        ArrayList<Recipe> recipes = new ArrayList<>();

        try {
            String command = String.format("SELECT * FROM %s WHERE %s = ?",
                    ConstDb.RECIPE_TABLE, ConstDb.RECIPE_USER_ID);
            preparedStatement = getPreparedStatement(command);
            preparedStatement.setInt(1, id);

            ResultSet resultSet1 = preparedStatement.executeQuery();


            while (resultSet1.next()) {
                int idR = resultSet1.getInt(ConstDb.RECIPE_ID);
                String name = resultSet1.getString(ConstDb.RECIPE_NAME);
                String description = resultSet1.getString(ConstDb.RECIPE_DESCRIPTION);

                Description description1 = new Description(description, name);
                Recipe recipe = new Recipe(description1, getIngredients(idR), idR);
                recipes.add(recipe);


            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return recipes;
    }


    // Получить ингредиенты из рецепта idR
    private ArrayList<Ingredient> getIngredients(int idR) {
        ArrayList<Ingredient> ingredients = new ArrayList<>();


        try {
            String command = String.format("SELECT * FROM %s WHERE %s = ?",
                    ConstDb.RECIPE_HAS_INGREDIENT_TABLE, ConstDb.RECIPE_HAS_RECIPE_ID);
            preparedStatement = getPreparedStatement(command);
            preparedStatement.setInt(1, idR);


            ResultSet resultSet1 = preparedStatement.executeQuery();

            while (resultSet1.next()) {

                int id = (resultSet1.getInt(ConstDb.RECIPE_HAS_INGREDIENT_ID));
                String size = (resultSet1.getString(ConstDb.RECIPE_HAS_INGREDIENT_SIZE));

                ingredients.add(getOneIngredient(id, size));

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return ingredients;
    }

    // Получить ингредиент по id с его размером в конкретном рецете
    private Ingredient getOneIngredient(int id, String size) {
        Ingredient ingredient = null;

        try {
            String command = (String.format("SELECT * FROM %s WHERE %s = ?",
                    ConstDb.INGREDIENT_TABLE, ConstDb.INGREDIENT_ID));
            preparedStatement = getPreparedStatement(command);
            preparedStatement.setInt(1, id);

            ResultSet resultSet1 = preparedStatement.executeQuery();

            if (resultSet1.next()) {
                String name = resultSet1.getString(ConstDb.INGREDIENT_NAME);

                ingredient = new Ingredient(name, size);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return ingredient;
    }

    // Получить id последнего репта из БД
    public int getLastId() {
        int lastId = 0;

        String command = String.format("SELECT * FROM %s ORDER BY %s DESC LIMIT 1;",
                ConstDb.RECIPE_TABLE, ConstDb.RECIPE_ID);

        preparedStatement = getPreparedStatement(command);


        try {
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                lastId = resultSet.getInt(ConstDb.RECIPE_ID);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return lastId;
    }

    // Добавление рецепта в БД
    public void addRecipe(Recipe recipe, int id) {

        String command = String.format("INSERT INTO %s(%s, %s, %s, %s) VALUES (?,?,?,?);",
                ConstDb.RECIPE_TABLE, ConstDb.RECIPE_NAME, ConstDb.RECIPE_USER_ID, ConstDb.RECIPE_DESCRIPTION, ConstDb.RECIPE_ID);

        PreparedStatement preparedStatement = getPreparedStatement(command);

        try {

            preparedStatement.setString(1, recipe.getDescription().getName());
            preparedStatement.setInt(2, id);
            preparedStatement.setString(3, recipe.getDescription().getText());
            preparedStatement.setInt(4, recipe.getIdR());

            preparedStatement.execute();


        } catch (Exception e) {
            e.printStackTrace();
        }

        // Заполнение _Has_

        int idR = recipe.getIdR();

        ArrayList<Ingredient> ingredients = recipe.getIngredients();


        for (Ingredient ingredient : ingredients) {

            int idIng = databaseHandler.getIngredientHandler().getIngredientId(ingredient.getName());

            command = String.format("INSERT INTO %s(%s, %s, %s) VALUES (?,?,?);",
                    ConstDb.RECIPE_HAS_INGREDIENT_TABLE,
                    ConstDb.RECIPE_HAS_RECIPE_ID, ConstDb.RECIPE_HAS_INGREDIENT_SIZE, ConstDb.RECIPE_HAS_INGREDIENT_ID);
            preparedStatement = getPreparedStatement(command);


            try {
                preparedStatement.setInt(1, idR);
                preparedStatement.setString(2, ingredient.getSize());
                preparedStatement.setInt(3, idIng);
                preparedStatement.execute();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    // Проверка наличия рецепта по id
    public boolean findRecipe(int idR) {
        preparedStatement = getPreparedStatement(String.format("SELECT * FROM %s WHERE %s = %d",
                ConstDb.RECIPE_TABLE, ConstDb.RECIPE_ID, idR));

        try {
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    // Удалить все рецепты (имена) из БД (только из таблицы рецептов)
    public void deleteRecipes() {

        try {
            String command = String.format("SELECT * FROM %s WHERE %s < ?",
                    ConstDb.RECIPE_TABLE, ConstDb.RECIPE_ID);
            preparedStatement = getPreparedStatement(command);
            preparedStatement.setInt(1, getLastId() + 1);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                String command2 = String.format("DELETE FROM %s WHERE %s < 100;",
                        ConstDb.RECIPE_TABLE, ConstDb.RECIPE_ID);
                useCommand(command2);
            }

            DatabaseConnector.resetIncrement(ConstDb.RECIPE_TABLE);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Удалить рецепт по id из БД вместе с ингредиентами
    public void deleteRecipes(int idR) {
        if (!findRecipe(idR)) // Проверка наличия рецепта в БД
            return;

        databaseHandler.getRecipe_has_ingredientHandler().deleteHas(idR);

        try {
            String command = String.format("SELECT * FROM %s WHERE %s = ?",
                    ConstDb.RECIPE_TABLE, ConstDb.RECIPE_ID);
            PreparedStatement preparedStatement = getPreparedStatement(command);
            preparedStatement.setInt(1, idR);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                String command2 = String.format("DELETE FROM %s WHERE %s = %d;",
                        ConstDb.RECIPE_TABLE, ConstDb.RECIPE_ID, idR);

                useCommand(command2);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    // Удалить рецепты юзера по его id
    public void deleteUserRecipes(int id) {





        try {
            String command = String.format("SELECT * FROM %s WHERE %s = ?",
                    ConstDb.RECIPE_TABLE, ConstDb.RECIPE_USER_ID);
            PreparedStatement preparedStatement = getPreparedStatement(command);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int idR = resultSet.getInt(ConstDb.RECIPE_ID);
                deleteRecipes(idR);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
