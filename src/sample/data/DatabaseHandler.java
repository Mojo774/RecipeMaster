package sample.data;

import sample.data.thread.ThreadGetPreparedStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DatabaseHandler extends DatabaseConnector{

    // Handlers
    private Recipe_has_ingredientHandler recipe_has_ingredientHandler = new Recipe_has_ingredientHandler();
    private IngredientHandler ingredientHandler = new IngredientHandler();
    private RecipeHandler recipeHandler = new RecipeHandler();
    private UserHandler userHandler = new UserHandler();

    protected static ExecutorService service = Executors.newFixedThreadPool(2);



    // удаляет поля во всех таблицах кроме юзера, и сбрасывает инкремент
    private void clear() {

        try {
            ingredientHandler.deleteIngredients();
            recipeHandler.deleteRecipes();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public Recipe_has_ingredientHandler getRecipe_has_ingredientHandler() {
        return recipe_has_ingredientHandler;
    }

    public IngredientHandler getIngredientHandler() {
        return ingredientHandler;
    }

    public RecipeHandler getRecipeHandler() {
        return recipeHandler;
    }

    public UserHandler getUserHandler() {
        return userHandler;
    }

    // Если надо получить результат выполнения
    public PreparedStatement getPreparedStatement(String command) {
        try {

            // Запрос в Бд идет из другого потока
            // Вообще, от него тут пользы не особо много
            // т.к. оснавная нить все равно не может продолжить работать без
            // результата от обращения
            Future<PreparedStatement> task = service.submit(new ThreadGetPreparedStatement(command, connection));

            while (!task.isDone()) {
                Thread.sleep(3);
            }

            PreparedStatement preparedStatement = task.get();

            return preparedStatement;

        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }

    }

    // Получить значения всех строк таблицы tableName
    protected ResultSet getResultSet(String tableName) {

        try (PreparedStatement preparedStatement = getPreparedStatement(String.format("SELECT * FROM %s", tableName));
             ResultSet resultSet = preparedStatement.executeQuery();) {

            return resultSet;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }


    }

    // Сбрасывает авто-инкремент у таблицы tableName
    protected void resetIncrement(String tableName) {
        String command = String.format("ALTER TABLE %s AUTO_INCREMENT = 1;", tableName);
        useCommand(command);
    }

    // Если надо просто выполнить команду
    protected void useCommand(String command) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(command)) {
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}
