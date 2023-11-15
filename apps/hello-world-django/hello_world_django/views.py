import json
from adsp_service_django_sdk import adsp, get_user
from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt


from .event import create_hello_event


@csrf_exempt
def hello(request):
    if request.method != 'POST':
        return HttpResponse("Method Not Allowed", status=405)

    if request.headers["content-type"] != "application/json":
        return HttpResponse("Bad Request", status=400)

    body = json.loads(request.body)
    message = body.get("message", None)

    configuration = adsp.get_configuration(request)
    response = configuration.get("responses", {}).get(message, "Hello World!")

    adsp.event_service.send(create_hello_event(get_user(request), message, response))
    return HttpResponse(response)
