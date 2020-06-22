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
        //创建一个type=“direct”、持久化的、非自动删除的交换器
        channel.exchangeDeclare(EXCHANGE_NAME, "direct", true, false, null);
        //创建一个持久化，非排他的、非自动删除的队列
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        //通过路由键绑定一个交换机和一个队列
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
        String message = "Hello World";
        channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN,
                message.getBytes());
        //关闭资源
        channel.close();
        connection.close();
    }
}
