package chap9_1_2

import chap9.TreeNode
import chap9_1_1_2.addChildren
import chap9_1_1_2.walkDepthFirst

fun <T : Number> TreeNode<T>.average(): Double {
    var count = 0
    var sum = 0.0

    walkDepthFirst {
        count++
        sum += it.toDouble()
    }

    return sum / count
}

fun main() {
    val intTree = TreeNode(1).apply {
        addChild(2).addChild(3)
        addChild(4).addChild(5)
    }
    println(intTree.average())

    val doubleTree = TreeNode(1.0).apply {
        addChild(2.0)
        addChild(3.0)
    }
    println(doubleTree.average())

    val stringTree = TreeNode("Hello").apply {
        addChildren("World", "!!!")
    }
    // [UNRESOLVED_REFERENCE_WRONG_RECEIVER] Unresolved reference. None of the following candidates is applicable because of receiver type mismatch:
    //println(stringTree.average())
}