package chap13

import kotlinx.coroutines.*

fun main() {
    runBlocking {
        println("Parent task started")

        launch {
            println("Task A started")
            delay(200)
            println("Task A finished")
        }

        launch {
            println("Task B started")
            delay(200)
            println("Task B finished")
        }

        delay(100)
        println("Parent task finished")
    }
    println("Shutting down...")
}