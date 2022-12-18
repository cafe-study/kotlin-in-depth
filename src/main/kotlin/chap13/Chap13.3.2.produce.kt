package chap13

import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        val channel = produce {
            for (n in 1..5) {
                val square = n * n
                println("Sending: $square")
                send(square)
            }
        }

        launch {
            channel.consumeEach {
                println("Receiving: $it")
            }
        }
    }
}