package com.hasoo.sample.rabbitmq.batchsender;

import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeoutException;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Hello world!
 *
 */
public class App {
  private static Logger logger = LoggerFactory.getLogger(App.class);

  public static void main(String[] args) throws java.io.IOException, TimeoutException {
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
    Channel channel = connection.createChannel();

    String name = "Hasoo Kim";
    for (int j = 0; j < 5; j++) {
      StopWatch stopWatch = new StopWatch();
      stopWatch.start();
      for (int i = 0; i < 5000000; i++) {
//      for (int i = 0; i < 1000000; i++) {
        String buffer = name + " " + i;
        channel.basicPublish(exchangeName, routingKey, null, buffer.getBytes());
      }
      stopWatch.stop();
      logger.info(
          "sent 5,000,000 : " + stopWatch.getNanoTime() / 1000 + " microseconds");
//      logger.info(
//          "stacked " + (j + 1) + ",000,000 : " + stopWatch.getNanoTime() / 1000 + " microseconds");
    }

    channel.close();
    connection.close();
  }
}
