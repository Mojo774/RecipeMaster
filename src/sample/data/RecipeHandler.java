package sample.data;

import sample.recipe_package.Description;
import sample.recipe_package.Ingredient;
import sample.recipe_package.Recipe;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RecipeHandler extends DatabaseHandler implements IHandler, AutoCloseable{

    DatabaseController dataBaseController;

    PreparedStatement preparedStatement;
    ResultSet resultSet;

    protected RecipeHandler(DatabaseController dataBaseController){
        this.dataBaseController = dataBaseController;
    }

    // Получить рецепт по idR
    public Recipe getRecipe(int idR) throws SQLException {

        Recipe recipe = null;


        String command = String.format("SELECT * FROM %s WHERE %s = ?",
                getConstDB("RECIPE_TABLE"), getConstDB("RECIPE_ID"));
        preparedStatement = getPreparedStatement(command);
        preparedStatement.setInt(1, idR);

        ResultSet resultSet = preparedStatement.executeQuery();


        if (resultSet.next()) {
            String name = resultSet.getString(getConstDB("RECIPE_NAME"));
            String description = resultSet.getString(getConstDB("RECIPE_DESCRIPTION"));

            Description description1 = new Description(description, name);

            recipe = new Recipe(description1, getIngredients(idR), idR);


        }

        return recipe;

    }

    // Получить лист рецептов текущего юзера
    public ArrayList<Recipe> getRecipesUser(int id) throws SQLException {
        ArrayList<Recipe> recipes = new ArrayList<>();


        String command = String.format("SELECT * FROM %s WHERE %s = ?",
                getConstDB("RECIPE_TABLE"), getConstDB("RECIPE_USER_ID"));
        preparedStatement = getPreparedStatement(command);
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();


        while (resultSet.next()) {
            int idR = resultSet.getInt(getConstDB("RECIPE_ID"));
            String name = resultSet.getString(getConstDB("RECIPE_NAME"));
            String description = resultSet.getString(getConstDB("RECIPE_DESCRIPTION"));

            Description description1 = new Description(description, name);
            Recipe recipe = new Recipe(description1, getIngredients(idR), idR);
            recipes.add(recipe);


        }


        return recipes;
    }


    // Получить ингредиенты из рецепта idR
    private ArrayList<Ingredient> getIngredients(int idR) throws SQLException {
        ArrayList<Ingredient> ingredients = new ArrayList<>();


        String command = String.format("SELECT * FROM %s WHERE %s = ?",
                getConstDB("RECIPE_HAS_INGREDIENT_TABLE"), getConstDB("RECIPE_HAS_RECIPE_ID"));
        preparedStatement = getPreparedStatement(command);
        preparedStatement.setInt(1, idR);


        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {

            int id = (resultSet.getInt(getConstDB("RECIPE_HAS_INGREDIENT_ID")));
            String size = (resultSet.getString(getConstDB("RECIPE_HAS_INGREDIENT_SIZE")));

            ingredients.add(getOneIngredient(id, size));

        }


        return ingredients;
    }

    // Получить ингредиент по id с его размером в конкретном рецете
    private Ingredient getOneIngredient(int id, String size) throws SQLException {
        Ingredient ingredient = null;


        String command = (String.format("SELECT * FROM %s WHERE %s = ?",
                getConstDB("INGREDIENT_TABLE"), getConstDB("INGREDIENT_ID")));
        preparedStatement = getPreparedStatement(command);
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            String name = resultSet.getString(getConstDB("INGREDIENT_NAME"));

            ingredient = new Ingredient(name, size);
        }


        return ingredient;
    }

    // Получить id последнего репта из БД
    public int getLastId() throws SQLException {
        int lastId = 0;

        String command = String.format("SELECT * FROM %s ORDER BY %s DESC LIMIT 1;",
                getConstDB("RECIPE_TABLE"), getConstDB("RECIPE_ID"));

        preparedStatement = getPreparedStatement(command);


        resultSet = preparedStatement.executeQuery();
        if (resultSet.next())
            lastId = resultSet.getInt(getConstDB("RECIPE_ID"));


        return lastId;
    }

    // Добавление рецепта в БД
    public void addRecipe(Recipe recipe, int id) throws SQLException {

        String command = String.format("INSERT INTO %s(%s, %s, %s, %s) VALUES (?,?,?,?);",
                getConstDB("RECIPE_TABLE"), getConstDB("RECIPE_NAME"), getConstDB("RECIPE_USER_ID"), getConstDB("RECIPE_DESCRIPTION"), getConstDB("RECIPE_ID"));

        preparedStatement = getPreparedStatement(command);


        preparedStatement.setString(1, recipe.getDescription().getName());
        preparedStatement.setInt(2, id);
        preparedStatement.setString(3, recipe.getDescription().getText());
        preparedStatement.setInt(4, recipe.getIdR());

        preparedStatement.execute();


        // Заполнение _Has_

        int idR = recipe.getIdR();

        ArrayList<Ingredient> ingredients = recipe.getIngredients();


        for (Ingredient ingredient : ingredients) {

            int idIng = dataBaseController.getIngredientHandler().getIngredientId(ingredient.getName());

            command = String.format("INSERT INTO %s(%s, %s, %s) VALUES (?,?,?);",
                    getConstDB("RECIPE_HAS_INGREDIENT_TABLE"),
                    getConstDB("RECIPE_HAS_RECIPE_ID"), getConstDB("RECIPE_HAS_INGREDIENT_SIZE"), getConstDB("RECIPE_HAS_INGREDIENT_ID"));
            preparedStatement = getPreparedStatement(command);


            preparedStatement.setInt(1, idR);
            preparedStatement.setString(2, ingredient.getSize());
            preparedStatement.setInt(3, idIng);
            preparedStatement.execute();


        }

    }

    // Проверка наличия рецепта по id
    public boolean findRecipe(int idR) throws SQLException {
        preparedStatement = getPreparedStatement(String.format("SELECT * FROM %s WHERE %s = %d",
                getConstDB("RECIPE_TABLE"), getConstDB("RECIPE_ID"), idR));


        resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return true;
        }


        return false;
    }

    // Удалить все рецепты (имена) из БД (только из таблицы рецептов)
    public void deleteRecipes() throws SQLException {


        String command = String.format("SELECT * FROM %s WHERE %s < ?",
                getConstDB("RECIPE_TABLE"), getConstDB("RECIPE_ID"));
        preparedStatement = getPreparedStatement(command);
        preparedStatement.setInt(1, getLastId() + 1);

        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {

            String command2 = String.format("DELETE FROM %s WHERE %s < 100;",
                    getConstDB("RECIPE_TABLE"), getConstDB("RECIPE_ID"));
            useCommand(command2);
        }

        resetIncrement(getConstDB("RECIPE_TABLE"));


    }

    // Удалить рецепт по id из БД вместе с ингредиентами
    public void deleteRecipes(int idR) throws SQLException {
        if (!findRecipe(idR)) // Проверка наличия рецепта в БД
            return;

        dataBaseController.getRecipe_has_ingredientHandler().deleteHas(idR);


        String command = String.format("SELECT * FROM %s WHERE %s = ?",
                getConstDB("RECIPE_TABLE"), getConstDB("RECIPE_ID"));
        preparedStatement = getPreparedStatement(command);
        preparedStatement.setInt(1, idR);

        resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {

            String command2 = String.format("DELETE FROM %s WHERE %s = %d;",
                    getConstDB("RECIPE_TABLE"), getConstDB("RECIPE_ID"), idR);

            useCommand(command2);
        }


    }


    // Удалить рецепты юзера по его id
    public void deleteUserRecipes(int id) throws SQLException {


        String command = String.format("SELECT * FROM %s WHERE %s = ?",
                getConstDB("RECIPE_TABLE"), getConstDB("RECIPE_USER_ID"));
        preparedStatement = getPreparedStatement(command);
        preparedStatement.setInt(1, id);

        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int idR = resultSet.getInt(getConstDB("RECIPE_ID"));
            deleteRecipes(idR);
        }


    }


    @Override
    public void close() throws Exception {
        if (preparedStatement != null){
            preparedStatement.close();
        }

        if (resultSet != null){
            resultSet.close();
        }
    }
}
