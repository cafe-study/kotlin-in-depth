package chap4

fun main() {
    println("[Chapter 4.2.5] 엘비스 연산자 ")

    println("""
       null이 될 수 있는 값을 다룰 때 유용한 연산자로 null 복합 연산자인 ?:을 들 수 있다.
       ?: 연산자를 사용하면 null을 대신할 디폴트 값을 지정할 수 있다.
    """)

   fun sayHello(name: String?) {
       println("Hello, "+ (name ?: "Unknown"))
       // 아래와 동일
       //println("Hello, " + (if (name != null) name else "Unknown"))
   }

    sayHello("John")
    sayHello(null)

    println("""
       return 이나 throw같은 제어 흐름을 깨는 코드도 엘비스 연산자 오른쪽에 넣을 수 있다. (if식을 대신할 수 있음) 
    """)


    class Name(val firstName: String, val familyName: String?)

    class Person(val name: Name?) {
        fun describe(): String {
            val currentName = name ?: return "Unknown"
            return "${currentName.firstName} ${currentName.familyName}"
        }
    }

    println(Person(Name("John", "Doe")).describe()) // John Doe
    println(Person(null).describe()) // Unknown
}