package com.thg.voyager.personalisation.controller;

import com.thg.voyager.personalisation.client.OrderManagerClient;
import com.thg.voyager.personalisation.dto.OrderDTO;
import io.micronaut.core.util.CollectionUtils;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.retry.annotation.Recoverable;
import io.micronaut.views.View;

import javax.inject.Inject;
import java.util.List;

@Controller("/admin")
public class AdminController {

  @Inject
  OrderManagerClient orderManagerClient;

  @View("apps-ecommerce-orders")
  @Get
  public HttpResponse getOrderView() {
    List<OrderDTO> orderDTOS = orderManagerClient.fetchOrders();
    return HttpResponse.ok(CollectionUtils.mapOf("orders", orderDTOS));
  }
}
