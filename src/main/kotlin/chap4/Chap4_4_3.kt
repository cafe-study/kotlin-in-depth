package chap4

fun main() {
    println("[Chapter 4.4.3] 객체 식 ")

    println("""
      코틀린에서는 명시적인 선언 없이 바로 객체를 생성할 수 있는 특별한 식을 제공한다 (=자바의 익명클래스)
      객체 식은 이름이 없는 객체 정의처럼 보이며, 식이므로 객체 식이 만들어내는 값을 변수에 대입할 수 있다.
      
      * 클래스 생성 -> 객체 생성 -> 메소드 로 호출하지 않고, 람다식 자체만으로도 메소드 역할을 수행할 수 있다.
    """)

    fun midPoint(xRange: IntRange, yRange: IntRange) = object {
        val x = (xRange.first + xRange.last) / 2
        val y = (yRange.first + yRange.last) / 2
    }

    val midPoint = midPoint(1..5, 2..6)
    println("${midPoint.x}, ${midPoint.y}") // 3, 4


    println("""
        midPoint() 함수가 반환하는 객체에 대해 명시적으로 타입을 지정하지 않음 
        => 익명 객체 타입(anonymous object type)을 반환함. 이런 타입은 단 하나만 존재한다. 
        (멤버가 모두 완전히 같은 두 객체 식이 있다고 해도, 둘의 타입은 서로 다르다)
    """)

    val obj = object { // val obj: <anonymous object : Any> => obj 는 익명 객체 타입이 추론됨
        val x = readLine()!!.toInt()
        val y = readLine()!!.toInt()
    }
    println(obj.x)

    println("""
        지역 함수나 클래스와 같이, 객체 식도 자신을 둘러싼 코드 영역의 변수를 포획/변경할 수 있다. 
        (컴파일러가 지역 클래스와 비슷하게 데이터를 공유하기 위해 필요한 랩퍼를 생성해준다)
    """)

    var x = 1
    val o = object {
        fun change() {
            x = 2
        }
    }
    o.change()
    println(x) // 2


    println("""
        객체 식이 만들어내는 객체는 인스턴스가 생성된 직후 바로 초기화된다. 
        아래의 경우 객체가 생성되는 시점에 c가 초기화되므로, a 값으로 2가 표시된다.
    """)

    var a = 1
    val b = object {
        val c = a++
    }
    println("b.c = ${b.c}") // b.c = 1
    println("a = $a") // x = 2


    println("""
        객체 식은 클래스 상속과 조합했을 때 더 강력해진다고 해요....... 이는 8장에서 계속....!
    """)
}