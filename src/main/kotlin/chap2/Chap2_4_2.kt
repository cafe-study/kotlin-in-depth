package chap2

import java.util.*

fun main() {
    println("[2.4] 배열 > 배열 사용하기")

    println("""
       배열은 문자열 타입과 꽤 비슷하다.
       특히 size와 lastIndex 프로퍼티가 있다는 점과 인덱스 연산으로 원소에 접근할 수 있다는 점이 비슷한다.
    """)
    val squares = arrayOf(1, 4, 9, 16)
    println(squares.size) // 4
    println(squares.lastIndex) // 3
    println(squares[3]) // 16
    println(squares[1]) // 4

    println("""
       문자는 리터럴(literal) 이기에 불변(immutable) 하지만
       배열은 mutable 하여, 원소 변경이 가능하다.
    """)

    squares[2] = 100
    squares[3] += 9
    squares[0]--
    println(squares.toList()) // [0, 4, 100, 25]

    println("""
       원본과 별도 배열을 만들고 싶다면 copyOf 함수를 사용한다.
    """)

    val numbers = squares.copyOf()
    numbers[0] = 1000
    println(numbers.toList())                   // [1000, 4, 100, 25]
    println(squares.copyOf(2).toList()) // [0, 4]
    println(squares.copyOf(5).toList()) // [0, 4, 100, 25, null], 책에는 부족한 부분은 0이라고 했는데 null 이 채워짐
    println(squares.copyOf(5)[4])       // 실제 출력도 0 이 아닌 null 이 출력


    println("""
       배열의 원소 타입과 다른 타입은 대입할 수 없다.
       
       참고,
       자바에서는 상위 타입의 배열에 하위 타입을 대입할 수 있었다
       Object[] objects = new String[] {"one", "two", "three" };
       objects[0] = new Object(); // ArrayStoreException 예외가 런타임에 발생
       
       이런 이유로 코틀린에서는 자신과 같은 타입을 제외하고 다른 타입과 하위 타입 관계가 성립하지 않는다고 간주된다
       이는 9장 제네릭스에서 다룰 "강력한 변성(variance)" 개념을 구체적으로 적용한 내용이다
    """)

    var a = arrayOf(1, 4, 9, 16)
    // a = arrayOf("one", "two") // Compile Error, Type mismatch: inferred type is String but Int was expected

    println("""
       배열을 생성하고 나면 그 길이를 바꿀 수 없지만, + 연산이나 새로운 배열을 만들어 추가할 수 있다. 
    """)
    val b = intArrayOf(1, 2, 3) + 4
    val c = intArrayOf(1, 2, 3) + intArrayOf(5, 6)
    println(b.toList()) // [1, 2, 3, 4]
    println(c.toList()) // [1, 2, 3, 5, 6]

    println("""
       문자열과 달리 배열에 대한 == 와 != 연산자는 원소 자체를 비교하지 않고, 참조 비교한다.
       값 비교를 위해서는 eontentEquals() 함수를 사용
    """)

    println(intArrayOf(1, 2, 3) == intArrayOf(1, 2, 3)) // false
    println(intArrayOf(1, 2, 3).contentEquals(intArrayOf(1, 2, 3))) // true
}