from typing import Any, Dict
from adsp_service_django_sdk import (
    AdspRegistration,
    ConfigurationDefinition,
    DomainEventDefinition,
    ServiceRole,
)

from hello_world_django import service_roles
from .event import HELLO_WORLD_EVENT


def convert_config(tenant_config, _) -> Dict[str, Any]:
    return tenant_config


registration = AdspRegistration(
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
        convert_config,
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
)
