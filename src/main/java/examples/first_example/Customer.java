package examples.first_example;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Date;


public class Customer {

    public static void main(String[] args) {

        String user = ActiveMQConnection.DEFAULT_USER;

        String password = ActiveMQConnection.DEFAULT_PASSWORD;

        String url = ActiveMQConnection.DEFAULT_BROKER_URL;

        String subject = "test.queue";

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, url);

        Connection connection;

        try {
            connection= connectionFactory.createConnection();
            connection.start();
            final Session session =connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(subject);
            MessageConsumer message = session.createConsumer(destination);
            message.setMessageListener(new MessageListener() {

                public void onMessage(Message msg){

                    MapMessage message = (MapMessage) msg;

                    try {

                        System.out.println("--receive message" + new Date() + message);

                        session.commit();

                    }catch(JMSException e) {

                        e.printStackTrace();

                    }

                }

            });
            Thread.sleep(30000);

            session.close();

            Thread.sleep(30000);

            connection.close();

            Thread.sleep(30000);


        } catch (JMSException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}