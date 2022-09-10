package chap8.chap8_2_4

interface PersonData {
    val name: String
    val age: Int
}

open class Person(
    override val name: String,
    override val age: Int
) : PersonData

data class Book(val title: String, val author: PersonData) {
    override fun toString() = "'$title' by ${author.name}"
}

// 작가들의 필명을 사용할 수 있도록 하는 것을 위임을 통해 해결하는 예제
class Alias(
    private val realIdentity: PersonData,
    private val newIdentity: PersonData
) : PersonData {
    override val name: String
        get() = newIdentity.name
    override val age: Int
        get() = newIdentity.age
}

fun main() {
    val valWatts = Person("Val Watts", 30)
    val johnDoe = Alias(valWatts, Person("John Doe", 25))
    val introKotlin = Book("Introduction to Kotlin", johnDoe)

    println(introKotlin)
}