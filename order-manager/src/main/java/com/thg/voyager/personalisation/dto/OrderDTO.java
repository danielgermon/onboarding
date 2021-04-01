package com.thg.voyager.personalisation.dto;

import com.thg.voyager.personalisation.entity.Personalisation;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.MappedProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Map;
import java.util.UUID;

@Value
@AllArgsConstructor
@MappedEntity(value = "order")
public class OrderDTO {
  UUID id;
  @MappedProperty(value = "product")
  String product;
  Timestamp timestamp;
  @MappedProperty(value = "personalisation")
  Map<Personalisation.PersonalisationType, String> personalisation;
}
