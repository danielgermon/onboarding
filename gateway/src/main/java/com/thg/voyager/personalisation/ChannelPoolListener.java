package com.thg.voyager.personalisation;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import io.micronaut.rabbitmq.connect.ChannelInitializer;

import javax.inject.Singleton;
import java.io.IOException;

@Singleton
public class ChannelPoolListener extends ChannelInitializer {
  @Override
  public void initialize(Channel channel) throws IOException {
    channel.exchangeDeclare("micronaut", BuiltinExchangeType.DIRECT, true);
    channel.queueDeclare("orders", true, false, false, null);
    channel.queueBind("orders", "micronaut", "orders");
  }
}
