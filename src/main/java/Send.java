import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.*;
import java.util.concurrent.TimeoutException;

public class Send {
    private final static String QUEUE_NAME = "hello1";

    public static void main(String[] args) throws IOException, TimeoutException {

        MessageFTP messageFTP = new MessageFTP();
        messageFTP.setPath("path/path/path22222222222");

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        boolean durable = true;
        channel.queueDeclare(QUEUE_NAME, durable, false, false, null);

        for(int i =0; i < 1000; i++)
        channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_BASIC, convertObjectToByte(messageFTP));

        System.out.println(messageFTP.getPath());
        channel.close();
        connection.close();
    }


    private static byte[] convertObjectToByte(MessageFTP message) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out;
        byte[] bytes = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(message);
            out.flush();
            bytes = bos.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return bytes;
    }

    public static Object convertBytesToObject(byte[] bytes) {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInput in = null;
        Object o = null;
        try {
            in = new ObjectInputStream(bis);
            o = in.readObject();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                // ignore close exception
            }
        }
        return o;
    }
}
