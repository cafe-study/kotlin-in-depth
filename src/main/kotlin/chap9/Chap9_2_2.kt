package chap9_2_2

interface List<out T> {
    val size: Int
    fun get(index: Int): T
}

class ListByArray<T>(private vararg val items: T) : List<T> {
    override val size: Int
        get() = items.size

    override fun get(index: Int) = items[index]
}

fun <T> concat(list1: List<T>, list2: List<T>) = object : List<T> {
    override val size: Int
        get() = list1.size + list2.size

    override fun get(index: Int): T {
        return if (index < list1.size) {
            list1.get(index)
        } else {
            list2.get(index - list1.size)
        }
    }
}

fun main() {
    val numbers = ListByArray<Number>(1, 2.5, 3f)
    val integers = ListByArray(10, 20, 30)

    // interface List<T>로 정의하면 [TYPE_MISMATCH] Type mismatch. -> List<Number>과 List<Int>는 무공변
    val result = concat(numbers, integers)
}