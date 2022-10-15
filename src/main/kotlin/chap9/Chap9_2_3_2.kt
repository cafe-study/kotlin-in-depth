package chap9_2_3

interface Producer<out T> {
    fun produce(): T
}

interface Consumer<in T> {
    fun consume(value: T)
}

fun main() {
    //[CONFLICTING_PROJECTION] Projection is conflicting with variance of the corresponding type parameter of Producer. Remove the projection or replace it with '*'
//    val inProducer: Producer<in String>
    // [REDUNDANT_PROJECTION] Projection is redundant: the corresponding type parameter of Producer has the same variance
    val outProducer: Producer<out String>
    // [REDUNDANT_PROJECTION] Projection is redundant: the corresponding type parameter of Consumer has the same variance
    val inConsumer: Consumer<in String>
    //[CONFLICTING_PROJECTION] Projection is conflicting with variance of the corresponding type parameter of Producer. Remove the projection or replace it with '*'
//    val outConsumer: Consumer<out String>
}