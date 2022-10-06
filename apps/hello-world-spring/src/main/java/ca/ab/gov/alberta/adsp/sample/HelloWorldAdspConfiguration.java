package ca.ab.gov.alberta.adsp.sample;

import org.springframework.context.annotation.Configuration;

import ca.ab.gov.alberta.adsp.sdk.AdspConfiguration.Builder;
import ca.ab.gov.alberta.adsp.sdk.events.DomainEventDefinition;
import ca.ab.gov.alberta.adsp.sdk.registration.ConfigurationDefinition;
import ca.ab.gov.alberta.adsp.sdk.registration.ServiceRole;
import ca.ab.gov.alberta.adsp.sample.dtos.HelloWorldConfiguration;
import ca.ab.gov.alberta.adsp.sample.dtos.HelloWorldEvent;
import ca.ab.gov.alberta.adsp.sdk.AdspConfigurationSupport;

// Initialization of ADSP SDK components...
@Configuration
class HelloWorldAdspConfiguration extends AdspConfigurationSupport {

  // 1. Override customize to configure ADSP via configuration builder.
  // 2. By default configuration values are bound to properties with an 'adsp' prefix.
  @Override
  protected Builder customize(Builder builder) {

    // 3. Provide configuration of the service.
    // 3a. Configure basic service information.
    return builder.withDisplayName("Hello world service")
        .withDescription("Hello world sample for Spring Boot with ADSP SDK.")
        .register(
            // 3b. Register configuration definition.
            registration -> registration.withConfiguration(
                new ConfigurationDefinition<HelloWorldConfiguration>(
                    "Configuration of the hello world sample service.") {
                })
                // 3c. Register service roles.
                .withRoles(
                    new ServiceRole(
                        ServiceRoles.HelloWorlder,
                        "Hello worlder role that allows user to post a message to the API."))
                // 3b. Register domain events.
                .withEvents(
                    new DomainEventDefinition<HelloWorldEvent>(HelloWorldEvent.EventName,
                        "Signalled when a hello world message is posted to the API.") {
                    }));
  }
}
