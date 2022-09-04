package chap8.chap8_1_2_1

open class Vehicle {
    init {
        println("initializing Vehicle")
    }
}

open class Car : Vehicle() {
    init {
        println("initializing Car")
    }
}

class Truck: Car() {
    init {
        println("initializing Truck")
    }
}

fun main() {
    Truck()
}

// 출력 결과
//initializing Vehicle
//initializing Car
//initializing Truck
