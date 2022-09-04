package homework.week3

/**
 * [문자열 압축](https://school.programmers.co.kr/learn/courses/30/lessons/60057?language=kotlin)
 */
class StringCompression {
    fun solution(s: String): Int {
        var answer = Int.MAX_VALUE

        if (s.length == 1) {
            return 1
        }

        for (i in 1..s.length / 2) {
            val compressionPatternBuckets = s.chunked(i)
                .map { PatternBucket(it, 1) }
                .fold(mutableListOf()) { combineList: MutableList<PatternBucket>, bucket: PatternBucket ->
                    combineList.apply {
                        if (isNotEmpty() && last() isEqualPattern bucket) {
                            last().count += bucket.count
                        } else {
                            add(bucket)
                        }
                    }
                }.toList()

            val compressedSize = compressionPatternBuckets.sumOf {
                it.compressedSize
            }

            println("index(=$i), compressionSize(=$compressedSize): $compressionPatternBuckets")


            if (answer > compressedSize) {
                answer = compressedSize
            }
        }

        return answer
    }
}

data class PatternBucket(
    val pattern: String,
    var count: Int
) {
    val compressedSize: Int
        get() {
            return pattern.length + count.let { if (it == 1) 0 else it.toString().length }
        }

    infix fun isEqualPattern(other: PatternBucket) = other.pattern == this.pattern
}
