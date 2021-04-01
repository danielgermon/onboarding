package com.thg.voyager.personalisation.client;

import com.thg.voyager.personalisation.dto.OrderDTO;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;

import java.util.List;

@Client(id = "order-manager")
public interface OrderManagerClient {

  @Get("/api/orders")
  List<OrderDTO> fetchOrders();

}
