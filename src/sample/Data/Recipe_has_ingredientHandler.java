package sample.Data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Recipe_has_ingredientHandler extends DatabaseHandler{

    // Получить id используемых ингредиентов
    public static ArrayList<Integer> getIngredientsId(){
        ArrayList<Integer> array = new ArrayList<>();

        try {
            String command = String.format("SELECT * FROM %s WHERE %s < ? ",
                    ConstDb.RECIPE_HAS_INGREDIENT_TABLE, ConstDb.RECIPE_HAS_RECIPE_ID);
            PreparedStatement preparedStatement = getPreparedStatement(command);
            preparedStatement.setInt(1, RecipeHandler.getLastId()+1);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                array.add(resultSet.getInt(ConstDb.RECIPE_HAS_INGREDIENT_ID));

            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return array;
    }

    // Удалить все значения таблицы _HAS_
    public  static void deleteHas(){
        try {
            String command = String.format("SELECT * FROM %s WHERE %s < ?",
                    ConstDb.RECIPE_HAS_INGREDIENT_TABLE, ConstDb.RECIPE_HAS_RECIPE_ID);
            PreparedStatement preparedStatement = getPreparedStatement(command);
            preparedStatement.setInt(1, RecipeHandler.getLastId()+1);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                String command2 = String.format("DELETE FROM %s WHERE %s < 100;",
                        ConstDb.RECIPE_HAS_INGREDIENT_TABLE, ConstDb.RECIPE_HAS_RECIPE_ID);
                useCommand(command2);
            }

            DatabaseHandler.resetIncrement(ConstDb.RECIPE_HAS_INGREDIENT_TABLE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Удалить связки с ингредиентами из таблицы _HAS_ для ингредиента idR
    public  static void deleteHas(int idR){
        try {
            String command = String.format("SELECT * FROM %s WHERE %s = ?",
                    ConstDb.RECIPE_HAS_INGREDIENT_TABLE, ConstDb.RECIPE_HAS_RECIPE_ID);
            PreparedStatement preparedStatement = getPreparedStatement(command);
            preparedStatement.setInt(1, idR);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                //int id = resultSet.getInt(ConstDb.RECIPE_HAS_INGREDIENT_ID);

                String command2 = String.format("DELETE FROM %s WHERE %s = %d;",
                        ConstDb.RECIPE_HAS_INGREDIENT_TABLE, ConstDb.RECIPE_HAS_RECIPE_ID, idR);
                useCommand(command2);


            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
