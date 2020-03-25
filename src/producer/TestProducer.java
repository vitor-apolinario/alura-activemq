package producer;

import javax.jms.*;
import javax.naming.InitialContext;
import java.util.Scanner;

public class TestProducer {

    public static void main(String[] args) throws Exception {
        InitialContext ctx = new InitialContext();
        QueueConnectionFactory cf = (QueueConnectionFactory) ctx.lookup("ConnectionFactory");
        QueueConnection conexao = cf.createQueueConnection();
        conexao.start();

        QueueSession sessao = conexao.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue fila = (Queue) ctx.lookup("financeiro");

        MessageProducer producer = (MessageProducer) sessao.createProducer(fila);


        for (int i = 0; i < 1000 ; i++) {
            Message message = sessao.createTextMessage(i + ": vitor lindo");

            producer.send(message);
        }

        // new Scanner(System.in).nextLine();

        sessao.close();
        conexao.close();
        ctx.close();
    }
}
