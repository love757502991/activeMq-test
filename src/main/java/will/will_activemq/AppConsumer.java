package will.will_activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by will on 2018/8/7.
 */
public class AppConsumer {

    public static void main(String[] args) {

        thread(new HelloWorldConsumer(), false);        
        thread(new HelloWorldConsumer(), false);
        thread(new HelloWorldConsumer(), false);

	}


    public static class HelloWorldConsumer implements Runnable, ExceptionListener {
    	

        public void run() {

            try {
                ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://182.61.21.180:61616");
                Connection connection = connectionFactory.createConnection();
                connection.start();
                connection.setExceptionListener(this);
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

                // Create the destination (Topic or Queue)
                Destination destination = session.createQueue("TEST.FOO");

                // Create a MessageConsumer from the Session to the Topic or Queue
                MessageConsumer consumer = session.createConsumer(destination);
                Message message = consumer.receive(1000);



                if (message instanceof TextMessage) {

                    TextMessage textMessage = (TextMessage) message;

                    String text = textMessage.getText();

                    System.out.println("Received: " + text);

                } else {

                    System.out.println("Received: " + message);

                }
                consumer.close();
                session.close();
                connection.close();

            } catch (Exception e) {
                System.out.println("Caught: " + e);
            }

        }



        public synchronized void onException(JMSException ex) {
            System.out.println("JMS Exception occured.  Shutting down client.");
        }

    }



    public static void thread(Runnable runnable, boolean daemon) {

        Thread brokerThread = new Thread(runnable);

        brokerThread.setDaemon(daemon);

        brokerThread.start();

    }

}
