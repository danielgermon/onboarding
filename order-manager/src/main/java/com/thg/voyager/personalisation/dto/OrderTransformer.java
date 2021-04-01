package com.thg.voyager.personalisation.dto;

import com.thg.voyager.personalisation.entity.Order;
import com.thg.voyager.personalisation.entity.Personalisation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderTransformer {
  public OrderDTO transformToDTO(Order order) {
    Map<Personalisation.PersonalisationType, String> personalisationMap = new HashMap<>();
    for (Personalisation personalisation : order.getPersonalisationList()) {
      personalisationMap.put(personalisation.getPersonalisationType(), personalisation.getPersonalisationValue());
    }
    OrderDTO
        orderDTO = new OrderDTO(order.getId(), order.getProduct(), order
        .getTimestamp(), personalisationMap);
    return orderDTO;
  }

  public List<OrderDTO> transformToDTOList(List<Order> orders) {
    return orders.stream().map(this::transformToDTO).collect(Collectors.toList());
  }
}
