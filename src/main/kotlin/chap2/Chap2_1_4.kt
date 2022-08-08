package chap2

fun main() {
    println("[Chapter 2.1.4] 가변 변수")

    println("""
       val(value)는 불변(immutable) 변수 키워드
         - java final 변수와 유사
         - 초기화하면 다른 값 대입을 할 수 없음
         - 코드에 대한 추론이 쉬워지기에 불변 변수 사용 권장
       
       var(variable)는 가변(mutable) 변수 키워드
         - 초기화 후 값 변경이 가능
         - 다른 타입의 값을 넣을 수 없음 (kotlin 은 strongly typed 언어)
    """)

    var sum = 1
    sum = sum + 2
    sum = sum + 3
    println(sum)

    println("""
       복합 대입 연산이라는 대입(=)과 +,-,*,/,% 등의 이항 연산을 조합한 연산(+=, -=, *=, /=, %=)도 제공한다. 
    """)

    var result = 3
    result *= 10 // result = result * 10
    result += 6 // result = result + 6
    println(result)

    println("""
        증감(++), 감소(--) 
    """)

    var num = 1
    println(num++) // num = 2, 1이 출력
    println(++num) // num = 3, 3이 출력
    println(--num) // num = 2, 2가 출력
    println(num--) // num = 1, 2가 출력
}