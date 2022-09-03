package chap8

open class Vehicle {
    var currentSpeed = 0

    fun start() {
        println("I'm moving")
    }

    fun stop() {
        println("Stopped")
    }
}

open class FlyingVehicle : Vehicle() {
    fun takeOff() {
        println("Taking off")
    }

    fun land() {
        println("Landed")
    }
}

class Aircraft(val seats: Int) : FlyingVehicle()

fun main() {
    val aircraft = Aircraft(200)
    val vehicle: Vehicle = aircraft

    vehicle.start()
    vehicle.stop()
    aircraft.start()
    aircraft.takeOff()
    aircraft.land()
    aircraft.stop()
    println(aircraft.seats)
}

/**
 * data class 는 open을 추가할 수 없음
 */
//open data class Person(val name: String, val age: Int)

/**
 * 인라인 클래스는 상속을 하는 것도, 부모 클래스로 동작하는 것도 불가능
 */
class MyBase

//open value class MyString(val value: String)

//value class MyStringInherited(val value: String) : MyBase()