package ch14

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.listeners.*
import io.kotest.core.listeners.ProjectListener
import io.kotest.core.spec.AutoScan
import io.kotest.core.spec.Spec
import io.kotest.core.spec.style.FunSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.matchers.shouldBe
import kotlin.reflect.KClass

/**
 *
 *
 *  @author ysj
 *  @since 2022.12.19
 */

/***
 * 픽스처 제공
 *  테스트 실행 후 다시 자원과 환경 초기화(정리) 하는 환경 픽스처
 *
 *  접미사 spec과 test 의 차이
 *  test는 ㅁ테스트마다 실행 (테스트가 활성화되면 실행)
 *  spec 명세가 인스턴스화 되면 실행
 *
 *  전체 테스트에 대한 설정을 추가하려면  ProjectConfig 타입의 객체에 리스너를 등록해라
 */
object SpecLevelListener : TestListener {
    override val name:String = "SpecLevelListener"

    override suspend fun prepareSpec(kclass: KClass<out Spec>) {
        println("PrepareSpec(in SpecLevelListener): ${kclass.qualifiedName}")
    }

//    override suspend fun beforeSpec(spec: Spec) {
//        println("BeforeSpec: ${spec.materializeRootTests().joinToString { it.testCase.displayName }}")
//    }

    override suspend fun beforeTest(testCase: TestCase) {
        println("BeforeTest: ${testCase.displayName}")
    }

    override suspend fun afterTest(testCase: TestCase, result: TestResult) {
        println("AfterTest: ${testCase.displayName}")
    }

//    override suspend fun afterSpec(spec: Spec) {
//        println("AfterSpec: ${spec.materializeRootTests().joinToString { it.testCase.displayName }}")
//    }

    override suspend fun finalizeSpec(kclass: KClass<out Spec>, results: Map<TestCase, TestResult>) {
        println("FinalizeSpec(in SpecLevelListener): ${kclass.qualifiedName}")
    }
}

class NumbersTestWithFixture1 : FunSpec() {
    init {
        context("Addition") {
            test("2 + 2") {
                2 + 2 shouldBe  4
            }
            test("4 * 4") {
                4 + 4 shouldBe 8
            }
        }
    }

    override fun listeners() = listOf(SpecLevelListener)
}

class NumbersTestWithFixture2 : FunSpec() {
    init {
        context("Multiplication") {
            test("2 * 2") {
                2 * 2 shouldBe 4
            }
            test("4 * 4") {
                4 * 4 shouldBe 16
            }
        }
    }

    override fun listeners() = listOf(SpecLevelListener)
}

object MyProjectListener : ProjectListener, TestListener {
    override val name: String = "MyProjectListener"

    override suspend fun beforeProject() { println("Before project") }

    override suspend fun afterProject() { println("After project") }

    override suspend fun prepareSpec(kclass: KClass<out Spec>) {
        println("PrepareSpec: ${kclass.qualifiedName}")
    }

    override suspend fun finalizeSpec(kclass: KClass<out Spec>, results: Map<TestCase, TestResult>) {
        println("FinalizeSpec: ${kclass.qualifiedName}")
    }
}

object ProjectConfig: AbstractProjectConfig() {
    override fun listeners() = listOf(MyProjectListener)
}