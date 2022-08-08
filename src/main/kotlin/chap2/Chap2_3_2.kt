package chap2

import java.util.*

fun main() {
    println("[2.3.2] 문자열 > 기본 문자열 연산")
    println("""
       String 타입의 property
       length : 문자 수 반환
       lastIndex : 마지막 문자 인덱스
    """)

    val hello = "Hello!"
    println(hello.length)    // 6
    println(hello.lastIndex) // 5 (index 시작은 0)

    println("""
       인덱스를 각괄호([]) 안에 넣는 연산자를 사용해 개별 문자열 접근 가능
    """)

    println(hello[0]) // H
    println(hello[1]) // e
    println(hello[2]) // l
    println(hello[3]) // l
    println(hello[4]) // o
    println(hello[5]) // !
    // println(hello[6]) // Runtime Error, java.lang.StringIndexOutOfBoundsException: String index out of range: 6

    println("""
       + 연산자를 사용해 두 문자열을 연결(concatenate) 할 수 있다.
        그러나 문자열 템플릿 사용이 더 간결하므로 템플릿을 사용하자
    """)

    val sum = 1_000
    println("The sum is: " + sum)
    println("The sum is: $sum")


    println("""
       문자열은 ==와 != 를 사용해 동등성을 비교할 수 있다. 
       "문자열의 내용을 비교" 하므로 서로 다른 두 객체 인스턴스를 비교해도 문자들의 순서와 갈이가 같으면 같은 문자열로 간주
       
       자바에서는 == 와 !== 연산자는 참조 동등성 (referential equality) 을 비교하기에 실제 문자열 비교는 equals() 메서드를 사용해야 한다.
       자바 문법 : s1.equals(s2)
       코틀린 문법 : s1 == s2 >> 편의 문법(syntatic sugar)
       
       참고, 코틀린에서 참조 동등성 비교는 ===와 !=== 연산자를 사용하면 된다
    """)

    val s1 = "Hello!"
    val s2 = "Hel" + "lo!"
    println(s1 == s2) // true
    println(s1 === s2) // false or ture (내부 정책)

    println("""
       문자열은 사전식 순서(lexicographically) 로 정렬되어 <, > , <=, >= 연산자 사용해 문자열 비교 가능)
    """)

    println("abc" < "cba") // true
    println("123" > "34") // false

    println("""
       문자열 > 수 타입 변환 메서드
        toByte()
        toShort()
        toInt()
        toLong()
        toFloat()
        toDouble()
        toBoolean()
        변환 함수를 제공한다.
    """)

    println("1".toByte())       // 1
    println("1".toShort())      // 1
    println("1".toInt())        // 1
    println("1".toLong())       // 1
    println("1".toFloat())      // 1.0
    println("1".toDouble())     // 1.0
    println("true".toBoolean()) // true

    // println("a".toInt()) // Runtime Error, java.lang.NumberFormatException: For input string: "a"

    println("""
       String 타입 제공 유용한 함수        
    """)

    val nullValue = null
    println(hello.isEmpty()) // false
    println("".isEmpty()) // true
    println(hello.isNotEmpty()) // true
    println(hello.isNotBlank()) // true
    println(nullValue.isNullOrBlank()) // true

    println(hello.substring(2)) // llo!
    println(hello.substring(1,3)) // el
    println(hello.startsWith("Hel")) // true
    println(hello.endsWith("lo!")) // true
}