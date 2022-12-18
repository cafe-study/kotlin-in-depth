package homework.week3

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe


//class StringCompressionTest : FunSpec({
//    context("압축 문자열 길이 테스트") {
//        withData(
//            TestDatum("aabbaccc", 7),
//            TestDatum("ababcdcdababcdcd", 9),
//            TestDatum("abcabcdede", 8),
//            TestDatum("abcabcabcabcdededededede", 14),
//            TestDatum("xababcdcdababcdcd", 17),
//            TestDatum("x", 1),
//        ) {
//            StringCompression().solution(it.input) shouldBe it.result
//        }
//    }
//})
//
//data class TestDatum(
//    val input: String,
//    val result: Int,
//)