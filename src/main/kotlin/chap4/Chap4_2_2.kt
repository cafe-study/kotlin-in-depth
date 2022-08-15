package chap4

fun main() {
    println("[Chapter 4.2.2] 널 가능성과 스마트 캐스트 ")

    println("""
        널이 될 수 있는 값을 처리하는 가장 직접적인 방법 = null 조건문 처리 
        s 타입을 바꾸지 않았는데도 null 검사를 추가하면 컴파일이 된다!
          ㄴ 스마트 캐스트 : null 체크를 통해 코드 흐름 중 확실하게 null이 아닌 구문이 나오면, 컴파일이 값 타입을 세분화하여 
                         null이 될 수 있는 값을 null이 될 수 없는 값으로 타입 변환 한다.
                         스마트 캐스트는 널 가능성에만 제한되지 않음. (클래스 계층에서도 가능한데, 이는 8장에서 더...)
    """)

    fun isLetterString(s: String?): Boolean {
        if (s == null) return false

        // 아래부터는 null이 될 수 없음
        if (s.isEmpty()) return false
        for (ch in s) {
            if (!ch.isLetter()) return false
        }
        return true
    }

    println("널 검사와 사용 지점 사이에서 값이 변경되는 경우 스마트 캐스트가 동작하지 않는다.")

   /*var s = readLine() // String?
   if (s != null) {
        s = readLine()
        // 변수값이 바뀌므로 스마트 캐스트 쓸 수 없음
        // Only safe (?.) or non-null asserted (!!.) calls are allowed on a nullable receiver of type String?
        println(s.length)
    }*/
}