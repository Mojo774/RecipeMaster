package sample.data;

import sample.data.thread.ThreadGetPreparedStatement;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

abstract class DatabaseConnector {
    protected static Connection connection;

    private static Properties constDB = new Properties();
    private static Properties configs = new Properties();


    // Create connection
    static {
        // Загрузка Properties
        try {
            configs.load(DatabaseConnector.class.getResourceAsStream("/sample/assets/db_properties/configs.properties"));
            constDB.load(DatabaseConnector.class.getResourceAsStream("/sample/assets/db_properties/DB.properties"));

        } catch (IOException e) {
            e.printStackTrace();
        }


        String connectionString = String.format("jdbc:mysql://%s:%s/%s?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                getConfigs("dbHost"), getConfigs("dbPort"), getConfigs("dbName"));

        // Class.forName("com.mysql.cj.jdbc.driver");

        try {
            connection = DriverManager.getConnection(connectionString, getConfigs("dbUser"), getConfigs("dbPass"));
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private static String getConfigs(String key) {
        return configs.getProperty(key);
    }

    protected static String getConstDB(String key) {
        return constDB.getProperty(key);
    }


    // Закрытие соединений
    public static void closeConnection() {
        try {
            if (connection != null)
                connection.close();



        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

}
