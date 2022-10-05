package ca.ab.gov.alberta.adsp.sample.controllers;

import java.time.Instant;

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

@RestController
class HelloController {
  private EventService eventService;

  public HelloController(EventService eventService) {
    this.eventService = eventService;
  }

  @PreAuthorize("hasRole('" + ServiceRoles.HelloWorlder + "')")
  @PostMapping("/hello-world/v1/hello")
  public String hello(@RequestBody HelloWorldMessage message) {
    var context = AdspRequestContextHolder.current();
    var user = context.getUser();
    var configuration = context.getConfiguration(HelloWorldConfiguration.TypeReference).blockOptional();

    var response = "Hello World";
    if (configuration != null && configuration.get() != null && message != null) {
      var responses = configuration.get().getResponses();

      if (responses != null) {
        response = responses.getOrDefault(message.getMessage(), "Hello World!");
      }
    }

    eventService.send(new DomainEvent<HelloWorldEvent>(
        HelloWorldEvent.EventName,
        Instant.now(),
        new HelloWorldEvent(
            user.getId(),
            user.getName(),
            message != null ? message.getMessage() : null,
            response)))
        .blockOptional();

    return response;
  }
}
