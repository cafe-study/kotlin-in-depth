package chap2

import java.util.*

fun main() {
    println("[2.4] 배열")
    println("""
       개념적으로 자바 배열과 비슷하며, 실제로 코틀린/jvm app에서는 자바 배열로 코틀린 배열을 표현한다.
    """)

    println("[2.4.1] 배열 > 배열 정의하기")

    println("""
       배열 구조를 구현하는 가장 일반적인 타입은 Array<T>
       T 는 원소의 타입이다.
    """)

    val a = emptyArray<String>() // Array<String> (0개), 원소가 없어 타입 추론할 수 없기에 타입을 명시함
    val b = arrayOf("hello", "world") // Array<String> (2개)
    val c = arrayOf(1, 4, 9) // Array<Int> (3개)

    println("""
       인덱스로부터 원소를 만들어 배열을 생성하는 방법 > 중괄호({}) 내 lambda 를 사용 
    """)
    println("input array size : ")
    val size = readLine()!!.toInt()
    val squares = Array(size) { (it + 1)*(it + 1) } // it > index 로 자동선언 변수
    println(squares.toList())

    println("""
       Array<Int> 를 사용하는 배열은 제대로 작동하지만, 모든 타입을 박싱하기에 실용적이지 않음
       더 효과적인 방법은 ByteArray, ShortArray, IntArray, LongArray, FloatArray, DoubleArray, CharArray, BooleanArray 라는 특화된 배열 타입을 사용
       이런 타입은 jvm 에서 int[], boolean[] 등의 원시 타입 배열로 표현된다
    """)

    val operations = charArrayOf('+', '-', '*', '/', '%')
    val squares2 = IntArray(10) { (it + 1)*(it + 1)}
    println(operations.toList())
    println(squares2.toList())
}