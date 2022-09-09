package chap8.chap8_1_4

class Address(
    val city: String,
    val street: String,
    val house: String
) {
    // intellij 에서 생성해준 equals/hashCode
//    override fun equals(other: Any?): Boolean {
//        if (this === other) return true
//        if (javaClass != other?.javaClass) return false
//
//        other as Address
//
//        if (city != other.city) return false
//        if (street != other.street) return false
//        if (house != other.house) return false
//
//        return true
//    }
//
//    override fun hashCode(): Int {
//        var result = city.hashCode()
//        result = 31 * result + street.hashCode()
//        result = 31 * result + house.hashCode()
//        return result
//    }
}

open class Entity(
    val name: String,
    val address: Address
)

class Person(
    name: String,
    address: Address,
    val age: Int
) : Entity(name, address)

class Organization(
    name: String,
    address: Address,
    val manager: Person
) : Entity(name, address)

fun main() {
    val addresses = arrayOf(
        Address("London", "Ivy Lane", "BA"),
        Address("New York", "Kingsway West", "11/B"),
        Address("Sydney", "North Road", "129")
    )

    // address에 equals 메소드를 오버라이드하기 전까지는 의도한 대로 동작하지 않는다.
    println(addresses.indexOf(Address("Sydney", "North Road", "129")))

    // 코틀린의 ==, != 연산자는 모두 equals를 사용해 구현된다.
    val addr1 = Address("London", "Ivy Lane", "BA")
    val addr2 = addr1 // addr1과 같은 인스턴스
    val addr3 = Address("London", "Ivy Lane", "BA") // addr1과 다른 인스턴스이나 동일한

    println(addr1 === addr2)    // true
    println(addr1 == addr2)     // true
    println(addr1 === addr3)    // false
    println(addr1 == addr3)     // true
}