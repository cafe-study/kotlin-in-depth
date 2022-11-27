package chap13

import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun main() {
    newSingleThreadContext("Worker").use { worker ->
        runBlocking {
            println(Thread.currentThread().name)

            withContext(worker) {
                println(Thread.currentThread().name)
            }

            println(Thread.currentThread().name)
        }
    }
}