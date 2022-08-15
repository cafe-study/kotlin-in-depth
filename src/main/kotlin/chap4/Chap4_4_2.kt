package chap4

class Application2 private constructor (val name: String) {
    object Factory {
    // companion object Factory {
    // companion object {
        fun create(arg: String): Application2? {
            return Application2(arg)
        }
    }
}

fun main() {
    println("[Chapter 4.4.2] 동반 객체 ")

    println("""
      생성자를 직접 사용하고 싶지 않을 때, 생성자를 비공개로 지정 후 내포된 객체에 factory method 역할을 하는 함수를 정의하고 
      그 함수 내에서 객체의 생성자를 호출하면 된다. (Application2 참조)
      * 주석1 : companion object
        * 매번 내포된 객체 이름을 지정해야 하는데, 이 때 동반객체 (companion object)로 정의하면 문제 해결할 수 있다.
        * 동반 객체의 멤버에 접근할 때에는 동반 객체의 이름을 쓰지 않고 외부 클래스 이름으로 사용 가능.
      * 주석2 : companion object 이름 생략 가능 (이 방식을 더 권장)
        * 동반 객체 이름을 생략한 경우, 컴파일러는 이름을 Companion 으로 가정함 (따라서 동반객체가 둘 이상 있을 수 없음)
    """)

    val app = Application2.Factory.create("abc") ?: return
    // val app = Application2.create("abc") ?: return
    println("Application started!: ${app.name}")

    println("""
        코틀린의 companion object를 자바의 static 과 대응되는 것으로 생각할 수도 있다.
        비슷하지만, 중요한 차이는 코틀린 동반 객체는 "객체 인스턴스" 라는 점이다. 
        이로 인해 다른 상위 타입을 상속하거나 일반 객체처럼 전달되는 등, 자바의 정적 멤버/클래스보다 더 유연하다고 한다. 
        (자세한 건 8/11장에서...)
    """)

}