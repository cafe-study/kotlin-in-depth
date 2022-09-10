package chap8.chap8_2_3

//abstract class Result {
sealed class Result {
    class Success(val value: Any) : Result() {
        fun showResult() {
            println(value)
        }
    }

    class Error(val message: String) : Result() {
        fun throwException() {
            throw Exception(message)
        }
    }
}

fun runComputation(): Result {
    try {
        val a = readLine()?.toInt() ?: return Result.Error("Missing first argument")
        val b = readLine()?.toInt() ?: return Result.Error("Missing second argument")

        println("Sum: ${a + b}")

        return Result.Success(a + b)
    } catch (e: NumberFormatException) {
        return Result.Error(e.message ?: "Invalid input")
    }
}

fun main() {
    val message = when (val result = runComputation()) {
        is Result.Success -> "Completed Successfully : ${result.value}"
        is Result.Error -> "Error: ${result.message}"
//        else -> return
    }

    println(message)
}