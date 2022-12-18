package ch14

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.core.spec.style.StringSpec
import io.kotest.core.test.config.TestCaseConfig
import io.kotest.matchers.shouldBe
import kotlin.time.ExperimentalTime
import kotlin.time.minutes

/**
 *
 *  invocations 테스트 실행 횟수 - 모든 실행이 성공헤애 힘
 *  thread 테스트를 실행할 스레드 개수 invocations가 2 이상
 *  enabled 테스트를 실행해야 할지 여부
 *  timeout : 실행에 걸리는 최대 시간
 *
 *
 *  @author ysj
 *  @since 2022.12.19
 */
class StringSpecWithConfig : StringSpec({
    "2 + 2 should be 4".config(invocations = 10) { (2 + 2) shouldBe 4 }
})
@OptIn(ExperimentalTime::class)
class ShouldSpecWithConfig : ShouldSpec({
    context("Addition") {
        context("1 + 2") {
            should("be equal to 3").config(threads = 2, invocations = 100) {
                (1 + 2) shouldBe  3
            }
            should("be equal to 2 + 1").config(timeout = 1.minutes) {
                (1 + 2) shouldBe (2 + 1)
            }
        }
    }
})

class BehaviorSpecWithConfig : BehaviorSpec({
    Given("Arithmetic") {
        When("x is 1") {
            val x = 1
            And("increased by 1") {
                then("result is 2").config(invocations = 100) {
                    (x + 1) shouldBe 2
                }
            }
        }
    }
})


//object ProjectConfig : AbstractProjectConfig() {
//    override val parallelism = 4
//}
//
//class StringSpecWithConfig2 : StringSpec({
//    "2 + 2 should be 4" { (2 + 2) shouldBe 4 }
//}) {
//    override fun defaultConfig(): TestCaseConfig =
//        TestCaseConfig(invocations = 10, threads = 2)
//}