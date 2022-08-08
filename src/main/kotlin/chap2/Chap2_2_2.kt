package chap2

fun main() {
    println("[2.2.2] 기본타입 > 부동 소수점")

    println("""
       부동소수점 수(floating point number)를 따르는 Float과 Double을 제공한다.
        
        일반적인 형태 10진수 소수 형태(3.14)
        정수부 생략가능 : 정수 부분이 비어있는 경우 정수를 0으로 간주 (.25 > 0.25)
        소수부 생략불가 : 소수 부분은 생략할 수 없다
        
        자바와 달리 Double 이나 Float 의 16진 리터럴을 지원하지 않음
        부동소수점 수 기본 타입은 Double 이다.
        
        자바에서는 리터럴을 뒤에 D나 d를 추가하여 double 타입 리터럴을 만들 수 있었다
        그러나, 코틀린에서는 이런 접미사를 허용하지 않는다.
    """)

    val pi = 3.14
    val one = 1.0
    val quarter = .25 // 0.25
    // val one = 1. // Error 소수부 생략 오류
    val two = 2 // 오류는 아니지만 정수 리터럴


    val piDouble = 3.14
    val piFloat = 3.14f // f를 추가함으로써 float 타입 선언
    // val piDouble2 = 3.14d // Error, Unresolved reference: d (D, d 접미사를 허용하지 않음)
    println("Double : %s, Float : %s".format(piDouble.javaClass.simpleName, piFloat.javaClass.simpleName))


    println("""
       과학적 표기법(scientific notation) 리터럴을 허용한다.
        e나 E 뒤에 10을 몇번 거듭제곱하는지 알려주는 숫자
    """)

    val sn_pi = 0.314E1 // 3.14 = 0.314 * 10
    val sn_pi100 = 0.314E3 // 3.14 = 0.314 * 1000
    val sn_piOver100 = 3.14E-2 // 3.14 = 3.14/100
    val thousand = 1E3 // 1000.0 = 1*1000

    println(sn_pi)
    println(sn_pi100)
    println(sn_piOver100)
    println(thousand)
}