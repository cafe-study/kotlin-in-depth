package chap13

import kotlinx.coroutines.*
import java.lang.System.*

fun main() {
    val time = currentTimeMillis()

    GlobalScope.launch {
        delay(100)
        println("Task 1 finished in ${currentTimeMillis() - time}ms")
    }

    GlobalScope.launch {
        delay(100)
        println("Task 2 finished in ${currentTimeMillis() - time}ms")
    }

    Thread.sleep(200)
}