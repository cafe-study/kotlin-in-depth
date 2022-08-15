package chap4

import java.lang.IllegalArgumentException
import java.util.*

fun main() {
    println("[Chapter 4.3.3] 커스텀 접근자 사용하기 ")

    println("""
       커스텀 접근자 -> 코틀린에서는 변수와 함수의 동작을 한 선언 안에 조합할 수 있다.
       커스텀 접근자는 프로퍼티 값을 읽거나 쓸 때 호출되는 특별한 함수다. 
    """)

    println("""
       getter는 프로퍼티 정의 끝에 get()으로 붙으며, 파라미터가 없고 반환 타입은 프로퍼티의 타입과 같아야 한다.
       person.fullName 을 읽으면 자동으로 getter를 호출한다. 
    """)

    class Person(val firstName: String, val familyName: String) {
        val fullName: String
            get(): String {
                return "$firstName $familyName"
            }

        // Getter return type must be equal to the type of the property.
        /*val fullName: Any
            get(): String {
                return "$firstName $familyName"
            }*/
    }

    println("""
       fullName은 firstName, familyName과 달리 backing field가 없어 프로퍼티를 읽을 때마다 다시 계산된다. 
       fullName은 프로퍼티 형태인 함수와 같다
        * backing field => 간단히는 java field. java 클래스로 디컴파일했을 때 프로퍼티 value를 담기 위해 java field가 생성된다.
          * ex) private int size;
    """)

    class Person2(val firstName: String, val familyName: String, age:Int) {
        val age: Int = age
            get(): Int {
                println("Accessing age")
                return field // backing field 참조 시 field 키워드 사용
            }
    }

    println("""
       backing field 참조는 field 키워드 사용하며 접근자의 본문 안에서만 유용하다.
        * 디폴트 getter/setter -> backing field 생성
        * 커스터마이즈 getter/setter -> field 키워드 사용하면 backing field 생성, 아니면 생성하지 않는다.
        * 프로퍼티에 backing field가 없다면 필드를 초기화 할 수 없다.
        * 첫 예제처럼 getter에 backing field 참조 없이 getter만 있으면 fullName 초기화하지 않는다.
    """)

    println("""
        이번엔 setter 보기!    
          * setter 파라미터는 단 하나이며 프로퍼티의 타입과 같아야 함 (보통은 파라미터 타입을 미리 알 수 있기 때문에 setter에서 타입 생략함)
          * 관습적으로 value 로 정하는 경우가 많지만 다른 이름도 괜찮다.
          * 프로퍼티를 초기화하면 값을 바로 backing field에 쓰기 떄문에 프로퍼티를 초기화하는 setter를 호출하지 않는다.
    """)

    class Person3(val firstName:String, val familyName: String) {
        var age: Int? = null
            set (value) {
                if (value != null && value <= 0) {
                    throw IllegalArgumentException("Invalid age: $value")
                }
                field = value
            }
    }

    println("""
        기본 getter/setter - backing field 생성됨
        기본 getter/커스터마이즈 setter, 
        커스터마이즈 getter/기본 setter,
        커스터마이즈 getter/커스터마이즈 setter - 커스터마이즈 setter에서 field 접근하면 생성, 아니면 생성 안됨  
    """)

    class Person4(var firstName: String, var familyName: String, age:Int) {
        var fullName: String
            get(): String = "$firstName $familyName"
            set(value) {
                val names = value.split(" ")
                if (names.size != 2) {
                    throw IllegalArgumentException("Size Error")
                }
                firstName = names[0]
                familyName = names[1]
            }
    }

    println("""
        private 가시성을 붙이면 외부에서 프로퍼티 값 변경 못하게 할 수 있음.
        디폴트 getter/setter 역할일 경우 그냥 get/set 키워드만 쓸 수 있음.
    """)

    class Person5(name: String) {
        var lastChanged: Date? = null
            private set  // Person class 밖에서는 변경할 수 없다

        var name: String = name
            set(value) {
                lastChanged = Date()
                field = value
            }
    }
}