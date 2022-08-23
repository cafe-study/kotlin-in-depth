package chap4

import java.lang.IllegalArgumentException
import java.util.*

fun main() {
    println("[Chapter 4.3.3] 커스텀 접근자 사용하기 ")

    println("""
       코틀린 프로퍼티는 변수와 함수의 동작을 한 선언 안에 조합할 수 있다.
       커스텀 접근자(getter/setter) 는 프로퍼티 값을 읽거나 쓸 때 호출되는 특별한 함수다. 
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
       fullName은 프로퍼티 형태인 함수와 같다
       => fullName은 firstName, familyName과 달리 뒷받침하는 필드(backing field)가 없어 프로퍼티를 읽을 때마다 다시 계산된다. 
     
       * backing field => 프로퍼티의 실제 값을 저장하는 변수. (예약어 같은..)
         아래 코드 예시) field를 쓰면 get() 로직 안에서 age 값을 사용한다.
                     age 를 쓰면 자기참조로 stackoverflow 가 난다. (순환참조)

         backing field는 java 클래스로 디컴파일했을 때 프로퍼티 value를 담기 위해 java field로 생성된다.
    """)

    class Person2(val firstName: String, val familyName: String, age:Int) {
        val age: Int = age
            get(): Int {
                println("Accessing age")
                return field // backing field 참조 시 field 키워드 사용
                // return age // Age
            }
    }

    println("""
        이번엔 setter!    
          * setter 파라미터는 단 하나이며 프로퍼티의 타입과 같아야 함 (보통은 파라미터 타입을 미리 알 수 있기 때문에 setter에서 타입 생략함)
          * 관습적으로 value 로 정하는 경우가 많지만 다른 이름도 괜찮다.
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
        기본 setter - 항상 backing field 생성됨
        커스터마이즈 setter - 커스터마이즈 setter에서 field 접근하면 생성, 아니면 생성 안됨  
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

    println("""
        코틀린 코딩 관습
        * 값을 계산할 떄 예외가 발생할 여지가 없거나, 값을 계산하는 비용이 싸거나, 값을 캐시해두거나, 여러번 프로퍼티를 읽거나 함수를 호출해도 
          항상 똑같은 결과를 내는 경우 -> 함수보다 프로퍼티를 사용하는 쪽을 권장한다.
    """)
}