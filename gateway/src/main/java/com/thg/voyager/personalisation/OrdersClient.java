package com.thg.voyager.personalisation;

import com.thg.voyager.personalisation.dto.OrderDTO;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;

@RabbitClient("micronaut")
public interface OrdersClient {
  @Binding("orders")
  void newOrder(OrderDTO orderDto);
}
