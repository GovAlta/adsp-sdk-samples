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
    _eventService = eventService;
  }

  [HttpPost]
  [Route("hello")]
  [Authorize(Roles = ServiceRoles.HelloWorlder)]
  public async Task<string> Hello([FromBody] HelloWorldMessage message)
  {
    var user = HttpContext.GetAdspUser();
    var configuration = await HttpContext.GetConfiguration<HelloWorldConfiguration, HelloWorldConfiguration>();

    string? response = "Hello World!";
    if (!String.IsNullOrEmpty(message?.Message) && configuration?.Responses?.TryGetValue(message.Message, out string? configuredResponse) == true)
    {
      response = configuredResponse;
    }
    
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
