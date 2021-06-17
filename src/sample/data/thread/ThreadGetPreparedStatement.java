package sample.data.thread;

import java.sql.PreparedStatement;
import java.util.concurrent.Callable;
import java.sql.*;

// Поток для работы с БД
// Используется в DatabaseConnector в методе getPreparedStatement
public class ThreadGetPreparedStatement implements Callable<PreparedStatement> {
    private String command;
    private Connection connection;

    public ThreadGetPreparedStatement(String command, Connection connection) {
        this.command = command;
        this.connection = connection;
    }

    @Override
    public PreparedStatement call() throws SQLException {

        return connection.prepareStatement(command);
    }
}
