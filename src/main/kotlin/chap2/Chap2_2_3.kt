package chap2

fun main() {
    println("[2.2.3] 기본타입 > 산술 연산")

    println("""
       산술 연산의 동작은 자바와 같다
       산술 연산자 (+덧셈, -뺄샘, *곱셈, /나눗셈, % 나머지)
       https://kotlinlang.org/docs/keyword-reference.html#operators-and-special-symbols
       
       코틀린 1.5 표준라이브러에 정수 floorDiv()-몫과 mod()-나머지 메서드가 추가됨
    """)

    println(7.floorDiv(4)) // 1
    println((-7).floorDiv(4)) // -2 (참고, -7은 리터럴문자가 아님 - 연산자 7 의 결함
    println(7.mod(4))  // 3
    println((-7).mod(4)) // 1

    println("""
       Double > Float > Long > Int > Short > Byte
        
       byte + byte // 2: byte
       int + byte // 2: Int
       int + int // 2 : Int
       int + long // 2: Long
       long + doble // 2.5 : double
    """)

    val byte: Byte = 1
    val int = 1
    val long = 1L
    val float = 1.5f
    val double = 1.5

    val no1 = byte + byte // 2 byte (책에는 byte 로 표현)
    val no2 = int + byte // 2 Int
    val no3 = int + int // 2 Int
    val no4 = int + long // 2 long
    val no5 = long + double // 2.5 double
    val no6 = float + double // 3.0 double
    val no7 = float + int // 2.5 float
    val no8 = long + double // 2.5 double

    println("byte + byte : %s, %d".format(no1.javaClass.simpleName, no1))
    println("int + byte : %s, %d".format(no2.javaClass.simpleName, no2))
    println("int + int : %s, %d".format(no3.javaClass.simpleName, no3))
    println("int + long : %s, %d".format(no4.javaClass.simpleName, no4))
    println("long + double : %s, %f".format(no5.javaClass.simpleName, no5))
    println("float + double : %s, %f".format(no6.javaClass.simpleName, no6))
    println("float + int : %s, %f".format(no7.javaClass.simpleName, no7))
    println("long + double : %s, %f".format(no8.javaClass.simpleName, no8))

}