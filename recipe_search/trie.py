from node import Node
from unicode import join_jamos
from collections import deque


class Trie:
    def __init__(self):
        self.idx = 1
        self.head = Node(1, None)

    def add_idx(self):
        self.idx += 1

    def insert(self, string: str):
        cur_node = self.head
        text = ""
        for value in string:
            if value not in cur_node.children:
                self.add_idx()
                cur_node.children[value] = Node(self.idx, value)
            cur_node = cur_node.children[value]
            text += value
            cur_node.data = join_jamos(text)
        # cur_node.data = join_jamos(string)

    def search(self, string: str) -> list:
        cur_node = self.head
        answer = []

        for char in string:
            if char in cur_node.children:
                cur_node = cur_node.children[char]

        if not cur_node.children:
            return [cur_node.data]

        queue = deque([])
        queue.append(cur_node)
        while queue:
            node = queue.popleft()
            if not node.children:
                answer.append(node.data)
            for key in node.children:
                queue.append(node.children[key])
        return answer
