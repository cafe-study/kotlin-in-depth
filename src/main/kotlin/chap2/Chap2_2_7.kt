package chap2

fun main() {
    println("[2.2.7] 기본타입 > 불 타입과 논리 연산")

    println("""
       참(true), 거짓(false) 중 하나로 판명되는 불(boolean) 타입과 논리 연산을 제공
       자바와 동일하게 수 타입과는 다른 타입, toInt() 등의 명시적인 내장 연산을 사용하여 수로 변환 할 수 없다. (반대도 마찬가지)
       
       ! : 논리 부정
       or, and, xor : 즉시(eager) 계산 방식의 논리합, 논리곱, 논리 배타합
       ||, && : 지연(lazy) 계산 방식의 논리합, 논리곱
         - || 왼쪽 피연산자가 true 이면 오른쪽 피연산자를 계산하지 않는다.
         - && 왼쪽 피연산자가 false 이면 오른쪽 피연산자를 계산하지 않는다.
    """)

    val hasErrors = false
    val testPassed = true
}