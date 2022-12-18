package chap9_1_2_2

import chap9.TreeNode
import chap9_1_1_2.addChildren

fun <T : Comparable<T>> TreeNode<T>.maxNode(): TreeNode<T> {
    val maxChild = children.maxByOrNull { it.data } ?: return this

    return if (data >= maxChild.data) this else maxChild
}

fun main() {
    val doubleTree = TreeNode(1.0).apply {
        addChild(2.0)
        addChild(3.0)
    }
    println(doubleTree.maxNode().data)

    val stringTree = TreeNode("abc").apply {
        addChildren("xyz", "def")
    }
    println(stringTree.maxNode().data)
}