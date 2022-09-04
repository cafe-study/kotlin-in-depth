package chap8.chap8_1_2_3

open class Person(
    val name: String,
    val age: Int
) {
    open fun showInfo() {
        println("$name, $age")
    }

    init {
        showInfo()
    }
}

class Student(
    name: String,
    age: Int,
    val university: String
) : Person(name, age) {
    override fun showInfo() {
        println("$name, $age (student at $university)")
    }
}

fun main() {
    Student("Euan Reynolds", 25, "MIT") // Euan Reynolds, 25 (student at null)
}