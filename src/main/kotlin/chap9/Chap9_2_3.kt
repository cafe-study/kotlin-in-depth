package chap9_2_3

import chap9.TreeNode

fun <T> TreeNode<T>.addSubtree(node: TreeNode<out T>): TreeNode<T> {
    val newNode = addChild(node.data)
    node.children.forEach { newNode.addSubtree(it) }
    return newNode
}

//fun <T, U : T> TreeNode<T>.addSubtree(node: TreeNode<U>): TreeNode<T> {
//    val newNode = addChild(node.data)
//    node.children.forEach { newNode.addSubtree(it) }
//    return newNode
//}

// 지정한 변성에서 사용할 수 없는 연산을 사용하려 하면 컴파일 에러가 발생
//fun processOut(node: TreeNode<out Any>) {
//    node.addChild("xyz") // error: type missmatch
//}

// 소비자로 사용할 수도 있음
fun <T> TreeNode<T>.addTo(parent: TreeNode<in T>) {
    val newNode = parent.addChild(data)

    children.forEach { it.addTo(newNode) }
}

// 다음과 같이 Number에 해당되면 모두 처리할 수 있게 하고 싶다면 addSubtree 파라미터의 제네릭 타입에 out을 붙임
// 또는 추가되는 트리의 원소를 표현하기 위해 첫 번째 타임에 의해 바운드되는 두 번째 타입 파라미터를 도입
fun main() {
    val root = TreeNode<Number>(123)
    val subRoot = TreeNode(456.7)

    root.addSubtree(subRoot)
    println(root) // abc {def {}}
}