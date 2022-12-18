package chap13

import kotlinx.coroutines.*
import java.lang.Exception
import java.io.File

suspend fun main () {
    runBlocking {
        val asyncData = async {
            File("data.txt").readText()
        }

        try {
            val text = withTimeout(50) {
                asyncData.await()
            }
            println("Data loaded: $text")
        } catch (e: Exception) {
            println("Timeout exceeded")
        }
    }
}