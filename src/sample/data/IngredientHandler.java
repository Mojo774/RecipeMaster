package sample.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

class IngredientHandler extends DatabaseConnector {

    DatabaseHandler databaseHandler;

    PreparedStatement preparedStatement;
    ResultSet resultSet;

    public IngredientHandler(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
    }


    // Удалить все ингредиенты и значения из таблицы связей _HAS_
    public void deleteIngredients() throws SQLException {
        databaseHandler.getRecipe_has_ingredientHandler().deleteHas();


        String command = String.format("SELECT * FROM %s WHERE %s < ?",
                getConstDB("INGREDIENT_TABLE"), getConstDB("INGREDIENT_ID"));
        preparedStatement = getPreparedStatement(String.format(command));
        preparedStatement.setInt(1, getLastId() + 1);

        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {

            command = String.format("DELETE FROM %s WHERE %s < 100;",
                    getConstDB("INGREDIENT_TABLE"), getConstDB("INGREDIENT_ID"));
            useCommand(command);
        }

        resetIncrement(getConstDB("INGREDIENT_TABLE"));


    }

    // Удалить ингредиент id
    public void deleteIngredients(int id) throws SQLException {


        preparedStatement = getPreparedStatement(String.format("SELECT * FROM %s WHERE %s = ?",
                getConstDB("INGREDIENT_TABLE"), getConstDB("INGREDIENT_ID")));
        preparedStatement.setInt(1, id);

        resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {

            String command = String.format("DELETE FROM %s WHERE %s = %d;",
                    getConstDB("INGREDIENT_TABLE"), getConstDB("INGREDIENT_ID"), id);
            useCommand(command);
        }


    }

    // Удаляет те ингредиенты, которые не используются в рецептах
    public void deleteUselessIngredients() throws SQLException {
        HashSet<Integer> setId = databaseHandler.getRecipe_has_ingredientHandler().getIngredientsId();


        String command = String.format("SELECT * FROM %s WHERE %s < ?",
                getConstDB("INGREDIENT_TABLE"), getConstDB("INGREDIENT_ID"));
        preparedStatement = getPreparedStatement(command);
        preparedStatement.setInt(1, getLastId() + 1);

        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Integer id = resultSet.getInt(getConstDB("INGREDIENT_ID"));

            if (!setId.contains(id)) {
                String command2 = String.format("DELETE FROM %s WHERE %s = %d;",
                        getConstDB("INGREDIENT_TABLE"), getConstDB("INGREDIENT_ID"), id);
                useCommand(command2);
            }
        }


    }

    // Возвращает id ингредиента по имени
    public int getIngredientId(String name) throws SQLException {
        String command = String.format("SELECT * FROM %s WHERE %s = ?",
                getConstDB("INGREDIENT_TABLE"), getConstDB("INGREDIENT_NAME"));

        preparedStatement = getPreparedStatement(command);
        int id = -1;
        preparedStatement.setString(1, name);

        resultSet = preparedStatement.executeQuery();

        if (resultSet.next())
            id = resultSet.getInt(getConstDB("INGREDIENT_ID"));


        if (id == -1)   // Если такого ингредиента нет - создает новый
            return createIngredient(name);
        else return id;
    }

    // Создание ингредиента с именем
    private int createIngredient(String name) throws SQLException {
        String command = String.format("INSERT INTO %s(%s) VALUES (?);",
                getConstDB("INGREDIENT_TABLE"), getConstDB("INGREDIENT_NAME"));

        preparedStatement = getPreparedStatement(command);
        preparedStatement.setString(1, name);
        preparedStatement.execute();

        return getIngredientId(name);
    }

    // Получить id последнего ингредиента из БД
    private int getLastId() {
        int lastId = 0;

        String command = String.format("SELECT * FROM %s ORDER BY %s DESC LIMIT 1;",
                getConstDB("INGREDIENT_TABLE"), getConstDB("INGREDIENT_ID"));

        preparedStatement = getPreparedStatement(command);

        try {
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                lastId = resultSet.getInt(getConstDB("INGREDIENT_ID"));

        } catch (Exception e) {
            e.printStackTrace();
        }


        return lastId;
    }

}
