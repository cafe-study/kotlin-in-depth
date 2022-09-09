package chap8.chap8_2_2

// 인터페이스는 interface 키워드로 정의
// 멤버는 디폴트로 abstract
interface Vehicle {
    val currentSpeed: Int
    fun move()
    fun stop()
}

// 다른 인터페이스의 상위 타입이 될 수 있음
interface FlyingVehicle : Vehicle {
    val currentHeight: Int
    fun takeOff()
    fun land()
}

// 다른 클래스가 구현할 수 있음
class Car : Vehicle {
    // 상속시 val -> var로 변경 가능
    override var currentSpeed = 0

    override fun move() {
        println("Riding..")
        currentSpeed = 50
    }

    override fun stop() {
        println("Stopped")
        currentSpeed = 0
    }
}

class Aircraft : FlyingVehicle {
    override var currentSpeed = 0
        private set
    override var currentHeight = 0
        private set

    override fun move() {
        println("Taxiing...")
        currentSpeed = 50
    }

    override fun stop() {
        println("Stopped")
        currentSpeed = 0
    }

    override fun takeOff() {
        print("Tacking off...")
        currentSpeed = 500
        currentHeight = 5000
    }

    override fun land() {
        println("Landed")
        currentSpeed = 50
        currentHeight = 0
    }
}

// 인터페이스 안의 함수와 프로퍼티에 구현을 추가할 수 있음
interface Vehicle2 {
    val currentSpeed: Int

    val isMoving get() = currentSpeed != 0

    fun move()

    fun stop()

    fun report() {
        println(if (isMoving) "Moving at $currentSpeed" else "Still")
    }
}
