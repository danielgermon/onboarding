package com.thg.voyager.personalisation.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Personalisation {
  public enum PersonalisationType {
    MAIN,
    SUB
  }

  @Id
  @GeneratedValue
  @Getter @Setter private UUID id;
  @ManyToOne
  @JoinColumn(name = "order_id", nullable = false)
  @Getter @Setter private Order order;
  @Getter @Setter private PersonalisationType personalisationType;
  @Getter @Setter private String personalisationValue;

  public Personalisation() {

  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Personalisation that = (Personalisation) o;
    return Objects.equals(id, that.id) &&
        personalisationType == that.personalisationType &&
        Objects.equals(personalisationValue, that.personalisationValue);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, personalisationType, personalisationValue);
  }

  public Personalisation(UUID id, Order order,
                         PersonalisationType personalisationType,
                         String personalisationValue) {
    this.id = id;
    this.order = order;
    this.personalisationType = personalisationType;
    this.personalisationValue = personalisationValue;
  }
}
