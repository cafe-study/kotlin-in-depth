package chap13

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.runBlocking

fun main() {
    newFixedThreadPoolContext(5, "WorkerThread").use { dispatcher ->
        runBlocking {
            for (i in 1..3) {
                launch(dispatcher) {
                    println(Thread.currentThread().name)
                    delay(1000)
                }
            }
        }
    }
}