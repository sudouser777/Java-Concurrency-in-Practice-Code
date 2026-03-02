package chapter03;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ThreadLocalExample {

    private static final ThreadLocal<Connection> connectionHolder = ThreadLocal.withInitial(() -> {
        try {
            return DriverManager.getConnection("");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    });

    public static Connection getConnection() {
        return connectionHolder.get();
    }
}
