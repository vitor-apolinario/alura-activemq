import javax.jms.*;
import javax.naming.InitialContext;
import java.util.Scanner;

public class TestConsumer {

    public static void main(String[] args) throws Exception {
        InitialContext context = new InitialContext();

        //importe do package javax.jms

        ConnectionFactory cf = (ConnectionFactory)context.lookup("ConnectionFactory");
        Connection conexao = cf.createConnection();

        conexao.start();

        Session session = conexao.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination fila = (Destination) context.lookup("financeiro");
        MessageConsumer consumer = session.createConsumer(fila);

        Message message = consumer.receive();
        System.out.println("Recebendo msg: " + message);

        session.close();

        new Scanner(System.in).nextLine();

        conexao.close();
        context.close();
    }
}
