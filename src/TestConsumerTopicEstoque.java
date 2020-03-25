import javax.jms.*;
import javax.naming.InitialContext;
import java.util.Scanner;

public class TestConsumerTopicEstoque {

    public static void main(String[] args) throws Exception {
        InitialContext ctx = new InitialContext();
        TopicConnectionFactory cf = (TopicConnectionFactory) ctx.lookup("ConnectionFactory");
        TopicConnection conexao = cf.createTopicConnection();
        conexao.setClientID("estoque");
        conexao.start();

        TopicSession sessao = conexao.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topico = (Topic) ctx.lookup("loja");

        MessageConsumer consumer = (MessageConsumer) sessao.createDurableSubscriber(topico, "assinatura", "ebook=false", false);

        consumer.setMessageListener(new MessageListener(){

           @Override
            public void onMessage(Message message){

                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println(textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        new Scanner(System.in).nextLine();

        sessao.close();
        conexao.close();
        ctx.close();
    }
}
