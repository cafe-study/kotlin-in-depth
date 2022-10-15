package chap9_2_2

interface MutableList<out T> : List<T> {
    // [TYPE_VARIANCE_CONFLICT_ERROR] Type parameter T is declared as 'out' but occurs in 'in' position in type T
    // fun set(index:Int, value: T)
}

// 제네릭이 생산자 타입으로 사용되는 예시
interface LazyList<out T> {
    // 반환 타입으로 쓰임
    fun get(index: Int): T

    // 반환 타입의 out 타입 파라미터로 쓰임
    fun subList(range: IntRange): LazyList<T>

    // 함수 타입의 반환값 부분도 out 위치임
    fun getUpTo(index: Int): () -> List<T>
}

class Writer<in T> {
    // 함수 인자로 쓰임
    fun write(value: T) {
        println(value)
    }

    // in 위치에 사용된 Iterable 제네릭 타입의 out 위치 인자로 T를 사용함
    // 이런 경우 위치가 in 위치로 인정됨
    fun writeList(values: Iterable<T>) {
        values.forEach { println(it) }
    }

    // 참고: 생성자 파라미터는 제네릭 타입의 인스턴스가 존재하기 전에 호출되므로, 위와 같은 검사에 대해 예외이다.
}

fun main() {
    val numberWriter = Writer<Number>()

    // 맞음: Writer<Number>가 Int도 처리 가능
    val integerWriter: Writer<Int> = numberWriter

    integerWriter.write(100)
}