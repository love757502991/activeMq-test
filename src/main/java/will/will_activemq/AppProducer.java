package will.will_activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by will on 2018/8/7.
 */
public class AppProducer {

    public static void main(String[] args) throws Exception {

        thread(new HelloWorldProducer(), false);

         thread(new HelloWorldProducer(), false);

        Thread.sleep(1000);

        thread(new HelloWorldProducer(), false);

        thread(new HelloWorldProducer(), false);

        Thread.sleep(1000);


        thread(new HelloWorldProducer(), false);


        thread(new HelloWorldProducer(), false);

        thread(new HelloWorldProducer(), false);

        Thread.sleep(1000);

        thread(new HelloWorldProducer(), false);


        thread(new HelloWorldProducer(), false);


        thread(new HelloWorldProducer(), false);


        thread(new HelloWorldProducer(), false);

        thread(new HelloWorldProducer(), false);

    }



    public static void thread(Runnable runnable, boolean daemon) {

        Thread brokerThread = new Thread(runnable);

        brokerThread.setDaemon(daemon);

        brokerThread.start();

    }

    public static class HelloWorldProducer implements Runnable {

        public void run() {

            try {

                ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://182.61.21.180:61616");
                Connection connection = connectionFactory.createConnection();

                connection.start();
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

                // Create the destination (Topic or Queue)

                Destination destination = session.createTopic("Topic--01");
                // Create a MessageProducer from the Session to the Topic or Queue

                MessageProducer producer = session.createProducer(destination);

                producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

                // Create a messages

                String text = "Hello world! From: " + Thread.currentThread().getName() + " : " + this.hashCode();

                TextMessage message = session.createTextMessage(text);

                // Tell the producer to send the message

                System.out.println("Sent message: "+ message.hashCode() + " : " + Thread.currentThread().getName());

                producer.send(message);

                // Clean up

                session.close();

                connection.close();

            }

            catch (Exception e) {

                System.out.println("Caught: " + e);

                e.printStackTrace();

            }

        }

    }



}