package will.will_activemq.queue;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnectionFactory;

import java.util.Enumeration;
import java.util.Map;

public class receiveQueueMq {
	
	public static void main(String[] args) throws JMSException  {
		
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://182.61.21.180:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();

		Enumeration names = connection.getMetaData().getJMSXPropertyNames();
		while (names.hasMoreElements()){
			String name = (String) names.nextElement();
			System.out.println("name" + name);
		}
		
		Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
		
		Destination destination = session.createQueue("will--05");
		
		MessageConsumer consumer = session.createConsumer(destination);
		
		int i = 0;
		while (i < 3) {

			MapMessage mapMessage = (MapMessage) consumer.receive();
			session.commit();
			System.out.println("收到的消息key为 ：【name" + i + "】");
			System.out.println("收到的消息：" + mapMessage.getString("name"+i));
			i++;
			
//			MapMessage mapMessage = (MapMessage) consumer.receive();
//            if(mapMessage == null)break;
//			System.out.println("收到的消息：" + mapMessage.getBoolean("isTrue"));
//			System.out.println("收到的消息：" + mapMessage.getString("name"));
//			System.out.println("收到的消息：" + mapMessage.getDouble("dubbo"));

//			TextMessage msg = (TextMessage)consumer.receive();
//			session.commit();
//            System.out.println("收到的消息："+msg.getText());
		}
		
		session.close();
        connection.close();
	}

}
