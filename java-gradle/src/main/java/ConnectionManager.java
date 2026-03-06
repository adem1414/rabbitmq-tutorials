import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionManager {

    private ConnectionManager() {
    }

    public static Connection createConnection() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        return factory.newConnection();
    }

    public static Connection createConnection(String username, String password) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername(username);
        factory.setPassword(password);
        return factory.newConnection();
    }
}
