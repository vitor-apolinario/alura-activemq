import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.naming.InitialContext;
import java.util.Scanner;

public class TestConsumer {

    public static void main(String[] args) throws Exception {
        InitialContext context = new InitialContext();

        //importe do package javax.jms

        ConnectionFactory cf = (ConnectionFactory)context.lookup("ConnectionFactory");
        Connection conexao = cf.createConnection();

        conexao.start();

        new Scanner(System.in).nextLine();

        conexao.close();
        context.close();
    }
}
