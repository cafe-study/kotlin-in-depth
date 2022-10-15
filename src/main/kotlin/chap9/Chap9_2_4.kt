package chap9_2_4

import chap9.TreeNode

fun main() {
    val anyList: List<*> = listOf(1, 2, 3)

    val anyComparable: Comparable<*> = "abcde"

    val any: Any = ""
    any is TreeNode<*>  // *는 모든 노드가 어떤 공통 타입 T에 속하나 T가 어떤 타입인지 알려져 있지 않음
    any is TreeNode<out Any?> // Any?는 아무 타입이나 들어갈 수 있다는 뜻
    // [CANNOT_CHECK_FOR_ERASED] Cannot check for instance of erased type: TreeNode<out Number>
    // any is TreeNode<out Number>
}