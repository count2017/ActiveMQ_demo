import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Receiver02 {
	
	public static void main(String[] args) {
		
		//ConnectionFactory连接工厂，JMS用它创建连接
		ConnectionFactory connecionFactory;
		
		//Connection jms客户端到jms  provider的连接
		Connection connection = null;
		
		//session 一个发送或者接收的线程
		Session session;
		
		//Destination 消息目的地，发送给谁接收
		Destination destination;
		
		//消费者消息接收者
		MessageConsumer messageConsumer;
		
		//
		connecionFactory = new ActiveMQConnectionFactory(
				ActiveMQConnection.DEFAULT_USER
				,ActiveMQConnection.DEFAULT_PASSWORD
				,"tcp://localhost:61617");//原端口号为61616
		
		
		try {
			//构造工厂得到连接对象
			connection = connecionFactory.createConnection();
			//启动
			connection.start();
			//获取操作连接
			session = connection.createSession(
					Boolean.FALSE,Session.AUTO_ACKNOWLEDGE );
			destination = session.createQueue("Hello_World");
			messageConsumer = session.createConsumer(destination);
			while(true){
				//设置接收者接收消息的时间，为了方便测试，这里暂定设为100s
				TextMessage message = (TextMessage) messageConsumer.receive(100);
				if(message != null){
					System.out.println("收到消息："+message.getText());
				}else{
					break;
				}
				
				
			}
			
			
		} catch (JMSException e) {
			e.printStackTrace();
		}finally {
			if(connection != null){
				try {
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
		
		
	}
	
	
}
