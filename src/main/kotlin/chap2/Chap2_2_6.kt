package chap2

fun main() {
    println("[2.2.6] 기본타입 > 수 변환")

    println("""
       각 수 타입마다 값을 다른 수 타입으로 변환하는 연산이 정의되어 있다.
       toByte()
       toShort()
       toInt()
       toLong()
       toFloat()
       toDouble()
       toChar() 
       등이 있다.
    """)

    println("""
        자바와 달리 코틀린에서는 범위가 큰 타입이 사용되어야 하는 문액에 범위가 작은 타입을 쓸 수 없다.
        e.g) Int 값을 Long 변수에 대입할 수 없다.
    """)

    val numberInt = 100
    // val numberLong:Long = numberInt // Compile Error, ype mismatch: inferred type is Int but Long was expected

    println("""
         암시적인 박싱 (Implict boxing, auto boxing)에 의한 모호성을 배제하기 위함
         타입 변화에 따른 다른 값(인스턴스)를 만들어낼 수 있는 가능성이 생기며, 
         이에 동등성 (equality) 요구 조건을 만족시키지 못하게 되면서 모호하고 미묘한 오류가 발생할 수 있음
    """)

    // println(numberInt == numberLong) // 24 라인 코드가 성공하면 false 가 출력됨 (같은 100이지만 객체가 다름, primitive 타입이 없음)

    println("""
       더 큰 범위를 담는 경우 손실 없이 수행
        그렇지 않는 경우 2진수로 표현했을때 상위 비트 쪽(MSB)를 잘라내고 나머지를 대상 타입 값으로 대입
    """)
    val n = 945
    println(n.toByte()) // -79
    println(n.toShort()) // 945
    println(n.toChar()) // alpha
    println(n.toLong()) // 945
    println(2.5.toInt()) // 2
    println((-2.5).toInt()) // -2
    println(1_000_000_000_000.toFloat().toLong()) // 999999995904
}