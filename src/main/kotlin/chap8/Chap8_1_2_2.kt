package chap8.chap8_1_2_2

open class Person(
    val name: String,
    val age: Int
) {
    constructor(name: String) : this(name, 0)
}

class Student(name: String, age: Int, val university: String) : Person(name, age)

class Baby : Person {
    constructor(name: String) : super(name)
}
