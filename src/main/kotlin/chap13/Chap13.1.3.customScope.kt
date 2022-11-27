package chap13

import kotlinx.coroutines.*

fun main() {
    runBlocking {
        println("Custom scope start")

        coroutineScope {
            launch {
                delay(200)
                println("Task A finished")
            }

            launch {
                delay(200)
                println("Task B finished")
            }
        }

        println("Custom scope end")
    }
}