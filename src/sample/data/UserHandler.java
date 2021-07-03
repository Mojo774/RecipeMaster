package sample.data;

import sample.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserHandler extends DatabaseHandler implements IHandler{

    DatabaseController dataBaseController;

    PreparedStatement preparedStatement;
    ResultSet resultSet;

    protected UserHandler(DatabaseController dataBaseController){
        this.dataBaseController = dataBaseController;
    }


    // Добавить юзера в БД
    public void addUser(String nameUser, String password) {
        String command = String.format("INSERT INTO %s(%s, %s) VALUES ('%s','%s');",
                getConstDB("USER_TABLE"), getConstDB("USER_NAME"), getConstDB("USER_PASSWORD"),
                nameUser, password);


        useCommand(command);
    }

    // Поиск юзера в БД и установка его как текущего пользователя
    public User getUser(int id) throws SQLException {

        String command = String.format("SELECT * FROM %s WHERE %s = %d",
                getConstDB("USER_TABLE"), getConstDB("USER_ID"), id);
        preparedStatement = getPreparedStatement(command);

        resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            int idUser = resultSet.getInt(getConstDB("USER_ID"));
            String nameUser = resultSet.getString(getConstDB("USER_NAME"));
            String passwordUser = resultSet.getString(getConstDB("USER_PASSWORD"));

            User user = new User(idUser, nameUser, passwordUser);
            return user;
        }


        return null;
    }

    // Поиск юзера в БД и установка его как текущего пользователя
    public User getUser(String name, String password) throws SQLException {
        String command = String.format("SELECT * FROM %s WHERE %s = ? AND %s = ?",
                getConstDB("USER_TABLE"), getConstDB("USER_NAME"), getConstDB("USER_PASSWORD"));
        preparedStatement = getPreparedStatement(command);


        preparedStatement.setString(1, name);
        preparedStatement.setString(2, password);

        resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            int idUser = resultSet.getInt(getConstDB("USER_ID"));
            String nameUser = resultSet.getString(getConstDB("USER_NAME"));
            String passwordUser = resultSet.getString(getConstDB("USER_PASSWORD"));

            //User.setUser(idUser, nameUser, passwordUser);
            //
            User user = new User(idUser, nameUser, passwordUser);
            return user;

        } else return null;


    }

    // Поиск юзера
    public boolean findUser(String name, String password) throws SQLException {
        String command = String.format("SELECT * FROM %s WHERE %s = ? AND %s = ?",
                getConstDB("USER_TABLE"), getConstDB("USER_NAME"), getConstDB("USER_PASSWORD"));
        preparedStatement = getPreparedStatement(command);


        preparedStatement.setString(1, name);
        preparedStatement.setString(2, password);

        resultSet = preparedStatement.executeQuery();

        return resultSet.next();


    }

    // Удаление юзера по id
    public void deleteUser(int id) throws SQLException {

        dataBaseController.getRecipeHandler().deleteUserRecipes(id);

        String command = String.format("DELETE FROM %s WHERE %s = %d;",
                getConstDB("USER_TABLE"), getConstDB("USER_ID"), id);

        useCommand(command);

    }
}
