package consumer;

import javax.jms.*;
import javax.naming.InitialContext;
import java.util.Scanner;

public class TestConsumerDLQ {

    public static void main(String[] args) throws Exception {
        InitialContext ctx = new InitialContext();
        QueueConnectionFactory cf = (QueueConnectionFactory) ctx.lookup("ConnectionFactory");
        QueueConnection conexao = cf.createQueueConnection("admin", "admin");
        conexao.start();

        QueueSession sessao = conexao.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue fila = (Queue) ctx.lookup("dlq");
        QueueReceiver receiver = (QueueReceiver) sessao.createReceiver(fila);

        // Message message = receiver.receive();

        receiver.setMessageListener(new MessageListener(){
            @Override
            public void onMessage(Message message){
                System.out.println(message);
            }
        });

        new Scanner(System.in).nextLine();

        sessao.close();
        conexao.close();
        ctx.close();
    }
}
