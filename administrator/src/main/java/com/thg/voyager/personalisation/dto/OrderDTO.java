package com.thg.voyager.personalisation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thg.voyager.personalisation.Types.PersonalisationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.sql.Timestamp;
import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
  @JsonProperty("id")
  UUID id;
  @JsonProperty("product")
  String product;
  @JsonProperty("timestamp")
  Timestamp timestamp;
  @JsonProperty("personalisation")
  Map<PersonalisationType, String> personalisation;

}
