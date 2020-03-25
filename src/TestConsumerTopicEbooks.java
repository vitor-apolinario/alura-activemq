import javax.jms.*;
import javax.naming.InitialContext;
import java.util.Scanner;

public class TestConsumerTopicEbooks {

    public static void main(String[] args) throws Exception {
        InitialContext ctx = new InitialContext();
        TopicConnectionFactory cf = (TopicConnectionFactory) ctx.lookup("ConnectionFactory");
        TopicConnection conexao = cf.createTopicConnection("user", "senha");
        conexao.setClientID("ebooks");
        conexao.start();

        TopicSession sessao = conexao.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topico = (Topic) ctx.lookup("loja");

        MessageConsumer consumer = (MessageConsumer) sessao.createDurableSubscriber(topico, "assinatura", "ebook=true", false);

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
