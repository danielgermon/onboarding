package com.thg.voyager.personalisation.integration;

import com.thg.voyager.personalisation.dto.OrderDTO;
import com.thg.voyager.personalisation.entity.Order;
import com.thg.voyager.personalisation.entity.Personalisation;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.micronaut.test.support.TestPropertyProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.CockroachContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.assertj.core.api.Assertions.*;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@MicronautTest
public class ServiceTest implements TestPropertyProvider {
  @Inject
  @Client("/")
  HttpClient client;

  static CockroachContainer cockroachContainer =
      new CockroachContainer(DockerImageName.parse("cockroachdb/cockroach").withTag("latest"))
          .withExposedPorts(26257);

  static {
    cockroachContainer.start();
  }

  @NonNull
  @Override
  public Map<String, String> getProperties() {
    return Map.of(
        "flyway.datasources.default.enabled", "true",
        "datasources.default.url", cockroachContainer.getJdbcUrl(),
        "datasources.default.username", cockroachContainer.getUsername(),
        "datasources.default.password", cockroachContainer.getPassword(),
        "jpa.default.database-platform", "org.hibernate.dialect.CockroachDB201Dialect",
        "jpa.default.properties.hibernate.globally_quoted_identifiers", "true"
    );
  }

  @Test
  public void testGetProducts() {
    UUID uuid1 = UUID.fromString("4e2f5386-9ffd-4f0f-9bea-6b06c0fac239");
    UUID uuid2 = UUID.fromString("2f78fe8d-6d00-4e4e-82fb-b7cf00aeb274");
    HttpRequest<String> request = HttpRequest.GET("/api/orders");
    List<OrderDTO> body =
        client.toBlocking().retrieve(request, Argument.of(List.class, OrderDTO.class));

    List<OrderDTO> expectedResponse = List.of(new OrderDTO(uuid1, "test1", null, Map.of(
        Personalisation.PersonalisationType.MAIN, "Main label")), new OrderDTO(uuid2, "test2", null,
        Map.of(Personalisation.PersonalisationType.MAIN, "Main label",
            Personalisation.PersonalisationType.SUB, "Sub label")));

    assertNotNull(body);
    assertThat(body).hasSameElementsAs(expectedResponse);
  }
}
