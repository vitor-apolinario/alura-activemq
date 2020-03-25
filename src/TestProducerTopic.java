import javax.jms.*;
import javax.naming.InitialContext;

public class TestProducerTopic {

    public static void main(String[] args) throws Exception {
        InitialContext ctx = new InitialContext();
        TopicConnectionFactory cf = (TopicConnectionFactory) ctx.lookup("ConnectionFactory");
        TopicConnection conexao = cf.createTopicConnection("user", "senha");
        conexao.start();

        TopicSession sessao = conexao.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topico = (Topic) ctx.lookup("loja");

        MessageProducer producer = (MessageProducer) sessao.createProducer(topico);


        for (int i = 0; i < 5 ; i++) {
            Message message = sessao.createTextMessage(i % 2 == 0 ? "ebook" : "not a ebook");

            message.setBooleanProperty("ebook", (i % 2 == 0));

            producer.send(message);
        }

        // new Scanner(System.in).nextLine();

        sessao.close();
        conexao.close();
        ctx.close();
    }
}
