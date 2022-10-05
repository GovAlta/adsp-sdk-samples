using System.Text.Json.Serialization;

namespace AdspSdkSamples.HelloWorldAsp;

public class HelloWorldConfiguration {
  [JsonPropertyName("responses")]
  public Dictionary<string, string>? Responses { get; set; }
}
