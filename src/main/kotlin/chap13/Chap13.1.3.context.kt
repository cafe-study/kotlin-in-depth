package chap13

import kotlinx.coroutines.*

private fun CoroutineScope.showName() {
    println("Current coroutine: ${coroutineContext[CoroutineName]?.name}")
}

fun main() {
    runBlocking {
        showName()
        launch(coroutineContext + CoroutineName("Worker")) {
            showName()
        }
    }
}