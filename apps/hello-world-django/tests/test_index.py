from hello_world_django import index


def test_index():
    assert index.hello() == "Hello hello-world-django"
