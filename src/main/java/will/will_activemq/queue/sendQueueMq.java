package will.will_activemq.queue;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnectionFactory;

public class sendQueueMq {
	
	public static void main(String[] args) throws  Exception {
		
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://182.61.21.180:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		
		Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
		
		Destination destination = session.createQueue("will--05");
		
		MessageProducer producer = session.createProducer(destination);
		
		for (int i = 0 ; i < 3 ; i++) {
			//1 普通文本
//			TextMessage message = session.createTextMessage("message===33311" + i);

			//2 map集合
//			MapMessage mapMessage = session.createMapMessage();
//			mapMessage.setBoolean("isTruw",true);
//			mapMessage.setString("name","张召");
//			mapMessage.setDouble("dubbo",1.09);

			//2.2 map
			MapMessage mapMessage = session.createMapMessage();
			mapMessage.setString("name"+i,"value"+i);

			producer.send(mapMessage);
		}
		
		session.commit();
		session.close();
		connection.close();
		
	}

}
