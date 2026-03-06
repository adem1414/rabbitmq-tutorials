import com.rabbitmq.client.*;

import java.util.HashMap;
import java.util.Map;

public class ReceiveLogHeader {

    private static final String EXCHANGE_NAME = "header_test";

    public static void main(String[] argv) throws Exception {
        if (argv.length < 1) {
            System.err.println("Usage: ReceiveLogHeader [bindings]");
            System.exit(1);
        }

        Connection connection = ConnectionManager.createConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.HEADERS);

        String queueName = channel.queueDeclare().getQueue();

        // The rest of the arguments are key value header pairs. For the purpose of this
        // example, we are assuming they are all strings, but that is not required by RabbitMQ
        // The implementation uses broad match, where any of the headers is enough to match
        Map<String, Object> headers = new HashMap<>();
        for (int i = 0; i < argv.length; i++) {
            headers.put(argv[i], "");
        }
        headers.put("x-match", "any"); // Match any of the headers

        channel.queueBind(queueName, EXCHANGE_NAME, "", headers);

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }
}
