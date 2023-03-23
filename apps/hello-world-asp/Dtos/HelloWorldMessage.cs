using System.Text.Json.Serialization;

namespace AdspSdkSamples.HelloWorldAsp;

public class HelloWorldMessage
{
  [JsonPropertyName("message")]
  public string? Message { get; set; }
}
