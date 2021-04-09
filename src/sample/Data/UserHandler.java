package sample.Data;

import sample.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserHandler extends DatabaseHandler{

    // Добавить юзера в БД
    public static void addUser(String nameUser, String password) {
        String command = String.format("INSERT INTO %s(%s, %s) VALUES ('%s','%s');",
                ConstDb.USER_TABLE, ConstDb.USER_NAME, ConstDb.USER_PASSWORD,
                nameUser, password);


        useCommand(command);
    }

    // Вывести в консоль инфу о всех юзерах
    public static void printUsers() {
        try {
            resultSet = getResultSet(ConstDb.USER_TABLE);

            while (resultSet.next()) {

                int Id = resultSet.getInt(ConstDb.USER_ID);
                String Name = resultSet.getString(ConstDb.USER_NAME);
                String Password = resultSet.getString(ConstDb.USER_PASSWORD);

                System.out.println(String.format("Id: %d Name: %s Password: %s", Id, Name, Password));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Поиск юзера в БД и установка его как текущего пользователя
    public static void setUser(int id){
        preparedStatement = getPreparedStatement(String.format("SELECT * FROM %s WHERE %s = %d",
                ConstDb.USER_TABLE,ConstDb.USER_ID,id));

        try {
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                int Id = resultSet.getInt(ConstDb.USER_ID);
                String Name = resultSet.getString(ConstDb.USER_NAME);
                String Password = resultSet.getString(ConstDb.USER_PASSWORD);

                User.setUser(Id,Name,Password);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    // Поиск юзера в БД и установка его как текущего пользователя
    public static void setUser(String name,String password){
        String command = String.format("SELECT * FROM %s WHERE %s = ? AND %s = ?",
                ConstDb.USER_TABLE,ConstDb.USER_NAME, ConstDb.USER_PASSWORD);
        preparedStatement = getPreparedStatement(command);

        try {
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,password);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                int Id = resultSet.getInt(ConstDb.USER_ID);
                String Name = resultSet.getString(ConstDb.USER_NAME);
                String Password = resultSet.getString(ConstDb.USER_PASSWORD);

                User.setUser(Id,Name,Password);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // Удаление юзера по id
    public static void deleteUser(int id) {

        String command = String.format("DELETE FROM %s WHERE %s = %d;",
                ConstDb.USER_TABLE, ConstDb.USER_ID, id);

        useCommand(command);

    }
}
