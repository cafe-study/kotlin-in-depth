package chap9_2_4

interface Named {
    val name: String
}

interface Identified {
    val id: Int
}

class Registry<T> where T : Named, T : Identified

fun main() {
    val registry: Registry<*>? = null
    //println(registry?.id ?: "") // [UNRESOLVED_REFERENCE] Unresolved reference: id
    //println(registry?.name ?: "") // [UNRESOLVED_REFERENCE] Unresolved reference: name
}
