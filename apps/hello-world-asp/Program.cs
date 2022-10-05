using Adsp.Sdk;
using AdspSdkSamples.HelloWorldAsp;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.

builder.Services.AddLogging();
builder.Services.AddControllers();
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

// Bind configuration section.
var adspConfiguration = builder.Configuration.GetSection("Adsp");
builder.Services.Configure<AdspOptions>(adspConfiguration);

// Add ADSP components.
builder.Services.AddAdspForService(options =>
{
  var serviceId = AdspId.Parse(adspConfiguration.GetValue<string>("ClientId"));
  options.ServiceId = serviceId;
  
  options.DisplayName = "Hello world service";
  options.Description = "Hello world sample for ASP.NET Core with ADSP SDK.";
  
  // Register configuration definition
  options.Configuration = new ConfigurationDefinition<HelloWorldConfiguration>(
    "Configuration of the hello world sample service.",
    (tenant, core) => tenant
  );
  
  // Register service roles
  options.Roles = new[] {
    new ServiceRole {
      Role = ServiceRoles.HelloWorlder,
      Description = "Hello worlder role that allows user to post a message to the API."
    }
  };
  
  // Register domain events
  options.Events = new[] {
    new DomainEventDefinition<HelloWorldEvent>(
      HelloWorldEvent.EventName,
      "Signalled when a hello world message is posted to the API."
    )
  };
});


var app = builder.Build();

app.UseSwagger();
app.UseHttpsRedirection();

app.UseAdsp();
app.UseAuthorization();

app.MapControllers();

app.Run();
