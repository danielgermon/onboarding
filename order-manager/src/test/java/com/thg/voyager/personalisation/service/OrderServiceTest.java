package com.thg.voyager.personalisation.service;

import com.thg.voyager.personalisation.entity.Order;
import com.thg.voyager.personalisation.entity.Personalisation;
import com.thg.voyager.personalisation.exception.NotFoundException;
import com.thg.voyager.personalisation.repository.PersonalisationRepository;
import com.thg.voyager.personalisation.repository.OrderRepository;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@MicronautTest
public class OrderServiceTest {

  @Inject
  OrderService orderService;

  @Inject
  OrderRepository orderRepository;

  @MockBean(OrderRepository.class)
  OrderRepository orderRepository() {
    return mock(OrderRepository.class);
  }

  @Inject
  PersonalisationRepository personalisationRepository;

  @MockBean(PersonalisationRepository.class)
  PersonalisationRepository personalisationRepository() {
    return mock(PersonalisationRepository.class);
  }

  @Test
  public void testGetAllOrders() {
    List<Order>
        orders = List.of(new Order(UUID.randomUUID(), "P1", new Timestamp(10), null));
    when(orderRepository.findAll()).thenReturn(orders);
    assertEquals(orders, orderService.getAllOrders());
  }

  @Test
  public void testGetSingleOrder() throws NotFoundException {
    UUID uuid1 = UUID.randomUUID();
    Order order = new Order(uuid1, null, null, null);
    when(orderRepository.findById(uuid1)).thenReturn(Optional.of(order));
    assertEquals(order, orderService.getOrder(uuid1));
  }

  @Test
  public void testGetNonExistentOrder() {
    when(orderRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
    assertThrows(NotFoundException.class, () -> orderService.getOrder(UUID.randomUUID()));
  }

  @Test
  public void testDeleteOrder() {
    UUID uuid = UUID.randomUUID();
    orderService.delete(uuid);
    verify(orderRepository, times(1)).deleteById(uuid);
  }

  @Test
  public void testCreateOrder() {
    Order
        returnedOrder = new Order(UUID.randomUUID(), "Test1", new Timestamp(10), null);
    when(orderRepository.save(any(Order.class))).thenReturn(returnedOrder);
    Order p = orderService
        .createOrder("Test1", new Timestamp(10), Map.of(Personalisation.PersonalisationType.MAIN, "Main label"));
    assertEquals(returnedOrder, orderService.createOrder("Test1", new Timestamp(10), Map.of(
        Personalisation.PersonalisationType.MAIN, "Main label")));
  }
}
