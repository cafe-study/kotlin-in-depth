package chap9_1_2_3

import chap9.TreeNode
import chap9_1_1_2.walkDepthFirst

fun <T, U : T> TreeNode<U>.toList(list: MutableList<T>) {
    walkDepthFirst { list += it }
}

fun main() {
    val list = ArrayList<Number>()

    TreeNode(1).apply {
        addChild(2)
        addChild(3)
    }.toList(list)

    TreeNode(1.0).apply {
        addChild(2.0)
        addChild(3.0)
    }.toList(list)
}