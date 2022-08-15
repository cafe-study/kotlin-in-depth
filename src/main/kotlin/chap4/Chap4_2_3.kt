package chap4

fun main() {
    println("[Chapter 4.2.3] 널 아님 단언 선언자 ")

    println("""
       !! 연산자 => null 아님 단언 (KotlinNullPointerException 을 낼 수 있는 연산자다....!)
       
       상황에 따라 Null이 아닌 값만 포함되는 경우가 생길 수 있고, 그럴때 임의로 널값이 안들어온다는 보증을 해주는 연산자가 !! 연산자이다. 
       하지만 널값이 들어오게 되면 오류가 발생하므로 주의해서 써야 한다.

       일반적으로 null이 될 수 있는 값을 사용할 때 에러가 나는 것보다 더 타당한 응답을 제공해야 하기에, 이 연산자를 사용하지 말아야 하는데 
       이 연산자 사용을 정당화 할 수 있는 경우가 아래와 같은 경우이다.
    """)

    var name: String? = null

    fun initialize() {
        name = "John"
    }
    fun sayHello() {
        println(name!!.uppercase())
    }

    initialize()
    sayHello()

    println("""
       이름이 null이 아닌 값으로 initialize된 후 sayHello()가 호출됨 -> null 아님 단언도 적절한 해법이다. 
       하지만 컴파일러는 이 사용이 안전하다고 인식하지 못하므로, 코드를 바꿔서 컴파일러가 스마트 캐스트를 적용할 수 있도록 하는 편이 낫다. 
    """)

    println("""
       다른 후위 연산자와 마찬가지로 null 아님 연산자도 가장 높은 우선순위로 취급된다.
    """)
}