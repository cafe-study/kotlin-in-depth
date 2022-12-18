package chap13

import kotlinx.coroutines.*

fun main() {
    runBlocking {
        val job = coroutineContext[Job.Key]!!

        launch { println("This is task A") }
        launch { println("This is task B") }

        println("${job.children.count()} children running")
    }
}