package chap9_3

// 타입
typealias IntPredicate = (Int) -> Boolean
typealias IntMap = HashMap<Int, Int>

fun readFirst(filter: IntPredicate) = generateSequence { readLine()?.toIntOrNull() }.firstOrNull(filter)

fun main() {
    val map = IntMap().also {
        it[1] = 2
        it[2] = 3
    }

    //[TOPLEVEL_TYPEALIASES_ONLY] Nested and local type aliases are not supported
    //typealias A = Int
}

sealed class Status {
    object Success : Status()
    class Error(val message: String) : Status()
}

// 내포된 클래스
typealias stSuccess = Status.Success
typealias stError = Status.Error

// 타입 별명도 제네릭을 포함할 수 있음
typealias ThisPredicate<T> = T.() -> Boolean
typealias MultiMap<K, V> = Map<K, Collection<V>>

// 타입 별명에 가시성 부여 가능
private typealias MyMap = Map<String, String>

// [BOUND_ON_TYPE_ALIAS_PARAMETER_NOT_ALLOWED] Bounds are not allowed on type alias parameters
// typealias ComparableMap<K: Comparable<K>, V> = Map<K, V>