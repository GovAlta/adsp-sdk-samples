package ca.ab.gov.alberta.adsp.sample.dtos;

import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HelloWorldConfiguration {
  public static final ParameterizedTypeReference<HelloWorldConfiguration> TypeReference = new ParameterizedTypeReference<HelloWorldConfiguration>() {
  };
  
  @JsonProperty
  private Map<String, String> responses;

  public Map<String, String> getResponses() {
    return responses;
  }

  public void setResponses(Map<String, String> responses) {
    this.responses = responses;
  }
}
