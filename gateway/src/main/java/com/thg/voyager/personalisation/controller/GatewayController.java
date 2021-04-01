package com.thg.voyager.personalisation.controller;

import com.thg.voyager.personalisation.OrdersClient;
import com.thg.voyager.personalisation.dto.OrderDTO;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.uri.UriBuilder;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.UUID;

@Controller("/order")
public class GatewayController {
  @Inject
  private OrdersClient ordersClient;

  @Post(produces = MediaType.APPLICATION_JSON)
  public HttpResponse<UUID> createOrder(@Body OrderDTO orderDTO) {
    ordersClient.newOrder(orderDTO);
    return HttpResponse.ok();
  }

}
