package com.thg.voyager.personalisation.controller;

import com.thg.voyager.personalisation.dto.OrderDTO;
import com.thg.voyager.personalisation.dto.OrderTransformer;
import com.thg.voyager.personalisation.entity.Order;
import com.thg.voyager.personalisation.exception.NotFoundException;
import com.thg.voyager.personalisation.service.OrderService;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.uri.UriBuilder;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Controller("/api/orders")
public class OrderController {
  @Inject
  private OrderService orderService;

  @Get(value = "/{id}", produces = MediaType.APPLICATION_JSON)
  public HttpResponse<OrderDTO> getOrderForId(@PathVariable UUID id) {
    try {
      Order order = orderService.getOrder(id);
      return HttpResponse.ok(new OrderTransformer().transformToDTO(order));
    } catch (NotFoundException e) {
      return HttpResponse.notFound();
    }
  }


  @Get()
  public HttpResponse<List<OrderDTO>> getAllOrders() {
    List<Order> orders = orderService.getAllOrders();
    return HttpResponse.ok(new OrderTransformer().transformToDTOList(orders));
  }

  @Delete(value = "/{id}")
  public HttpResponse deleteOrder(@PathVariable UUID id) {
    orderService.delete(id);
    return HttpResponse.noContent();
  }
}
