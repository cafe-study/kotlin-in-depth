package chap4

fun main() {
    println("[Chapter 4.1.3] 멤버 가시성")

    println("""
     가시성 - 각각 어떤 영역에서 쓰일 수 있는지 결정 
       * public (공개) <= 디폴트
       * internal (모듈 내부)
           ㄴ 같은 모듈 안에서만 볼 수 있다. 
           ㄴ 모듈은 같이 컴파일된 kotlin 파일의 세트
             * Intellij IDEA 모듈, Maven 혹은 Gradle 프로젝트, 1번의 호출로 컴파일된 일련의 파일 Ant Task
       * protected (보호)
           ㄴ 같은 클래스 + 하위 클래스에서만 볼 수 있다. 
       * private (비공개) 
           ㄴ 같은 클래스 안에서만 볼 수 있다.
    """)

    println("""
        java는 디폴트가 package private -> 멤버가 속한 클래스가 들어있는 패키지 어디서나 볼 수 있음 
        코틀린은 디폴트가 public 
    """)

    class Person(private val firstName: String, private val familyName: String) {
        fun fullName() = "$firstName $familyName"
    }

    /*val person = Person("John", "Doe")
    println(person.firstName)
    println(person.fullName())*/

    println("""
       함수, 프로퍼티, 주생성자, 부생성자에 대해 가시성을 지원한다. 
       주생성자의 가시성을 지정하려면 constructor 키워드를 꼭 명시해야함 
       유일한 생성자가 private이면 이 클래스는 클래스 외부에서 인스턴스화 할 수 없다.
    """)

    class Person2 private constructor() {
        fun showMe() = println("Empty")
    }
    /*// Cannot access '<init>': it is private in 'Person2'
    Person2().showMe()*/
}