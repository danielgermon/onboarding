package com.thg.voyager.personalisation.service;

import com.thg.voyager.personalisation.entity.Order;
import com.thg.voyager.personalisation.entity.Personalisation;
import com.thg.voyager.personalisation.exception.NotFoundException;
import com.thg.voyager.personalisation.repository.PersonalisationRepository;
import com.thg.voyager.personalisation.repository.OrderRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class OrderService {
  @Inject
  private OrderRepository orderRepository;
  @Inject
  private PersonalisationRepository personalisationRepository;

  public Order getOrder(UUID orderId) throws NotFoundException {
    Optional<Order> orderOptional = orderRepository.findById(orderId);
    if (!orderOptional.isPresent()) {
      throw new NotFoundException("Product not found for id: " + orderId);
    }
    return orderOptional.get();
  }

  @Transactional
  public Order createOrder(String product, Timestamp currentTimestamp, Map<Personalisation.PersonalisationType, String> personalisation) {
    Order order = new Order();
    order.setProduct(product);
    order.setTimestamp(currentTimestamp);
    order = orderRepository.save(order);

    for (Map.Entry<Personalisation.PersonalisationType, String> entry : personalisation.entrySet()) {
      Personalisation p = new Personalisation();
      p.setPersonalisationType(entry.getKey());
      p.setPersonalisationValue(entry.getValue());
      p.setOrder(order);
      personalisationRepository.save(p);
    }
    return order;
  }

  public List<Order> getAllOrders() {
    return orderRepository.findAll();
  }

  public void delete(UUID id) {
    orderRepository.deleteById(id);
  }
}
