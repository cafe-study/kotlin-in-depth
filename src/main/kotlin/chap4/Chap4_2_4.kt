package chap4

fun main() {
    println("[Chapter 4.2.4] 안전한 호출 연산자 ")

    println("""
       null이 될 수 있는 타입은 해당 타입 메서드를 사용할 수 없다고 했는데
       안전한 호출 연산 (?.)을 이용하면, 이런 제약을 피할 수 있다.
    """)

    // 표준 I/O일 경우 정상동작. 그러나 파일을 입력받는다면 파일이 비어있는 경우 KotlinNullPointerException 발생시킬 수 있음
    fun readInt() = readLine()!!.toInt()

    // raadInt2() 는 readInt3() 으로 작성될 수 있다.
    fun readInt2() = readLine()?.toInt()
    fun readInt3(): Int? {
        val tmp = readLine()
        return if (tmp != null) tmp.toInt() else null
    }

    println("""
       안전한 호출 연산자(?.)는 왼쪽 피연산자가 null이 아닌 경우 일반적인 함수 호출처럼 동작하고
       null인 경우 호출을 수행하지 않고 null을 리턴한다. 
       
       "수신 객체가 null이 아닌 경우 의미 있는 일을 하고, 수신 객체가 null인 경우 null을 반환하라" 
       
       안전한 호출 연산자(?.)가 null을 반환할 수 있기 때문에, 반환하는 값의 타입도 null이 될 수 있는 버전이다.
    """)

    fun readInt4() = readLine()?.toInt()?.dec()
    val n = readInt4() // Int? 로 반환됨

    // Only safe (?.) or non-null asserted (!!.) calls are allowed on a nullable receiver of type Int?
    //println(n.toInt())
    if (n != null) {
        println(n + 1)
    } else {
        println("No Value")
    }
}