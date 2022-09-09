package chap8.chap8_2_2_2

interface Car {
    fun move() {
        println("I'm ridding")
    }
}

interface Ship {
    fun move() {
        println("I'm sailing")
    }
}

class Amphibia : Car, Ship {
    override fun move() {
        // compile error
//        super.move()
        super<Car>.move()
        super<Ship>.move()
    }
}

fun main() {
    Amphibia().move()
}