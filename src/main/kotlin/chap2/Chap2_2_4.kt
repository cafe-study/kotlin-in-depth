package chap2

fun main() {
    println("[2.2.4] 기본타입 > 비트 연산")

    println("Int 와 Long 은 비트 수준의 연산을 지원")

    println("shl : shift left 왼쪽 시프트")
    println(13 shl 2) // 52
    println(-13 shl 2) // -52

    println("shr : shift right 오른쪽 시프트")
    println(13 shr 2) // 3
    println(-13 shr 2) // -4

    println("ushr : unsigned shift right 부호없는 오른쪽 시프트")
    println(13 ushr 2) // 3
    println(-13 ushr 2) // 1073741820

    println("and : 비트곱 (AND)")
    println(13 and 19) // 1
    println(-13 and 19) // 19

    println("or : 비트합 (OR)") // 코틀린 1.1 byte, short 지원
    println(13 or 19) // 31
    println(-13 or 19) // -13

    println("xor : 비트 배타합 (XOR)") // 코틀린 1.1 byte, short 지원
    println(13 xor 19) // 30
    println(-13 xor 19) // -32

    println("inv : 비트 반전(inversion, NOT)") // 코틀린 1.1 byte, short 지원
    println(13.inv()) // -14
    println((-13).inv()) // 12
}