package sample.Data;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

import com.mysql.cj.jdbc.Driver;
import sample.Recipe_Package.All_recipes;
import sample.Recipe_Package.Description;
import sample.Recipe_Package.Ingredient;
import sample.Recipe_Package.Recipe;
import sample.User;

public class DatabaseHandler extends Configs {
    protected static Connection connection;
    protected static ResultSet resultSet;

    protected static PreparedStatement preparedStatement;

    // Create connection
    static {
        String connectionString = String.format("jdbc:mysql://%s:%s/%s?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                dbHost, dbPort, dbName);

        // Class.forName("com.mysql.cj.jdbc.driver");

        try {
            connection = DriverManager.getConnection(connectionString, dbUser, dbPass);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public static void signUpRecipes() {

        //RecipeHandler.deleteRecipes();
        //IngredientHandler.deleteIngredients();
        //UserHandler.addUser("Biba Boba" , "14881488");
        //System.out.println(RecipeHandler.getLastId());

        /*Description description = new Description("odin","dva");
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        Collections.addAll(ingredients, new Ingredient("Pow"),new Ingredient("Rod","5"),new Ingredient("MKK","4"));
        Recipe recipe = new Recipe(description,ingredients);*/
        //RecipeHandler.setRecipe(recipe);

        //All_recipes.loudRecipes();
        //recipe.Print();
        //All_recipes.print();

        //RecipeHandler.deleteRecipes(6);
        //clear();


    }

    // Получить значения всех строк таблицы tableName
    public static ResultSet getResultSet(String tableName) {
        try {
            preparedStatement = getPreparedStatement(String.format("SELECT * FROM %s", tableName));
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return resultSet;
    }

    // Сбрасывает авто-инкремент у таблицы tableName
    public static void resetIncrement(String tableName) {
        String command = String.format("ALTER TABLE %s AUTO_INCREMENT = 1;", tableName);
        useCommand(command);
    }

    // Если надо просто выполнить команду
    public static void useCommand(String command) {
        try {
            preparedStatement = connection.prepareStatement(command);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // Если надо получить результат выполнения
    public static PreparedStatement getPreparedStatement(String command) {
        try {
            preparedStatement = connection.prepareStatement(command);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return preparedStatement;
    }

    // Закрытие соединений
    public static void closeConnection() {
        try {
            connection.close();
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    // удаляет поля во всех таблицах кроме юзера, и сбрасывает инкремент
    public static void clear() {

        IngredientHandler.deleteIngredients();
        RecipeHandler.deleteRecipes();
    }
}
