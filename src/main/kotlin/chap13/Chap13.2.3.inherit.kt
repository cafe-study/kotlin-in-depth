package chap13

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        println("Root: ${Thread.currentThread().name}")

        launch {
            println("Nested, inherited: ${Thread.currentThread().name}")
        }

        launch(Dispatchers.Default) {
            println("Nested, explicit: ${Thread.currentThread().name}")
        }
    }
}