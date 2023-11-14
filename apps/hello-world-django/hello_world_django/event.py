from datetime import datetime
from adsp_service_flask_sdk import DomainEvent, User


HELLO_WORLD_EVENT = "hello-world-event"


def create_hello_event(user: User, message: str, response: str):
    return DomainEvent(
        HELLO_WORLD_EVENT,
        datetime.utcnow(),
        {
            "fromUserId": user.id,
            "fromUser": user.name,
            "message": message,
            "response": response,
        },
    )
