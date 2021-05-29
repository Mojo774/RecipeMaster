package sample.data;

import sample.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserHandler extends DatabaseConnector {
    DatabaseHandler databaseHandler;

    public UserHandler(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
    }


    // Добавить юзера в БД
    public void addUser(String nameUser, String password) {
        String command = String.format("INSERT INTO %s(%s, %s) VALUES ('%s','%s');",
                getConstDB("USER_TABLE"), getConstDB("USER_NAME"), getConstDB("USER_PASSWORD"),
                nameUser, password);


        useCommand(command);
    }

    // Вывести в консоль инфу о всех юзерах
    public void printUsers() {
        try {
            resultSet = getResultSet(getConstDB("USER_TABLE"));

            while (resultSet.next()) {

                int Id = resultSet.getInt(getConstDB("USER_ID"));
                String Name = resultSet.getString(getConstDB("USER_NAME"));
                String Password = resultSet.getString(getConstDB("USER_PASSWORD"));

                System.out.println(String.format("Id: %d Name: %s Password: %s", Id, Name, Password));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Поиск юзера в БД и установка его как текущего пользователя
    public User getUser(int id) {

        String command = String.format("SELECT * FROM %s WHERE %s = %d",
                getConstDB("USER_TABLE"), getConstDB("USER_ID"), id);
        PreparedStatement preparedStatement = getPreparedStatement(command);

        try {
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int idUser = resultSet.getInt(getConstDB("USER_ID"));
                String nameUser = resultSet.getString(getConstDB("USER_NAME"));
                String passwordUser = resultSet.getString(getConstDB("USER_PASSWORD"));

                User user = new User(idUser, nameUser, passwordUser);
                return user;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    // Поиск юзера в БД и установка его как текущего пользователя
    public User getUser(String name, String password) {
        String command = String.format("SELECT * FROM %s WHERE %s = ? AND %s = ?",
                getConstDB("USER_TABLE"), getConstDB("USER_NAME"), getConstDB("USER_PASSWORD"));
        PreparedStatement preparedStatement = getPreparedStatement(command);

        try {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int idUser = resultSet.getInt(getConstDB("USER_ID"));
                String nameUser = resultSet.getString(getConstDB("USER_NAME"));
                String passwordUser = resultSet.getString(getConstDB("USER_PASSWORD"));

                //User.setUser(idUser, nameUser, passwordUser);
                //
                User user = new User(idUser, nameUser, passwordUser);
                return user;

            } else return null;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    // Поиск юзера
    public boolean findUser(String name, String password) {
        String command = String.format("SELECT * FROM %s WHERE %s = ? AND %s = ?",
                getConstDB("USER_TABLE"), getConstDB("USER_NAME"), getConstDB("USER_PASSWORD"));
        PreparedStatement preparedStatement = getPreparedStatement(command);

        try {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    // Удаление юзера по id
    public void deleteUser(int id) {

        databaseHandler.getRecipeHandler().deleteUserRecipes(id);

        String command = String.format("DELETE FROM %s WHERE %s = %d;",
                getConstDB("USER_TABLE"), getConstDB("USER_ID"), id);

        useCommand(command);

    }
}
