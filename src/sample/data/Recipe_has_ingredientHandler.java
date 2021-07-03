package sample.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

class Recipe_has_ingredientHandler extends DatabaseHandler implements IHandler, AutoCloseable{

    DatabaseController dataBaseController;

    PreparedStatement preparedStatement;
    ResultSet resultSet;

    protected Recipe_has_ingredientHandler(DatabaseController dataBaseController){
        this.dataBaseController = dataBaseController;
    }

    // Получить id используемых ингредиентов
    public HashSet<Integer> getIngredientsId() throws SQLException {

        HashSet<Integer> setId = new HashSet<>();


        String command = String.format("SELECT * FROM %s WHERE %s < ? ",
                getConstDB("RECIPE_HAS_INGREDIENT_TABLE"), getConstDB("RECIPE_HAS_RECIPE_ID"));
        preparedStatement = getPreparedStatement(command);
        preparedStatement.setInt(1, dataBaseController.getRecipeHandler().getLastId() + 1);

        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {

            setId.add(resultSet.getInt(getConstDB("RECIPE_HAS_INGREDIENT_ID")));

        }


        return setId;
    }

    // Удалить все значения таблицы _HAS_
    public void deleteHas() throws SQLException {

        String command = String.format("SELECT * FROM %s WHERE %s < ?",
                getConstDB("RECIPE_HAS_INGREDIENT_TABLE"), getConstDB("RECIPE_HAS_RECIPE_ID"));
        preparedStatement = getPreparedStatement(command);
        preparedStatement.setInt(1, dataBaseController.getRecipeHandler().getLastId() + 1);

        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {

            String command2 = String.format("DELETE FROM %s WHERE %s < 100;",
                    getConstDB("RECIPE_HAS_INGREDIENT_TABLE"), getConstDB("RECIPE_HAS_RECIPE_ID"));
            useCommand(command2);
        }

        resetIncrement(getConstDB("RECIPE_HAS_INGREDIENT_TABLE"));


    }

    // Удалить связки с ингредиентами из таблицы _HAS_ для ингредиента idR
    public void deleteHas(int idR) throws SQLException {


        String command = String.format("SELECT * FROM %s WHERE %s = ?",
                getConstDB("RECIPE_HAS_INGREDIENT_TABLE"), getConstDB("RECIPE_HAS_RECIPE_ID"));
        preparedStatement = getPreparedStatement(command);
        preparedStatement.setInt(1, idR);

        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {

            //int id = resultSet.getInt(ConstDb.RECIPE_HAS_INGREDIENT_ID);

            String command2 = String.format("DELETE FROM %s WHERE %s = %d;",
                    getConstDB("RECIPE_HAS_INGREDIENT_TABLE"), getConstDB("RECIPE_HAS_RECIPE_ID"), idR);
            useCommand(command2);


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
