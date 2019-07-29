package com.kion.bunga.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kion.bunga.domain.PaymentDTO;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.stereotype.Service;


@Service
public class QueueServiceImpl {

  private final ConnectionFactory connectionFactory;
  private Channel channel;

  public QueueServiceImpl(ConnectionFactory connectionFactory) {
    this.connectionFactory = connectionFactory;
    channel = createNewChannel();
  }


  private Channel createNewChannel(){
    if (connectionFactory == null){
      return null;
    }
    try {
      Connection connection = connectionFactory.newConnection();
      return connection.createChannel();
    } catch (Exception e) {
      e.printStackTrace();
      //TODO log
      return null;
    }
  }

  public void send(PaymentDTO paymentDTO){
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      channel.basicPublish("", "payments", null, objectMapper.writeValueAsString(paymentDTO).getBytes());
    } catch (Exception e) {
      e.printStackTrace();
      //TODO put in local cache if rabbit is not working ???
    }

    //TODO recheck if payment was registered ?????
  }




//  public RabbitMQConsumer(RabbitConfig config, String queueName, Class<T> tClass) {
//    super(config, queueName, tClass);
//    messageConverter = config.getMessageConverter();
//
//    try {
//
//      connection = config.getConnection();
//
//      channel = connection.createChannel();
//
//      final RabbitMQConsumer self = this;
//
//      DeliverCallback deliverCallback = new DeliverCallback() {
//        public void handle(String consumerTag, Delivery delivery) throws IOException {
//          String message = new String(delivery.getBody(), "UTF-8");
//          T obj = messageConverter.deserialize(message, tClass);
//          self.onMessage(obj);
//        }
//      };
//      channel.basicConsume(queueName, true, deliverCallback, new CancelCallback() {
//        public void handle(String consumerTag) throws IOException {
//          System.out.println("cancelled " + consumerTag);
//        }
//      });
//
//
//
//    } catch (Exception e) {
//      logger.error("Failed to bind consumer", e);
//    }
//
//
//  }

}
