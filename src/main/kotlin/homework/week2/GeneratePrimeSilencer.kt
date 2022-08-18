package homework.week2


/**
 * [소수 만들기](https://school.programmers.co.kr/learn/courses/30/lessons/12977)
 */
class GeneratePrimeSilencer {
    fun solution(nums: IntArray): Int {
        return combinationGenerator(nums).count {
            isPrime(it.sum())
        }
    }

    private fun combinationGenerator(array: IntArray) = sequence {
        for (i in 0 until array.size - 2) {
            for (j in i + 1 until array.size - 1) {
                for (k in j + 1 until array.size) {
                    val result = intArrayOf(array[i], array[j], array[k])
                    yield(result)
                }
            }
        }
    }

    private fun isPrime(number: Int): Boolean {
        for (i in 2..number / 2) {
            if (number % i == 0) {
                return false
            }
        }

        return true
    }
}