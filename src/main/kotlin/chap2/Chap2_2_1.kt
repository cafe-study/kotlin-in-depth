package chap2

import kotlin.math.absoluteValue
import kotlin.math.ulp

fun main() {
    println("[2.2] 기본타입")

    println("""
       자바와 달리 모든 코틀린 타입은 근본적으로 클래스 정의를 기반으로 만들어진다.
       따라서, Int와 같이 원시 타입(primitive type)과 비슷한 타입들도 메서드와 프로퍼티를 제공한다.
       자바에는 원시 타입을 감싸는 박싱 타입(boxing type)이 있지만, 코틀린은 필요할 때 암시적(implict)으로 박싱을 수행한다.
    """)

    val number: Double = -3.14 // double type 이며, method 와 property 를 제공한다
    println(number.toInt()) // method
    println(number.absoluteValue) // property

    println("""
       타입은 하위 타입(subtype) 개념으로 계층화 할 수 있다.
       A타입이 B타입의 하위 타입이라는 말은 근본적으로 B타입 값이 쓰일 수 있는 모든 문맥에 A타입 값을 넣어도 문제가 없다.
       모든 코틀린 타입은 Any 라는 내장 타입의 직간접적인 하위 타입이다.
    """)

    val n: Any = 1
    println(n)

    println("[2.2.1] 기본타입 > 정수 타입")

    println("""
       Byte 1byte -127..127 Byte
       Short 2byte -32768..32767 Short
       Int 4byte -2^31..2^31-1
       Long 8Byte -2^63..2^63-1
       
       정수 타입은 기본 10진수
       숫자 리터럴에 "_" 를 통해 표현가능 val number = "12_345_678"
    """)
    println("[Byte]  min : %25d, max : %25d".format(Byte.MIN_VALUE, Byte.MAX_VALUE))
    println("[Short] min : %25d, max : %25d".format(Short.MIN_VALUE, Short.MAX_VALUE))
    println("[Int]   min : %25d, max : %25d".format(Int.MIN_VALUE, Int.MAX_VALUE))
    println("[Long]  min : %25d, max : %25d".format(Long.MIN_VALUE, Long.MAX_VALUE))

    println("[UnderScore] value : %25d".format(123_456_789))

    val hundredInt = 100 // Int 로 추론됨
    val hundredLong = 100L // Long 으로 추론됨
    println("hundredInt type is : [%s]".format(hundredInt.javaClass.simpleName))
    println("hundredLong type is : [%s]".format(hundredLong.javaClass.simpleName))
    println("[UnderScore] value : %25d".format(123_456_789))

    println("""
       2진수 (0b)
       16진수 (0x) 
       를 사용하여 리터럴 작성 가능
       그러나, 코틀린에서는 8진수는 지원하지 않음
    """)

    val bin_number = 0b10101 // 21
    val hex_number = 0xF9    // 249

    println("binary : [%d], hex : [%d]".format(bin_number, hex_number))
}