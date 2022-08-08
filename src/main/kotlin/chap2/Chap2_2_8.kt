package chap2

fun main() {
    println("[2.2.8] 기본타입 > 비교와 동등성")

    println("""
       == 같다
       != 같지 않다
       < ~보다 작다
       <= ~보다 작거나 같다
       > ~보다 크다
       >= ~보다 크거나 같다
    """)

    val a = 1
    val b = 2
    println(a == 1 || b != 1) // true
    println(a > 1 && b < 3) // true
    println(a < 1 || b < 1) // false
    println(a > b) // false

    println("""
        동등성 연산자인 == 와 != 는 모든 타입의 값에 적용할 수 있다.
        하지만 몇몇 예외가 있다.
        
        기본적으로 두 인자가 같은 타입일 때만 == 와 != 를 허용한다. 
    """)

    val numberInt = 1
    val numberLong = 1L

    // println(numberInt == numberLong) // Complie Error, Oerator '==' cannot be applied to 'Int' and 'Long'
    println(numberInt.toLong() == numberLong) // true

    println("""
        비교 연산자 <, <=, >, >= 는 모든 수 타입에서 서로 다른 타입간에 비교할 수 있다.
        이는 산술 연산(+-*/%)이 가능한 모든 경우를 다룰 수 있도록 오버로딩(overloading)된 것과 같은 개념이다.
    """)

    println(1 <= 2L || 3 > 4.5) // true

    println("""
       Char과 Boolean 값도 비교 연산을 지원하지만, 같은 타입값과 비교할 수 있다
    """)

    println(false == true) // false
    println(false < true) // true
    // println(false > 1) // Compile Error, The integer literal does not conform to the expected type Boolean
    println('a' < 'b') // true
    // println('a' > 0) //  Compile Error, The integer literal does not conform to the expected type Char

    println("""
       부동 소수점 타입 비교 연산 IEEE 754 표준을 따른다.
       NaN(Not a Number)값을 특별히 취급한다.
       NaN 은 어떠한 값과도 같지 않다. 또한 다른 NaN 과도 같지 않고, 무한대를 포함한다른 어떤 값보다 작지도 않고 크지도 않다.
    """)

    println(Double.NaN == Double.NaN) // false
    println(Double.NaN != Double.NaN) // true
    println(Double.NaN <= Double.NaN) // false
    println(Double.NaN < Double.POSITIVE_INFINITY) // false
    println(Double.NaN > Double.NEGATIVE_INFINITY) // false

    println("""
       컴파일 타임이 아닌 런타임 시점에서는 이 규칙 (NaN 의 동등비교)이 적용하지 않는다.,
       런타임에서는 jvm 이 원시 타입(primitive type)을 값을 감싼 Double, Float로 비교한다. 
       
       따라서, 이때는
         NaN 은 자기 자신과 같다
         NaN 은 Double 에서 가장 큰 수 이다.
    """)

    val set = sortedSetOf(Double.NaN, Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, 0.0)
    println(set) // [-Infinity, 0.0, Infinity, NaN]
}