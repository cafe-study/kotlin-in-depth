package chap8.chap8_1_3

fun main() {
    println("1" is String)
    println(2 is Int)
    println(null is Int)
    println(null is String?)
    // println("" is Int) // error
    println("" is String?)
    println("" !is String?)

    val objects = arrayOf("1", 2, "3", 4)
    var sum = 0

    for (obj in objects) { // obj: Any
        when(obj) {
            is Int -> sum += obj // obj: Int
            is String -> sum += obj.toInt() // obj: String
        }
    }

    println("sum=${sum}")


    val o: Any = 123
    println((o as Int) + 1) // 124
    println((o as? Int)!! + 1) // 124
    println((o as? String ?: "").length) // 0
    println((o as String).length) // throw java.lang.ClassCastException
}