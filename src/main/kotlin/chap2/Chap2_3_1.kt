package chap2

import java.util.*

fun main() {
    println("[2.3] 문자열")
    println("""
       자바와 동일하게 코틀린 문자열도 불면(immutable) 하다.
    """)

    println("[2.3.1] 문자열 > 문자열 템플릿")
    println("""
       문자열 리터럴(literal)을 정의하는 일반적인 방법 (")큰따옴표로 문자열을 감싸는 것이다.
    """)

    val hello = "hello, world!\nThis is \"multiline\" string"
    println(hello)
    println("\u03C0 \u2248 3.14") // π ≈ 3.14


    println("""
       기본적으로 $\{\}의 중괄호 사이에 넣기만 하면, 어떤 올바른 코틀린 식이든 문자열에 넣을 수 있다.
       이는 모든 코틀린 타입(Any)이 제공하는 toString() 메서드를 통해 문자열로 변환한다.
    """)

    print("input name : ")
    val name = readLine()
    println("Hello, $name!\n Today is ${Date()}")

    println("""
       로우 문자열(raw string)이 있다.
       로우 문자열을 사용하면 이스케이프 시퀀스를 사용하지 않고도 문자열을 작성할 수 있다.
    """)

    val message = """
        Hello, $name!
        Today is ${Date()}
    """

    println(message)
    println("""
       .trimIndent() 는 여러 줄에 공통된 최소 들여쓰기를 제거해주는 표준 코틀린 함수이다. 
    """)

    println("""
       로우 문자열에 특수 문자를 추가하고 싶다면 $\{\} 안에 특수 문자를 넣으면 된다. 
       아래 예시는 큰따옴표 3개 연속 작성하는 방법
    """)
    val messageTripleQuote = """
        This is triple quote : '${"\"\"\""}'
    """
    println(messageTripleQuote)
}