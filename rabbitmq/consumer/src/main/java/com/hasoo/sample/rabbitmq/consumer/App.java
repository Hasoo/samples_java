package com.hasoo.sample.rabbitmq.consumer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * Hello world!
 *
 */
public class App {
  private static Logger logger = LoggerFactory.getLogger(App.class);

  public static void main(String[] args) throws IOException, TimeoutException {
    final String propFile = "application.properties";

    InputStream inputStream = App.class.getClassLoader().getResourceAsStream(propFile);

    Properties prop = new Properties();
    prop.load(inputStream);

    String serverIp = prop.getProperty("rabbitmq.ip");
    int serverPort = Integer.parseInt(prop.getProperty("rabbitmq.port"));
    String userName = prop.getProperty("rabbitmq.id");
    String password = prop.getProperty("rabbitmq.password");
    String exchangeName = prop.getProperty("rabbitmq.exchange_name");
    String routingKey = prop.getProperty("rabbitmq.routing_key");
    String queueName = prop.getProperty("rabbitmq.queue_name");

    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost(serverIp);
    factory.setPort(serverPort);
    factory.setUsername(userName);
    factory.setPassword(password);
    Connection connection = factory.newConnection();
    final Channel channel = connection.createChannel();

    System.out.println("connected");

    boolean autoAck = false;
    channel.basicConsume(queueName, autoAck, new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope,
          AMQP.BasicProperties properties, byte[] body) throws IOException {
        String message = new String(body, "UTF-8");
        logger.info("received message:" + message);
        channel.basicAck(envelope.getDeliveryTag(), false);
      }
    });

    System.out.println("last");
  }
}
