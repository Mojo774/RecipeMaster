package sample.data;

import sample.data.thread.ThreadGetPreparedStatement;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class DatabaseConnector {
    protected static Connection connection;


    protected static ExecutorService service;
    protected static Properties constDB = new Properties();
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


        // Многопоточность
        service = Executors.newFixedThreadPool(2);
    }

    private static String getConfigs(String key) {
        return configs.getProperty(key);
    }

    protected static String getConstDB(String key) {
        return constDB.getProperty(key);
    }

    // Получить значения всех строк таблицы tableName
    public static ResultSet getResultSet(String tableName) {
        try {
            PreparedStatement preparedStatement = getPreparedStatement(String.format("SELECT * FROM %s", tableName));
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }


    }

    // Сбрасывает авто-инкремент у таблицы tableName
    public static void resetIncrement(String tableName) {
        String command = String.format("ALTER TABLE %s AUTO_INCREMENT = 1;", tableName);
        useCommand(command);
    }

    // Если надо просто выполнить команду
    public static void useCommand(String command) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(command);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // Если надо получить результат выполнения
    public static PreparedStatement getPreparedStatement(String command) {
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

    // Закрытие соединений
    public static void closeConnection() {
        try {
            if (connection != null)
                connection.close();

            if (service != null)
                service.shutdown();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

}
