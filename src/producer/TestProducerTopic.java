package producer;

import br.com.caelum.modelo.Pedido;
import br.com.caelum.modelo.PedidoFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.xml.bind.JAXB;
import java.io.StringWriter;

public class TestProducerTopic {

    public static void main(String[] args) throws Exception {
        InitialContext ctx = new InitialContext();
        TopicConnectionFactory cf = (TopicConnectionFactory) ctx.lookup("ConnectionFactory");
        TopicConnection conexao = cf.createTopicConnection("user", "senha");
        conexao.start();

        TopicSession sessao = conexao.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topico = (Topic) ctx.lookup("loja");

        MessageProducer producer = (MessageProducer) sessao.createProducer(topico);

        Pedido pedido = new PedidoFactory().geraPedidoComValores();

        StringWriter stringWriter = new StringWriter();

        JAXB.marshal(pedido, stringWriter);

        String xml = stringWriter.toString();

        for (int i = 0; i < 1 ; i++) {
            Message message = sessao.createObjectMessage(pedido);

            message.setBooleanProperty("ok", true);

            producer.send(message);
        }

        // new Scanner(System.in).nextLine();

        sessao.close();
        conexao.close();
        ctx.close();
    }
}
