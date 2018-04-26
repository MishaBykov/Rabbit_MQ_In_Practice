import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv {
    private final static String QUEUE_NAME = "hello1";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        System.out.println("Waiting for message");

        boolean checkMail = false;
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                MessageFTP messageFTP = (MessageFTP) Send.convertBytesToObject(body);
                System.out.println(messageFTP.getPath() + "<----");

                long deliveryTag = envelope.getDeliveryTag();

                boolean confirm = false;
                channel.basicReject( deliveryTag, confirm );


            }
        };

        channel.basicConsume(QUEUE_NAME, checkMail, consumer);
    }
}
