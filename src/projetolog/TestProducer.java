package projetolog;

import javax.jms.*;
import javax.naming.InitialContext;

public class TestProducer {

    public static void main(String[] args) throws Exception {
        InitialContext ctx = new InitialContext();
        QueueConnectionFactory cf = (QueueConnectionFactory) ctx.lookup("ConnectionFactory");
        QueueConnection conexao = cf.createQueueConnection();
        conexao.start();

        QueueSession sessao = conexao.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue fila = (Queue) ctx.lookup("LOG");

        MessageProducer producer = (MessageProducer) sessao.createProducer(fila);


//        for (int i = 0; i < 1000 ; i++) {
            Message message = sessao.createTextMessage("INFO | Transport Connection to: tcp://127.0.0.1:59526 failed: java.net.SocketException: Connection reset");
            producer.send(message, DeliveryMode.NON_PERSISTENT, 3, 800000);

            message = sessao.createTextMessage("warn | Transport Connection to: tcp://127.0.0.1:59526 failed: java.net.SocketException: Connection reset");
            producer.send(message, DeliveryMode.NON_PERSISTENT, 5, 800000);

            message = sessao.createTextMessage("WARN | Transport Connection to: tcp://127.0.0.1:59526 failed: java.net.SocketException: Connection reset");
            producer.send(message, DeliveryMode.NON_PERSISTENT, 7, 800000);

            message = sessao.createTextMessage("ERROR | Transport Connection to: tcp://127.0.0.1:59526 failed: java.net.SocketException: Connection reset");
            producer.send(message, DeliveryMode.NON_PERSISTENT, 9, 800000);
//        }

        // new Scanner(System.in).nextLine();

        sessao.close();
        conexao.close();
        ctx.close();
    }
}
