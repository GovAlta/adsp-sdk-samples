using System.Text.Json.Serialization;

namespace AdspSdkSamples.HelloWorldAsp;

public class HelloWorldEvent
{
  public const string EventName = "hello-world-event";

  [JsonPropertyName("fromUserId")]
  public string? FromUserId { get; set; }


  [JsonPropertyName("fromUser")]
  public string? FromUser { get; set; }

  [JsonPropertyName("message")]
  public string? Message { get; set; }

  [JsonPropertyName("response")]
  public string? Response { get; set; }
}
