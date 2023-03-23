from hello_world_flask import index


def test_index():
    assert index.hello() == "Hello hello-world-flask"
