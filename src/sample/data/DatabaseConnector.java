package sample.data;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DatabaseConnector extends Configs {
    protected static Connection connection;
    protected static ResultSet resultSet;
    protected static PreparedStatement preparedStatement;

    protected static Properties ConstDB = new Properties();
    private static Properties configs = new Properties();



    // Create connection
    static {
        try {
            configs.load(DatabaseConnector.class.getResourceAsStream("/sample/assets/properties/configs.properties"));
            ConstDB.load(DatabaseConnector.class.getResourceAsStream("/sample/assets/properties/DB.properties"));

        } catch (IOException e) {
            e.printStackTrace();
        }


        String connectionString = String.format("jdbc:mysql://%s:%s/%s?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                getConfigs("dbHost"),getConfigs("dbPort"),getConfigs("dbName"));

        // Class.forName("com.mysql.cj.jdbc.driver");

        try {
            connection = DriverManager.getConnection(connectionString, getConfigs("dbUser"), getConfigs("dbPass"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static String getConfigs (String key){
        return configs.getProperty(key);
    }

    protected static String getConstDB (String key){
        return ConstDB.getProperty(key);
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
            if (connection != null)
            connection.close();

            if (preparedStatement != null)
            preparedStatement.close();

            if (resultSet != null)
            resultSet.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

}
