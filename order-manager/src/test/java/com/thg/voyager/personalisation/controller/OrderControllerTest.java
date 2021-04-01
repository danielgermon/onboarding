package com.thg.voyager.personalisation.controller;

import com.thg.voyager.personalisation.entity.Order;
import com.thg.voyager.personalisation.entity.Personalisation;
import com.thg.voyager.personalisation.exception.NotFoundException;
import com.thg.voyager.personalisation.service.OrderService;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@MicronautTest(propertySources = "test.yml")
public class OrderControllerTest {
  @Inject
  @Client("/")
  HttpClient client;

  @Inject
  OrderService orderService;

  @MockBean(OrderService.class)
  OrderService orderService() {
    return mock(OrderService.class);
  }

  @Test
  public void testGetProducts() {
    UUID uuid1 = UUID.randomUUID();
    UUID uuid2 = UUID.randomUUID();
    when(orderService.getAllOrders()).thenReturn(List.of(new Order(uuid1, "Test1", new Timestamp(10),
        List.of(
            new Personalisation(UUID.randomUUID(), null, Personalisation.PersonalisationType.MAIN,
                "Main label"))), new Order(uuid2, "Test2", new Timestamp(11),
        List.of(
            new Personalisation(UUID.randomUUID(), null, Personalisation.PersonalisationType.MAIN,
                "Main label"), new Personalisation(UUID.randomUUID(), null,
                Personalisation.PersonalisationType.SUB, "Sub label")))));
    HttpRequest<String> request = HttpRequest.GET("/api/orders");
    String body = client.toBlocking().retrieve(request);

    assertNotNull(body);
    assertEquals("[{\"id\":\"" + uuid1 +
        "\",\"product\":\"Test1\",\"timestamp\":10,\"personalisation\":{\"MAIN\":\"Main label\"}},{\"id\":\"" + uuid2 +
        "\",\"product\":\"Test2\",\"timestamp\":11,\"personalisation\":{\"MAIN\":\"Main label\",\"SUB\":\"Sub label\"}}]", body);
  }

  @Test
  public void testGetProduct() throws NotFoundException {
    UUID uuid1 = UUID.randomUUID();
    when(orderService.getOrder(uuid1)).thenReturn(new Order(uuid1, "Test1", new Timestamp(10),
        List.of(
            new Personalisation(UUID.randomUUID(), null, Personalisation.PersonalisationType.MAIN,
                "Main label"))));
    HttpRequest<String> request = HttpRequest.GET("/api/orders/" + uuid1);
    String body = client.toBlocking().retrieve(request);
    assertEquals("{\"id\":\"" + uuid1 +
        "\",\"product\":\"Test1\",\"timestamp\":10,\"personalisation\":{\"MAIN\":\"Main label\"}}", body);
  }

  @Test
  public void testGetProductNotFound() throws NotFoundException {
    when(orderService.getOrder(any(UUID.class))).thenThrow(NotFoundException.class);
    HttpRequest<String> request = HttpRequest.GET("/api/orders/" + UUID.randomUUID());
    assertThrows(HttpClientResponseException.class, () -> client.toBlocking().retrieve(request));
  }

  @Test
  public void testDeleteOrder() {
    UUID uuid1 = UUID.randomUUID();
    HttpRequest<String> request = HttpRequest.DELETE("/api/orders/" + uuid1);
    assertThrows(HttpClientResponseException.class, () -> client.toBlocking().retrieve(request));
  }


}
