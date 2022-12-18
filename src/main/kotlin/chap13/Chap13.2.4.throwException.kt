package chap13

import kotlinx.coroutines.*

suspend fun main() {
    val handler = CoroutineExceptionHandler { _, throwable ->
        println("caught $throwable")
    }

    GlobalScope.launch(handler) {
        launch{
            throw Exception("Error in task A")
            println("Task A completed")
        }

        launch {
            delay(1000)
            println("Task B completed")
        }

        println("Root")
    }.join()
}