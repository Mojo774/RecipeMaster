package sample.Data;

import sample.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class IngredientHandler extends DatabaseHandler {

    // Возвращает id ингредиента по имени
    public static int getIngredientId(String name) {
        String command = String.format("SELECT * FROM %s WHERE %s = ?",
                ConstDb.INGREDIENT_TABLE, ConstDb.INGREDIENT_NAME);

        preparedStatement = getPreparedStatement(command);

        int id = -1;
        try {
            preparedStatement.setString(1, name);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
                id = resultSet.getInt(ConstDb.INGREDIENT_ID);


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if (id == -1)   // Если такого ингредиента нет - создает новый
            return createIngredient(name);
        else return id;
    }

    // Создание ингредиента с именем
    public static int createIngredient(String name) {
        String command = String.format("INSERT INTO %s(%s) VALUES (?);",
                ConstDb.INGREDIENT_TABLE, ConstDb.INGREDIENT_NAME);

        preparedStatement = getPreparedStatement(command);

        try {
            preparedStatement.setString(1, name);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return getIngredientId(name);
    }

    // Получить id последнего ингредиента из БД
    public static int getLastId() {
        int lastId = 0;

        String command = String.format("SELECT * FROM %s ORDER BY %s DESC LIMIT 1;",
                ConstDb.INGREDIENT_TABLE, ConstDb.INGREDIENT_ID);

        preparedStatement = getPreparedStatement(command);

        try {
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                lastId = resultSet.getInt(ConstDb.INGREDIENT_ID);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return lastId;
    }

    // Удалить все ингредиенты и значения из таблицы связей _HAS_
    public static void deleteIngredients() {
        Recipe_has_ingredientHandler.deleteHas();

        try {
            String command = String.format("SELECT * FROM %s WHERE %s < ?",
                    ConstDb.INGREDIENT_TABLE, ConstDb.INGREDIENT_ID);
            preparedStatement = getPreparedStatement(String.format(command));


            preparedStatement.setInt(1, IngredientHandler.getLastId()+1);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                command = String.format("DELETE FROM %s WHERE %s < 100;",
                        ConstDb.INGREDIENT_TABLE, ConstDb.INGREDIENT_ID);
                useCommand(command);
            }

            DatabaseHandler.resetIncrement(ConstDb.INGREDIENT_TABLE);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Удалить ингредиент id
    public static void deleteIngredients(int id) {

        try {
            preparedStatement = getPreparedStatement(String.format("SELECT * FROM %s WHERE %s = ?",
                    ConstDb.INGREDIENT_TABLE, ConstDb.INGREDIENT_ID));


            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                String command = String.format("DELETE FROM %s WHERE %s = %d;",
                        ConstDb.INGREDIENT_TABLE, ConstDb.INGREDIENT_ID, id);
                useCommand(command);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Удаляет те ингредиенты, которые не используются в рецептах
    public static void deleteUselessIngredients() {
        ArrayList<Integer> array = Recipe_has_ingredientHandler.getIngredientsId();

        try {
            String command = String.format("SELECT * FROM %s WHERE %s < ?",
                    ConstDb.INGREDIENT_TABLE, ConstDb.INGREDIENT_ID);
            PreparedStatement preparedStatement = getPreparedStatement(command);
            preparedStatement.setInt(1, IngredientHandler.getLastId()+1);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Integer id = resultSet.getInt(ConstDb.INGREDIENT_ID);

                if (!array.contains(id)) {
                    String command2 = String.format("DELETE FROM %s WHERE %s = %d;",
                            ConstDb.INGREDIENT_TABLE, ConstDb.INGREDIENT_ID, id);
                    useCommand(command2);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
