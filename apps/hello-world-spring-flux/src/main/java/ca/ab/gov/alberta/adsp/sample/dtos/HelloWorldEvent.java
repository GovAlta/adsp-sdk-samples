package ca.ab.gov.alberta.adsp.sample.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HelloWorldEvent {
  public static final String EventName = "hello-world-event";

  @JsonProperty
  private String fromUserId;

  @JsonProperty
  private String fromUser;
  @JsonProperty
  private String message;
  @JsonProperty
  private String response;


  public String getFromUserId() {
    return fromUserId;
  }

  public void setFromUserId(String fromUserId) {
    this.fromUserId = fromUserId;
  }

  public String getFromUser() {
    return fromUser;
  }

  public void setFromUser(String fromUser) {
    this.fromUser = fromUser;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getResponse() {
    return response;
  }

  public void setResponse(String response) {
    this.response = response;
  }
  
  public HelloWorldEvent(String fromUserId, String fromUser, String message, String response) {
    this.fromUserId = fromUserId;
    this.fromUser = fromUser;
    this.message = message;
    this.response = response;
  }
}
