package patmat

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import patmat.Huffman._

@RunWith(classOf[JUnitRunner])
class HuffmanSuite extends FunSuite {
  trait TestTrees {
    val t1 = Fork(Leaf('a', 2), Leaf('b', 3), List('a', 'b'), 5)
    val t2 = Fork(Fork(Leaf('a', 2), Leaf('b', 3), List('a', 'b'), 5), Leaf('d', 4), List('a', 'b', 'd'), 9)

    val CD = Fork(
      Leaf('C', 1),
      Leaf('D', 1),
      "CD".toList,
      2
    )
    val BCD = Fork(
      Leaf('B', 3),
      CD,
      "BCD".toList,
      5
    )
    val EF = Fork(
      Leaf('E', 1),
      Leaf('F', 1),
      "EF".toList,
      2
    )
    val GH = Fork(
      Leaf('G', 1),
      Leaf('H', 1),
      "GH".toList,
      2
    )
    val EFGH = Fork(
      EF,
      GH,
      "EFGH".toList,
      4
    )
    val BCDEFGH = Fork(
      BCD,
      EFGH,
      "BCDEFGH".toList,
      9
    )
    val ABCDEFGH = Fork(
      Leaf('A', 8),
      BCDEFGH,
      "ABCDEFGH".toList,
      17
    )
    val treeExample = ABCDEFGH
  }

  test("weight of a larger tree") {
    new TestTrees {
      assert(weight(t1) === 5)
    }
  }

  test("chars of a larger tree") {
    new TestTrees {
      assert(chars(t2) === List('a', 'b', 'd'))
    }
  }

  test("string2chars(\"hello, world\")") {
    assert(string2Chars("hello, world") === List('h', 'e', 'l', 'l', 'o', ',', ' ', 'w', 'o', 'r', 'l', 'd'))
  }

  test("makeOrderedLeafList for some frequency table") {
    assert(makeOrderedLeafList(List(('t', 2), ('e', 1), ('x', 3))) === List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 3)))
  }

  test("combine of some leaf list") {
    val leaflist = List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 4))
    assert(combine(leaflist) === List(Fork(Leaf('e', 1), Leaf('t', 2), List('e', 't'), 3), Leaf('x', 4)))
  }

  test("encode D with exampleTree") {
    new TestTrees {
      assert(encode(treeExample)("D".toList) === List[Bit](1, 0, 1, 1))
    }
  }

  test("decode 10001010  with exampleTree") {
    new TestTrees {
      assert(decode(treeExample, List[Bit](1, 0, 0, 0, 1, 0, 1, 0)) === "BAC".toList)
    }
  }

  test("quickEncode D with exampleTree") {
    new TestTrees {
      assert(quickEncode(treeExample)("D".toList) === List[Bit](1, 0, 1, 1))
    }
  }

  test("decode and encode a very short text should be identity") {
    new TestTrees {
      assert(decode(t1, encode(t1)("ab".toList)) === "ab".toList)
    }
  }

}