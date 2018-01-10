
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Sender02 {
	private static final int SEND_NUMBER = 10;
	
	public static void main(String[] args) {
		
		//ConnectionFactory是连接工厂，jms用它创建连接
		ConnectionFactory connectionFactory;
		
		//Connection  jms客户端到jms  provider的连接
		Connection connection = null;
		
		//一个发送或者接受消息的线程
		Session session;
		
		//Destination 消息发送目的地，消息发送给谁接收
		Destination destination;
		
		//MessageProducer 消息发送者
		MessageProducer messageProducer;
		
		//构造ConnectionFactory实例对象，此处采用ActiveMQ的实现jar
		connectionFactory = new ActiveMQConnectionFactory(
				ActiveMQConnection.DEFAULT_USER,
				ActiveMQConnection.DEFAULT_PASSWORD,
				"tcp://localhost:61617");//原端口号为61616
		
		try {
			//构造工厂得到连接对象
			connection = connectionFactory.createConnection();
			
			//启动
			connection.start();
			
			//获取操作连接
			session = connection.createSession(
					Boolean.TRUE,Session.AUTO_ACKNOWLEDGE );
			//创建一个Queue，名为HelloWorld
			destination = session.createQueue("Hello_World");
		
			//得到消息生产者【发送者】
			messageProducer = session.createProducer(destination);
			
			//设置不持久化，根据实际情况而定
			messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
			
			//构造消息，此处写死，项目就是方法参数或者方法获取
			sendMessage(session,messageProducer);
			
			//提交
			session.commit();
			
		} catch (JMSException e) {
			e.printStackTrace();
		}finally{
			if(connection != null){
				try {
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void sendMessage(Session session
			,MessageProducer producer) throws JMSException{
		for(int i=0;i<=SEND_NUMBER;i++){
			TextMessage message = session.createTextMessage(
					"ActiveMQ发送的消息："+i);
			System.out.println("发送消息："+"ActiveMQ发送的消息："+i);
			producer.send(message);
		}
	}
	
}
