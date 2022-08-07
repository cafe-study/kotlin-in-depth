package chap2

fun main() {
    println("[Chapter 2.1]")

    /*
     "!!" not-null assertion, readLine 결과가 null 인 경우 예외를 발생시킴
     코틀린은 어떤 타입이 널 값이 될 수 있는지를 추적하고, 널이 아닌 것이 확실하지 않은 값에 대해 이후 행위를 수행하지 못하게 막음으로써
     널로 인한 오류를 방지한다.
     */
    /*
        main 함수안에 정의 한 input1, input2 는 지역 변수 (local variable)
        val 키워드는 타입 추론 (type inference)이라는 기능 때문에 가능
        컴파일러는 toInt() 함수가 Int 타입의 값 반환을 추론 가능
        타입 추론에 의해 코틀린은 강한 타입 지정(strongly typed) 언어다.
        주의) kotlin 은 암시적 변환(implicit conversions)을 제공하는 약한 타입 지정(weakly-type) 언어가 아니다

        https://stackoverflow.com/questions/2690544/what-is-the-difference-between-a-strongly-typed-language-and-a-statically-typed
     */
    val input1 = readLine()!!.toInt()
    val input2 = readLine()!!.toInt()
    println(input1 + input2)


    /*
     * tip) IntelliJ 에서 컴파일러에 의해 변수 타입 추론 값을 보기
     * intellij short key : option + shift + p
     */
    val number1 = 100
    val number2: Int = 100
    val text: String = "Hello!"
    // val n: Int = "Hello!" // Error, Type mismatch: inferred type is String but Int was expected

    /*
     * 변수 값을 읽기 전에 변수를 초기화 해야 한다.
     */
    val number10: Int
    // println(number10 + 1) // Error, Variable 'number10' must be initialized

}