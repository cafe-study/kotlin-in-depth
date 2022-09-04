package chap8

class Car : Vehicle() {
    override fun start() {
        println("I'm riding")
    }
}

class Boat : Vehicle() {
    override fun start() {
        println("I'm sailing")
    }
}

fun startAndStop(vehicle: Vehicle) {
    vehicle.start()
    vehicle.stop()
}

fun main() {
    startAndStop(Car())
    startAndStop(Boat())
}


open class Entity(
    open val name: String = ""
)

class SubEntity(
    override var name: String
): Entity()