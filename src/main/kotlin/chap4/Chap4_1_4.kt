package chap4


class Person(val id: Id, val age: Int) {
    class Id (val firstName: String, val familyName: String)
    fun showMe() = println("${id.firstName} ${id.familyName}, $age")
}

// 주석1 - 내포된 클래스는 바깥쪽 클래스의 private에 접근할 수 없다.
/*
class Outer {
    private val bar = 3

    inner class Inner {
        fun foo() = bar
    }

   class Nested {
        // Unresolved reference: bar
        fun foo() = bar
    }
}
*/

/*
// 주석2 - 바깥쪽 클래스는 내포된 클래스 private에 접근할 수 없다.
class Person(private val id: Id, private val age: Int) {
    class Id (private val firstName: String, private val familyName: String)

    // error = Cannot access 'firstName': it is private in 'Id'
    fun showMe() = println("${id.firstName} ${id.familyName}, $age")
}*/

fun main() {
    println("[Chapter 4.1.4] 내포된 클래스")

    println("""
        함수, 프로퍼티, 생성자 외에 코틀린 클래스는 다른 클래스도 멤버로 가질 수 있다.
        => 내포된 클래스 (nested class) 
        
        내포된 클래스 밖에서는 Person.Id 처럼 바깥쪽 클래스의 이름을 덧붙여야만 참조할 수 있다.
    """)

    val id = Person.Id("John", "Doe")
    val person = Person(id, 25)
    person.showMe()

    println("""
        내포된 클래스는 바깥쪽 클래스의 private에 접근할 수 없다. (상단 주석1)
        바깥쪽 클래스는 내포된 클래스 private에 접근할 수 없다. (상단 주석2) 
    """)

    println("""
       nested clasee (inner 없음) - 외부 클래스 instance 없어도 됨 (관계 없음)
       inner class (inner 있음) - 외부 클래스 Instance 있어야 함 
        * 하단 예시 - person2.Possession() 처럼 인스턴스를 지정해서 생성해야 함  
      
       inner 클래스는 자신을 둘러싼 외부 클래스의 인스턴스에 접근할 수 있다. 
    """)

    class Person(val firstName: String, val familyName: String) {
        inner class Possession(val description: String) {
            // 자신을 둘러싼 외부 클래스의 private 메서드 호출 가능
            fun showOwner() = println(fullName())
        }
        private fun fullName() = "$firstName $familyName"
    }

    val person2 = Person("John", "Doe")

    // 내부 클래스 생성자를 호출할 때, person.Possession("Wallet") 처럼 인스턴스의 메서드로 호출해줘야 한다.
    val wallet = person2.Possession("Wallet")
    wallet.showOwner() // John Doe

    println("""
        일반적으로 this는 가장 내부의 클래스 인스턴스를 가리킨다. 
        따라서 내부 클래스 본문에서 외부 클래스 인스턴스를 가리켜야한다면, 한정시킨 (qualified) this 식을 이용해야 한다.
        => this@{외부 클래스 이름}
    """)

    class Person3(val firstName: String, val familyName: String) {
        inner class Possession(val description: String) {
            fun getOwner() = this@Person3
        }
    }

    println("""
        코틀린에서는 (inner가 없는) 내포된 클래스는 외부 클래스 인스턴스와 연관되지 않는다.
        자바에서는 외부 클래스 인스턴스와 연관되길 원하지 않으면 명시적으로 static을 붙인다. 
    """)
}