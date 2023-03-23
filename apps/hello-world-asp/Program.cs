using Adsp.Sdk;
using AdspSdkSamples.HelloWorldAsp;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.

builder.Services.AddLogging();
builder.Services.AddControllers();
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

// Initialization of ADSP SDK components...
// 1. Bind configuration section or otherwise configure the options.
var adspConfiguration = builder.Configuration.GetSection("Adsp");
builder.Services.Configure<AdspOptions>(adspConfiguration);

// 2. Add ADSP components to the service collection.
builder.Services.AddAdspForService(options =>
{
  // 3. Provide configuration of the service.
  // 3a. Configure basic service information.
  var serviceId = AdspId.Parse(adspConfiguration.GetValue<string>("ClientId"));
  options.ServiceId = serviceId;
  options.DisplayName = "Hello world service";
  options.Description = "Hello world sample for ASP.NET Core with ADSP SDK.";

  // 3b. Register configuration definition.
  options.Configuration = new ConfigurationDefinition<HelloWorldConfiguration>(
    "Configuration of the hello world sample service.",
    (tenant, core) => tenant
  );
  options.EnableConfigurationInvalidation = true;

  // 3c. Register service roles.
  options.Roles = new[] {
    new ServiceRole {
      Role = ServiceRoles.HelloWorlder,
      Description = "Hello worlder role that allows user to post a message to the API."
    }
  };

  // 3d. Register domain events.
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

// 4. Add ADSP middleware.
app.UseAdsp();
app.UseAuthorization();
app.UseAdspMetadata(new AdspMetadataOptions
{
  ApiPath = "hello-world/v1"
});

app.MapControllers();

app.Run();
