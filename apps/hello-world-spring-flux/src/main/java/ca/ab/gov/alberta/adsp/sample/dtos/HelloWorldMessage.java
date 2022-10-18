package ca.ab.gov.alberta.adsp.sample.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HelloWorldMessage {

  @JsonProperty
  private String message;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
