package chap4

/*// Unresolved reference: Point2
fun foo() {
    println(Point2(0, 0))
}*/

fun main() {
    println("[Chapter 4.1.5] 지역 클래스")

    println("""
        자바처럼 코틀린도 함수 본문에서 클래스를 정의할 수 있다.
        이런 지역 클래스는 자신을 둘러싼 코드 블록 안에서만 쓰일 수 있다.
          * 상단 주석 : 외부에서 호출 시 에러       
    """)

    // main 내부 지역 클래스
    class Point2(val x:Int, val y:Int) {
        fun shift(dx: Int, dy: Int): Point2 = Point2(x + dx, y + dy)
        override fun toString() = "($x, $y)"
    }

    println("지역클래스는 자신을 둘러싼 코드의 선언에 접근할 수 있으며, 클래스 본문 안에서 접근한 값을 변경할 수도 있다.")

    var x = 1
    class Counter {
        fun increment() {
            x++
        }
    }
    Counter().increment()
    println(x) // 2

    println("""
       자바에서는 포획한 변수의 값을 변경할 수 없다.
       그리고 익명 클래스 내부에서 외부 변수를 사용하고자 하면 명시적으로 final로 선언해야만 한다.
       
       코틀린 컴파일러는 대신 익명 객체와 둘러싸고 있는 코드 사이에 변수를 공유하기 위해, (공유되는) 값을 특별한 wrapper 객체로 둘러싼다. 
       => 변수를 참조값으로 전달할 수 있다!
       
       // 디컴파일 해보면 final Ref.IntRef x = new Ref.IntRef();
    """)

    println("""
       내포된 클래스와 달리 지역 클래스에는 가시성 변경자를 붙일 수 없다.
       지역 클래스의 영역은 항상 자신을 둘러싼 블록으로 제한된다!
       
       지역 클래스도 함수, 프로퍼티, 생성자, 내부 클래스(inner)를 가질 수 있다.
       클래스 안에 내포된 클래스를 허용하지 않음. 그 이유는?
         ㄴ 지역클래스(Foo)는 자신을 둘러싼 지역의 변수인 aa에 접근할 수 있다.
         ㄴ 내포된 클래스(nestBar)는 자신의 외부 클래스에서 사용할 수 있는 상태에 접근할 수 없음. (4.1.4 상단 주석1 참조)
         ㄴ 구문 영역에 따른 변수의 가시성 규칙이 지역 클래스 안의 내포된 클래스에서만 동작하지 않으면 얼핏 혼동을 야기하기 쉽기에 허용 X!
    """)

    var aa = 3
    class Foo {
        val bb = aa
        inner class Bar {
            var size = 10
        }
        /*// Class is not allowed here
        class nestBar {
            var size = 10
        }*/
    }
}