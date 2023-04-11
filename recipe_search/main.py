from jamo import h2j, j2hcj
import pandas as pd
from trie import Trie


def make_trie():
    names = pd.read_csv("data/recipe.csv")["recipe_name"]
    trie = Trie()
    for name in names:
        name_split = j2hcj(h2j(name))
        trie.insert(name_split)
    return trie


def dfs(parent_node, depth):
    children = parent_node.children
    for key in children:
        child_node = children[key]
        node_table.append(
            dict(zip(
                node_column,
                [child_node.id, child_node.key, parent_node.id, child_node.data])
            )
        )
        closure_table.append(
            dict(zip(
                closure_column,
                [parent_node.id, child_node.id, depth + 1]
            ))
        )
        dfs(child_node, depth + 1)


if __name__ == '__main__':
    data = make_trie()
    node_table = []
    node_column = ["id", "node_key", "parent_Id", "data"]
    closure_table = []
    closure_column = ["parent", "child", "depth"]

    start_node = data.head
    node_table.append(
        dict(zip(
            node_column,
            [start_node.id, start_node.key, start_node.id, start_node.data]))
    )
    dfs(start_node, 0)

    node_df = pd.DataFrame(node_table)
    closure_df = pd.DataFrame(closure_table)

    node_df.to_csv("data/recipe_word_completion_node.csv", index=False)
    closure_df.to_csv("data/recipe_word_completion_closure.csv", index=False)

