package homework.week2

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe

//class GeneratePrimeSilencerTest : FunSpec({
//    context("소수 개수 테스트") {
//        withData(
//            TestDatum(intArrayOf(1, 2, 3, 4), 1),
//            TestDatum(intArrayOf(1, 2, 7, 6, 4), 4)
//        ) {
//            GeneratePrimeSilencer().solution(it.input) shouldBe it.result
//        }
//    }
//})
//
//data class TestDatum(
//    val input: IntArray,
//    val result: Int,
//)