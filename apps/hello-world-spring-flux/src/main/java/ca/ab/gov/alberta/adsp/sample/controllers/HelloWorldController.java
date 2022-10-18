package ca.ab.gov.alberta.adsp.sample.controllers;

import java.time.Instant;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.ab.gov.alberta.adsp.sample.ServiceRoles;
import ca.ab.gov.alberta.adsp.sample.dtos.HelloWorldConfiguration;
import ca.ab.gov.alberta.adsp.sample.dtos.HelloWorldEvent;
import ca.ab.gov.alberta.adsp.sample.dtos.HelloWorldMessage;
import ca.ab.gov.alberta.adsp.sdk.AdspRequestContextHolder;
import ca.ab.gov.alberta.adsp.sdk.events.DomainEvent;
import ca.ab.gov.alberta.adsp.sdk.events.EventService;
import reactor.core.publisher.Mono;

@RestController
class HelloController {
  private EventService eventService;

  public HelloController(EventService eventService) {
    // Inject the event service.
    this.eventService = eventService;
  }

  @PreAuthorize("hasRole('" + ServiceRoles.HelloWorlder + "')")
  @PostMapping("/hello-world/v1/hello")
  public Mono<String> hello(@RequestBody HelloWorldMessage message) {

    return AdspRequestContextHolder.getCurrent()
        .flatMap(context -> {
          // Retrieve configuration from the request context; configuration of this
          // service for current tenant is returned.
          var configuration = context.getConfiguration(HelloWorldConfiguration.TypeReference)
              .map(c -> Optional.of(c))
              .switchIfEmpty(Mono.just(Optional.empty()));
          return Mono.zip(Mono.just(context), configuration);
        })
        .flatMap(values -> {
          var context = values.getT1();
          var configuration = values.getT2();

          var response = "Hello World";
          if (configuration.isPresent() && message != null) {
            var responses = configuration.get().getResponses();

            if (responses != null) {
              response = responses.getOrDefault(message.getMessage(), "Hello World!");
            }
          }

          // Retrieve user information from the request context;
          var user = context.getUser();

          // Send a domain event via the event service.
          return eventService.send(new DomainEvent<HelloWorldEvent>(
              HelloWorldEvent.EventName,
              Instant.now(),
              new HelloWorldEvent(
                  user.getId(),
                  user.getName(),
                  message != null ? message.getMessage() : null,
                  response)))
              .thenReturn(response);
        });
  }
}
