from typing import Union


class Node:
    def __init__(self, idx: int, key: Union[str, None], data=None):
        self.id = idx
        self.key = key
        self.data = data
        self.children = dict()
