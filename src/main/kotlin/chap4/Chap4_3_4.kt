package chap4

import java.io.File
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

fun main() {
    println("[Chapter 4.3.4] 지연 계산 프로퍼티와 위임 ")

    println("""
        lazy - 프로퍼티를 처음 읽을 때까지 값에 대한 계산을 미룸 
        lazy 다음에 오는 블록 안에는 프로퍼티를 초기화하는 코드를 지정한다.
        
        https://holika.tistory.com/entry/%EB%82%B4-%EB%A7%98%EB%8C%80%EB%A1%9C-%EC%A0%95%EB%A6%AC%ED%95%9C-Kotlin-lateinit%EA%B3%BC-by-lazy%EC%9D%98-%EC%B0%A8%EC%9D%B4%EC%A0%90
    """)

    lateinit var text: String
    val textLength: Int by lazy {
        text.length
    }

    // 초기화 전이라 Exception in thread "main" kotlin.UninitializedPropertyAccessException: lateinit property text has not been initialized
    println(textLength)

    text = "Hello!"
    println(textLength) // 6

    text = "Hello! Hello!"
    println(textLength) // 6 (val => 한번 저장 후 바뀌지 않음)


    println("""
        Late initialization : 필요할 때 초기화하고 사용할 수 있음. 초기화하지 않고 쓰면 Exception 발생
        Lazy initialization : 변수를 선언할 때 초기화 코드도 함께 정의. 변수가 사용될 때 초기화 코드 동작하여 변수가 초기화됨
    """)
}