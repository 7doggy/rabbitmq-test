package com.lichun.demo.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitProducer {
    public static final String EXCHANGE_NAME = "exchange_demo";
    public static final String ROUTING_KEY = "routingkey_demo";
    public static final String QUEUE_NAME = "queue_demo";
    public static final String IP_ADDRESS = "localhost";
    public static final int PORT = 5672;

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setPort(PORT);
        factory.setHost(IP_ADDRESS);
        factory.setUsername("root");
        factory.setPassword("root123");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "direct", true, false, null);
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
        String message = "Hello World";
        channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN,
                message.getBytes());
        channel.close();
        connection.close();
    }
}
