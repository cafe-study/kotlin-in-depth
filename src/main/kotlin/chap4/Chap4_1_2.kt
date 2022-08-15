package chap4

import java.lang.IllegalArgumentException

fun main() {
    println("[Chapter 4.1.2] 생성자")

    println("생성자 - 클래스 인스턴스를 초기화해주고 인스턴스 생성 시 호출되는 특별한 함수 (new 사용 안함)")

    class Person (firstName: String, familyName: String) {
        val fullName = "$firstName $familyName"
    }

    val person = Person("John", "Doe")
    println(person.fullName)

    println("주생성자는 클래스 헤더의 파라미터 목록을 전달받고, 프로퍼티 초기화와 초기화 블록(init)으로 구성됨")

    class Person2 (firstName: String, familyName: String) {
        val fullName = "$firstName $familyName"

        init {
            println("1. Created new Person instance!: $fullName")
        }
        init {
            // init 순서대로 실행됨
            println("2. familyName: $familyName")
        }
        init {
            // 초기화 블록에는 return문 들어갈 수 없음
            //if (firstName.isEmpty() && familyName.isEmpty()) return
            println("3. return X")
        }
    }
    val person2 = Person2("Yoon", "Juwon")

    println("init 블록 안에서 프로퍼티를 초기화 하는 것도 가능함")

    class Person3 (fullName: String) {
        val firstName: String
        val familyName: String

        init {
            val names = fullName.split(" ")
            if (names.size != 2) {
                throw IllegalArgumentException("Invalid name: $fullName")
            }
            firstName = names[0]
            familyName = names[1]
        }
    }

    val person3 = Person3("John Doe")
    println(person3.firstName)


    println("컴파일러는 주생성자 내부 모든 경로에서 프로퍼티가 초기화되지 않는 경우 예외를 발생한다.")

    /*class Person4(fullName: String) {
        // Property must be initialized or be abstract
        val firstName: String
        val familyName: String
        init {
            val names = fullName.split(" ")
            if (names.size == 2) {
                firstName = names[0]
                firstName = names[1]
            }
        }
    }*/

    println("주 생성자 파라미터를 프로퍼티 초기화나 init 블록 밖에서 사용할 수는 없다")

    class Person5(firstName: String, familyName: String) {
        val fullName = "$firstName $familyName"

        /*fun printFirstName() {
            println(firstName) //Unresolved reference: firstName
        }*/

        // 해법 (새로운 멤버 프로퍼티에 바인딩)
        val firstName = firstName
        fun printFirstName() {
            println(firstName) // this.firstName이 되어 멤버 프로퍼티를 가리킴
        }
    }

    println(""" 
        코틀린에서 제공하는 방법!
        주생성자 파라미터 앞에 val/var 키워드를 붙이면, 자동으로 생성자 파라미터와 이름이 같은 프로퍼티를 정의해서 바인딩해줌
        (하단 코드 - 프로퍼티 초기화나 init 안에서는 생성자 파라미터를 가리키고, 다른 위치에서 참조하면 멤버 프로퍼티를 가리키게 됨) 
    """)

    class Person6(val firstName: String, familyName: String) {
        // firstName은 생성자 파라미터를 가리킴
        val fullName = "$firstName $familyName"

        fun printFirstName() {
            println(firstName) // firstName은 멤버 프로퍼티를 가리킴
        }
    }

    println(""" 
        아래와 같이 선언하면 간단하게 멤버 프로퍼티로 firstName, familyName을 가지는 Person7 선언 가능. 
        {} 도 생략 가능하다. 
    """)

    class Person7(val firstName: String, val familyName: String = "")
    // = class Person7(val firstName: String, val familyName: String = "") {}

    println("주생성자 선언에 디폴트값, 가변인자도 가능")

    class Person8(val firstName:String, val familyName: String = "") {
        fun fullName() = "$firstName $familyName"
    }
    class Room(vararg val persons: Person8) {
        fun showNames() {
            for (person in persons) println(person.fullName())
        }
    }

    val room = Room(Person8("John"), Person8("Jane", "Smith"))
    room.showNames()

    println(""" 
        부생성자
        * 주생성자로 충분하지 않는 경우 부생성자로 해결. 
        * 함수 이름 대신 constructor 키워드 사용. 
        * 부생성자에 리턴 타입을 지정할 수 없음. (Unit타입 값을 반환하는 함수와 마찬가지)
        * init 블록과 달리 부생성자 안에서는 return을 사용할 수 있다.
        * 부생성자 파라미터 목록에 var/val 를 쓸 수 없다.
    """)

    class Person9 {
        val firstName: String
        val familyName: String

        constructor(firstName: String, familyName: String) {
            this.firstName = firstName
            this.familyName = familyName
        }
        constructor(fullName: String) {
            val names = fullName.split(" ")
            if (names.size != 2) {
                throw IllegalArgumentException("Invalid name: $fullName")
            }
            firstName = names[0]
            familyName = names[1]
        }
    }

    println(""" 
        클래스에 주생성자를 선언하지 않은 경우 > 부생성자는 자신의 본문을 실행하기 전에 프로퍼티 초기화와 init 블록을 실행한다.
        어떤 부생성자를 호출하든지 공통적인 초기화 코드가 정확히 한번만 실행되도록 보장됨 
    """)

    println(""" 
        생성자 위임 호출 - 생성자 파라미터 목록 뒤에 콜론(:)을 넣고 그 뒤에 코드를 작성하고, 함수 이름 대신 this를 사용 
        (Q. 만약 로직이 있고 맨 아래에 this를 호출하는 경우?)
    """)

    class Person10(val fullName: String) {
        constructor(firstName: String, familyName: String) :
            this("$firstName $familyName")
    }
}