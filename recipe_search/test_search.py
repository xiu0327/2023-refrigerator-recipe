import unittest
from jamo import h2j, j2hcj
from unicode import join_jamos
import pandas as pd
from trie import Trie


class MyTestCase(unittest.TestCase):
    def test_jamo_split(self):
        sample_text = "가나"
        result = j2hcj(h2j(sample_text))
        self.assertEqual(result, "ㄱㅏㄴㅏ")

    def test_something2(self):
        result = join_jamos("ㅅㅞㄹ")
        self.assertEqual(result, "쉘")

    def test_trie_insert(self):
        names = pd.read_csv("data/recipe.csv")["recipe_name"]
        trie = Trie()
        for name in names:
            name_split = j2hcj(h2j(name))
            trie.insert(name_split)
        string = j2hcj(h2j("볶음"))
        print(trie.search(string))

