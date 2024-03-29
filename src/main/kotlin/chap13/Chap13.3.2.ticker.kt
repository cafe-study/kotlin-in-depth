package chap13

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

fun main() {
    runBlocking {
        val ticker = ticker(100)
        println(withTimeoutOrNull(50) { ticker.receive() })
        println(withTimeoutOrNull(60) { ticker.receive() })
        delay(250)
        println(withTimeoutOrNull(1) { ticker.receive() })
        println(withTimeoutOrNull(60) { ticker.receive() })
        println(withTimeoutOrNull(60) { ticker.receive() })
    }
}