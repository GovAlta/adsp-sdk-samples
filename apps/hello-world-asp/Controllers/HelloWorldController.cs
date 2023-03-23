using Adsp.Sdk;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace AdspSdkSamples.HelloWorldAsp.Controllers;

[ApiController]
[Route("/hello-world/v1")]
public class HelloWorldController : ControllerBase
{
  private readonly ILogger<HelloWorldController> _logger;
  private readonly IEventService _eventService;

  public HelloWorldController(ILogger<HelloWorldController> logger, IEventService eventService)
  {
    _logger = logger;
    // Inject the event service
    _eventService = eventService;
  }

  [HttpPost]
  [Route("hello")]
  [Authorize(Roles = ServiceRoles.HelloWorlder)]
  public async Task<string> Hello([FromBody] HelloWorldMessage message)
  {
    // Retrieve user information from the request context.
    var user = HttpContext.GetAdspUser();
    // Retrieve configuration from the request context; configuration of this service for current tenant is returned.
    var configuration = await HttpContext.GetConfiguration<HelloWorldConfiguration, HelloWorldConfiguration>();

    string? response = "Hello World!";
    if (!String.IsNullOrEmpty(message?.Message) && configuration?.Responses?.TryGetValue(message.Message, out string? configuredResponse) == true)
    {
      response = configuredResponse;
    }

    // Send a domain event via the event service.
    await _eventService.Send(
      new DomainEvent<HelloWorldEvent>(
        HelloWorldEvent.EventName,
        DateTime.Now,
        new HelloWorldEvent
        {
          FromUser = user!.Name,
          FromUserId = user.Id,
          Message = message?.Message,
          Response = response
        }
      )
    );

    return response;
  }
}
