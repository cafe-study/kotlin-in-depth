package chap8.chap8_1_4_2

class Address(
    val city: String,
    val street: String,
    val house: String,
) {
//    override fun toString(): String {
//        return "Address(city='$city', street='$street', house='$house')"
//    }
}

fun main() {
    // toString 구현 전: chap8.chap8_1_4_2.Address@3a03464
    // toString 구현 후: Address(city='London', street='Ivy Lane', house='8A')
    println(Address("London", "Ivy Lane", "8A"))

    var nonAddress: Address? = null
    println(nonAddress)
}