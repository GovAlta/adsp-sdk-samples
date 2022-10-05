package ca.ab.gov.alberta.adsp.sample;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import ca.ab.gov.alberta.adsp.sdk.AdspConfiguration.Builder;
import ca.ab.gov.alberta.adsp.sdk.events.DomainEventDefinition;
import ca.ab.gov.alberta.adsp.sdk.registration.ConfigurationDefinition;
import ca.ab.gov.alberta.adsp.sdk.registration.ServiceRole;
import ca.ab.gov.alberta.adsp.sample.dtos.HelloWorldConfiguration;
import ca.ab.gov.alberta.adsp.sample.dtos.HelloWorldEvent;
import ca.ab.gov.alberta.adsp.sdk.AdspConfigurationSupport;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
class HelloWorldAdspConfiguration extends AdspConfigurationSupport {

  @Override
  protected Builder customize(Builder builder) {

    return builder.withDisplayName("Hello world service")
        .withDescription("Hello world sample for Spring Boot with ADSP SDK.")
        .register(
            registration -> registration.withConfiguration(
                new ConfigurationDefinition<HelloWorldConfiguration>(
                    "Configuration of the hello world sample service.") {
                })
                .withRoles(
                    new ServiceRole(
                        ServiceRoles.HelloWorlder,
                        "Hello worlder role that allows user to post a message to the API."))
                .withEvents(
                    new DomainEventDefinition<HelloWorldEvent>(HelloWorldEvent.EventName,
                        "Signalled when a hello world message is posted to the API.") {

                    }));
  }
}
