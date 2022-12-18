package chap13

import kotlinx.coroutines.*

fun main() {
    runBlocking {
        val job = launch(start = CoroutineStart.LAZY) {
            println("Job started")
        }

        delay(100)

        println("preparing to start...")
        job.start()
    }
}