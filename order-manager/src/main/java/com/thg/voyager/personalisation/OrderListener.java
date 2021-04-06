package com.thg.voyager.personalisation;

import com.thg.voyager.personalisation.dto.OrderDTO;
import com.thg.voyager.personalisation.service.OrderService;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.rabbitmq.annotation.Queue;
import io.micronaut.rabbitmq.annotation.RabbitListener;

import javax.inject.Inject;
import java.sql.Timestamp;

@Requires(notEnv = Environment.TEST)
@RabbitListener
public class OrderListener {
  @Inject
  private OrderService orderService;

  @Queue("orders")
  public void newOrder(OrderDTO orderDTO) {
    orderService
        .createOrder(orderDTO.getProduct(), new Timestamp(System.currentTimeMillis()), orderDTO.getPersonalisation());
  }
}
