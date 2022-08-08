package chap2

fun main() {
    println("[Chapter 2.1.3] 식별자")

    println("""
        식별자는 오직 문자, 숫자, 밑줄 문자(_)만 포함한다.
        숫자로 시작할 수 없다.
        밑줄로만 이뤄질 수도 있다. (그러나 예약된 식별자는 불가하다)
        하드 키워드(hard keyword)를 식별자로 쓸 수는 없다. 
          하드 키워드 (val, fun, class, do, else, for 등) https://kotlinlang.org/docs/keyword-reference.html#hard-keywords
          소프트 키워드 (import, catch, constructor 등) https://kotlinlang.org/docs/keyword-reference.html#soft-keywords
          - 특별한 문맥에서만 키워드로 간주
          
        달러 기호($)를 쓸 수 없다. (자바와의 차이점)
        
        작은 역따옴표(`)로 감싼 식별자(identifier) 가능하며
        작은 역따옴표(`)로 사이에는 빈 문자열을 제외한 아무 문자열이나 와도 된다
    """)

    val `fun` = 1
    val `name with spaces 테스트` = 2 // space 포함
    val ` ` = 3
    // val `` = 4 // (빈문자열만 있을 경우 error)
    // val $test = 5 // (빈문자열만 있을 경우 error)
    println(`fun` + `name with spaces 테스트` + ` `)


    println("""
        작은 역따옴표(`, grave accent) 등장 배경
        자바에서 식별자로 사용할 수 있는 단어가 코틀린에서 키워드인 경우가 있다 (e.g fun 은 코틀린에서 키워드지만 자바에서는 아님)
        코틀린에서 자바에 정의한 식별자 사용시 활용됨
    """)
}