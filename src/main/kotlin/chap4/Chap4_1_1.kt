package chap4

fun main() {
    println("[Chapter 4.1] 클래스 정의하기")

    println("""
     기본적인 클래스 구조, 인스턴스 초기화 방법, 가시성 문제 등을 알아본다.
     * 클래스는 인스턴스의 실제 데이터 위치를 가리키는 참조 타입이다.
       => 이러한 참조 값은 명시적으로 특별한 생성자 호출을 통해 생성되고, 객체의 모든 참조가 사라지면 garbage collector에 의해 자동으로 해제됨.
    """)

    println("[Chapter 4.1.1] 클래스 내부 구조")

    println("""
     클래스 정의 형태
      * 프로퍼티 : 특정 클래스와 연관된 변수, 프로퍼티에 계산이 포함될 수도 있음 
      * 메서드 : 클래스 내 멤버 함수
    """)

    class Person {
        var firstName: String = "" // 프로퍼티
        var familyName: String = ""
        var age: Int = 0

        fun fullName() = "$firstName $familyName" // function

        fun showMe() { //
            println("${fullName()}: $age")
        }
    }

    println(""" 
        this
          * 대부분의 경우 this가 디폴트로 가정되므로, 수신객체 내부에서는 this 생략 가능
          * this가 꼭 필요한 경우 (메서드 파라미터 = 프로퍼티명 같을 떄)
    """)

    class Person2 {
        var firstName: String = "" // 프로퍼티
        var familyName: String = ""

        fun setName(firstName: String, familyName: String) {
            this.firstName = firstName
            this.familyName = familyName
        }
    }

    println("""
        클래스 인스턴스를 사용하려면 인스턴스를 명시적으로 생성해야 한다.
         * 생성자 호출 > 인스턴스에 대한 힙 메모리 할당 > 생성자 코드를 호출하여 인스턴스 상태를 초기화한다.
    """)

    val person = Person()

    person.firstName = "John"
    person.familyName = "Doe"
    person.age = 25

    person.showMe() // John Doe: 25

    println("""
        디폴트 호출자, 커스텀 호출자도 만들 수 있음
        코틀린 클래스는 public (어디서나 클래스 사용 가능). internal이나 private으로도 지정할 수 있음 
         * java는 보통 package private 임. 
         * 코틀린은 파일과 클래스 이름을 똑같이 만들 필요 없음 (보통 하나만 들어있으면 같게 만들긴 함) 
    """)

}