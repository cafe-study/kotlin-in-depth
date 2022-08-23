package chap4

fun main() {
    println("[Chapter 4.2] 널 가능성 ")
    println("""
        null - 그 어떤 할당된 객체도 가리키지 않는 참조 
        코틀린은 "널 값이 될 수 있는 타입"과 "널 값이 될 수 없는 타입"을 명확히 구분해준다. 
          ㄴ 이는 NPE를 컴파일 시점으로 옮겨줌!
    """)

    println("[Chapter 4.2.1] 널이 될 수 있는 타입 ")

    println("""
        자바에서 모든 참조타입은 널이 될 수 있는 타입이다. (String str = null;)
        코틀린에서 기본적으로 모든 참조타입은 널이 될 수 없는 타입이다.
          ㄴ String 타입에 null을 대입할 수 없다 
    """)

    println("""
        null을 s 파라미터에 넘기면 컴파일 오류 발생함 
    """)

    fun isLetterString(s: String): Boolean {
        /*if (s == null) return false*/

        if (s.isEmpty()) return false
        for (ch in s) {
            if (!ch.isLetter()) return false
        }
        return true
    }

    println(isLetterString("abc")) // OK
    // 컴파일 타임에 NPE 발견할 수 있음 + null에 대한 추가 검사 필요 없음
    // println(isLetterString(null)) // Null can not be a value of a non-null type String

    println("""
        null이 될 수도 있는 값은 타입 뒤에 물음표(?) 를 붙여 "null이 될 수 있는 타입"으로 지정해야함! 
          ㄴ null이 될 수 있는 타입은 원래 타입(물음표 ? 없는 타입) 의 상위 타입
          ㄴ null이 될 수 있는 타입의 변수에 항상 null이 될 수 없는 타입의 값을 대입할 수 있다. 
          
        런타임에 null이 될 수 없는 값은 null이 될 수 없는 값과 차이가 없고, 둘 사이의 구분은 컴파일 수준에서만 존재한다. 
    """)

    println("""
        Nothing? = 가장 작은 null이 될 수 있는 타입 (null 외에 어떤 값도 포함하지 않음) 
        Any? = 가장 큰 null이 될 수 있는 타입 (코틀린 타입 시스템 전체에서 가장 큰 타입)
    """)

    println("""
        널이 될 수 있는 타입은 원래 타입에 들어있는 어떤 프로퍼티나 메서드도 제공하지 않는다. (null에서는 일반적인 연산이 의미가 없기 때문) 
        상단 isLetterString() 함수의 파라미터 타입만 String? 로 바꾸면 모든 s 사용법이 잘못되었다고 컴파일 오류가 뜬다. 
          ㄴ isLetterString() 이 null이 될 수 있는 값을 처리하도록 코틀린에서 여러가지 방법을 제공한다. (4.2.2 고고)
    """)
}