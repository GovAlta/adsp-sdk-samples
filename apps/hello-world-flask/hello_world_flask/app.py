from adsp_service_flask_sdk import (
    AdspExtension,
    AdspRegistration,
    ConfigurationDefinition,
    DomainEventDefinition,
    ServiceRole,
    request_user,
    require_user,
)
from flask import Flask, request

from hello_world_flask import service_roles
from hello_world_flask.event import HELLO_WORLD_EVENT, create_hello_event


adsp_extension = AdspExtension()

app = Flask(__name__)
adsp = adsp_extension.init_app(
    app,
    AdspRegistration(
        "Hello world service",
        "Hello world example for Flask",
        configuration=ConfigurationDefinition(
            "Configuration of the hello world sample service.",
            {
                "type": "object",
                "properties": {
                    "responses": {
                        "type": "object",
                        "additionalProperties": {"type": "string"},
                    },
                },
            },
        ),
        roles=[
            ServiceRole(
                service_roles.HELLO_WORLDER,
                "Hello worlder role that allows user to post a message to the API.",
            )
        ],
        events=[
            DomainEventDefinition(
                HELLO_WORLD_EVENT,
                "Signalled when a hello world message is posted to the API.",
                payload_schema={
                    "type": "object",
                    "properties": {
                        "fromUserId": {"type": "string"},
                        "fromUser": {"type": "string"},
                        "message": {"type": "string"},
                        "response": {"type": "string"},
                    },
                },
            )
        ],
    ),
)

app.register_blueprint(adsp.metadata_blueprint)


@app.route("/hello-world/v1/hello", methods=["POST"])
@require_user(service_roles.HELLO_WORLDER)
def hello():
    body = request.json()
    message = body.get("message", None)

    configuration = adsp.get_configuration() or {}
    response = configuration.get("responses", {}).get(message, "Hello World!")

    adsp.event_service.send(create_hello_event(request_user, message, response))
    return response
