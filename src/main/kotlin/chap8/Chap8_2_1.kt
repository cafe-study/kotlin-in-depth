package chap8.chap8_2_1

import kotlin.math.PI

// 추상 클래스는 abstract 키워드를 붙임
abstract class Entity(val name: String)

// 추상 클래스의 생성자는 오직 하위클래스 생성자의 위임호출로만 호출 가능
class Person(name: String, val age: Int) : Entity(name)

// 추상 클래스는 추상 멤버를 정의할 수 있으며, 하위 클래스에서 반드시 멤버를 오버라이드 해야 함
abstract class Shape {
    abstract val width: Double
    abstract val height: Double
    abstract fun area(): Double
}

class Circle(val radius: Double) : Shape() {
    private val diameter
        get() = 2 * radius
    override val width: Double
        get() = diameter
    override val height: Double
        get() = diameter

    override fun area() = PI * radius * radius
}

fun main() {
    // 추상 클래스는 인스턴스 생성 불가
    //    val entity = Entity("Unknown")
}