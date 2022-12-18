package ch14

import io.kotest.assertions.assertSoftly
import io.kotest.assertions.timing.continually
import io.kotest.assertions.timing.eventually
import io.kotest.core.spec.style.*
import io.kotest.data.row
import io.kotest.inspectors.*
import io.kotest.matchers.*
import io.kotest.matchers.ints.beLessThanOrEqualTo
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.ints.shouldBeGreaterThanOrEqual
import io.kotest.matchers.ints.shouldBeLessThan
import io.kotest.property.*
import io.kotest.property.arbitrary.arbitrary
import io.kotest.property.arbitrary.bind
import io.kotest.property.arbitrary.int
import java.io.File
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.seconds

/**
 *
 *
 *  @author ysj
 *  @since 2022.12.18
 */

/**
 * StringSpec이 정의한 String.invoke() 확장함수를 호출
 * 검증은 shouldBe 중위 연산자 함수를 사용한다
 * 이 함수는 수신객체로 받은 값과 인자로 받은 값이 일치하지 않으면 예외를 던진다
 * 이 함수는 매처 DSL에 속해 있다.
 */
class NumberTest : StringSpec({
    "2 + 2 should be 4" { (2 + 2) shouldBe  4 }
    "2 * 2 should be 4" { (2 * 2) shouldBe 4 }
})


/**
 * wordSpec으로 계층 구조 형태로 테스트 코드를 작성 가능
 * should() 로 그룹핑
 */
class NumberTest2 : WordSpec({
    "1 + 2" should {
        "be equal to 3" { (1+2) shouldBe 3 }
        "be equal to 2 + 1" { (1 + 2) shouldBe ( 2+1 ) }
    }
})

/**
 * When으로 감싸면 3계층으로 구성 가능 (`when` 소문자로 시작하는 when은 예약어)
 * 책에서는 numberTest2
 */
class NumberTest3 : WordSpec({
   "Addition" When {
       "1 + 2" should {
           "be equal to 3" { ( 1+2 ) shouldBe 3 }
           "be equals to 2 + 1" { (1 + 2) shouldBe (2 + 1) }
       }
   }
})


/**
 * 테스트 계층을 3계층 이상으로 더 많이 만들 때
 * Funspec을 상속받은 후 context 블록 사용
 *   test
 *     context
 *        test
 *          context
 * test블록 안에 test 블록은 사용 불가
 */
class NumberTest4 : FunSpec({
    test("0 should be equals to 0") { 0 shouldBe 0 }
    context("Arithmetic") {
        context("Addtion") {
            test("2 + 2 should be 4") { (2 + 2) shouldBe 4 }
        }
        context("Multiplication") {
            test("2*2 should be 4") { ( 2 * 2 ) shouldBe 4 }
        }
    }
})


/**
 *  DescribeSpec 사용
 *  describe와 context로 블록 만든 후 it으로 테스트 검증
**/
class NumberTest5: DescribeSpec({
    describe("Addition") {
        context("1+2") {
            it("should give 3") { (1+2) shouldBe 3}
        }
    }
})

/**
 * FunSpec과 유사
 */
class shouldSpecTest: ShouldSpec({
    should ("be equal to 0") { 0 shouldBe 0 }
    context("Addition") {
        context("1+2") {
            should(" be equal to 3") { ( 1+2 ) shouldBe 3 }
            should(" be equal to 2 + 1") { ( 1+2 ) shouldBe (2 + 1) }
        }
    }
})

/**
 * FreeSpec invoke()로 테스트를 정의하고
 *  문맥은 (-) minus 사용
**/
class NumberTest6 : FreeSpec({
    "0 should be equal to 0" { 0 shouldBe  0 }
    "Addition " - {
        "1 + 2" - {
            "1 + 2 should be equals to 3" { ( 1+2 ) shouldBe 3 }
            "1 _ 2 should be equals to 2 + 1" { (1+2) shouldBe (2+1)}
        }
    }
})

/**
 * BDD 스타일 featureSpec 사용
**/
class NumberTest7 : FeatureSpec ({
    feature("Arithmetric") {
        val x = 1
        scenario(" x is 1 at first") {x shouldBe  1}
        feature("increase by") {
            scenario("1 give 2" ) {(x + 1) shouldBe 2}
            scenario("2 give 3" ) {(x + 2) shouldBe 3}
        }
    }
})

/**
 * BDD 스타일 BehaviorSpec
 * given, when, then
**/
class NumberTest8 : BehaviorSpec ({
    Given("Arithemtic") {
        When ("x is 1") {
            val x = 1
            And ("increase by 1") {
                Then ("result is 2") {( x + 1) shouldBe 2}
            }
        }
    }
})

/**
 *  Anotation 스타일
 *  Ignore 사용 가능
**/
class NumberTest9: AnnotationSpec() {
    @Test fun `2+2 should be 4`() {(2 + 2) shouldBe 4}
    @Test fun `2*2 should be 4`() {(2 * 2) shouldBe 4}
    @Ignore @Test fun `3+1 should be 4`() {(2 * 2) shouldBe 4}
}


/***
    단언문

 Matcher 사용

  커스텀 매처를 사용하려면 Matcher 인터페이스를 구현하고  test 메소드를 오버라이드 해야 한다.
  abstract fun test(value: T): MatcherResult

 pass : true/ false 여부
 failureMessage: 실패 시 원인
 negatedFailureMessage:
   책 => 매처를 반전시킨 버전을 사용했는데 매처가 실패하는 경우 메시지
   의역: 의도한 결과와 반대일 때 노출 메시지
 **/

/**
 *   MatcherResult 예시
**/

fun beOdd() = object : Matcher<Int> {
    override fun test(value: Int): MatcherResult {
        //return MatcherResult.Companion.invoke()
        return MatcherResult(
            value % 2 != 0,
            "$value shoulde be odd",
            "value should not be odd"
        )
    }
}

/**
 * 만들어진 매처를 StringSpec으로 테스트
 */
class NUmberTestWithOddMatcher: StringSpec({
    "5 is odd" { 5 shouldBe beOdd() }
    "4 is not odd" { 4 shouldNot  beOdd() }
})

/**
 * 인스펙터
 *  컬렉션 함수에 대한 확장함수이다..
 *  컬렉션 원소에 대해 단언문으로 검증 가능
 *
 *  forAll() / forNone 모두 만족 또는 모두 false
 *  forExactly(n) 단언문을 n개의 원소가 만족하는 지 확인
 *  forAtLeast(n) / forAtMost() 최소 n개가 만족 하는지 / 최대 n개가 만족 하는 지
 *  forSome() 모두가 만족하지는 않지만 만족하는 요소가 존재재
 *  import shouldeBeGreaterThanOrEqual
**/

class NumberTestWithInspectors : StringSpec({
    val numbers = Array(10) {it + 1}
    "all are non-negative" {
        numbers.forAll { it shouldBeGreaterThanOrEqual 0}
    }

    "none is zero" { numbers.forNone { it shouldBe 0 }}
    "a single 10" { numbers.forOne { it shouldBe 10 }}
    "a most one 0" {numbers.forAtMostOne { it shouldBe  0 }}
    "at least one odd number" {
        numbers.forAtLeastOne { it % 2 shouldBe  1 }
    }
    "at most five odd numbers" {
        numbers.forAtMost(5) { it % 2 shouldBe  1}
    }
    "at least threee even numbers" {
        numbers.forAtLeast(3) { it % 2 shouldBe  0 }
    }
    "some numbers are odd" {numbers.forAny { it % 2 shouldBe  1 }}

    "some but not all numbers are even" {
        numbers.forSome { it % 2 shouldBe  0 }
    }

    "exctly five numbers are even" {
        numbers.forExactly(5) {
            it % 2 shouldBe  0
        }
    }
})

/***
 * 단언문 예외처리
 *  일반적으로 단언문 실패가 발생하는 시점에서 테스트가 종료되기 때문에 모든 테스트 결과를 볼 수 없다.
 *  assertSoftly 블록을 사용해서 결과를 확인할 수 있다. (단언문 예외처리)
 *
 *  예외처리 안됨
**/

class NumberTestWithForAll : StringSpec({
    val numbers = Array(10) { it + 1 }
    "invalid numbers" {
        assertSoftly {
            numbers.forAll { it shouldBeLessThan 5 }
            numbers.forAll { it shouldBeGreaterThan 3 }
        }
    }
})

///**
// * 비결정적 코드 테스트 ( 여러 번 시도가 필요한 테스트)
// * ex) 시간 체크
// *  kotlin/time/DurationUnit not found
//**/
//@ExperimentalTime
//class StringSpecWithEventually : StringSpec({
//    "10 초 안엔 파일의 내용이 단 한 줄인 경우가 있어야 함" {
//        eventually(Duration.seconds(10)) {
//            File("data.txt").readLines().size shouldBe 1
//        }
//    }
//})
//
//
//@ExperimentalTime
//class StringSpecWithEventually2 : StringSpec({
//    "10 초동안 파일의 내용이 단 한 줄로 유지함" {
//        continually(Duration.seconds(10)) {
//            File("data.txt").readLines().size shouldBe 1
//        }
//    }
//})

/***
 * 속성 기반 테스트
 * 임의의 테스트 데이터를 생성해주는 테스트
 *  kotest-property 추가
 *
 *  기본적으로 1000개의 테스트 집합 원소 생성(?)
**/

infix fun Int.min(n:Int) = if (this < n) this else n

class NumberTestWithAssertAll : StringSpec({
    "min" {
        checkAll {a: Int, b : Int ->
            (a min b).let {
                it should (beLessThanOrEqualTo(a) and beLessThanOrEqualTo(b))
            }}
    }
})

/***
 *  forAll 로 모든 테스트 true 람다로
 */
class NumberTestWithAssertAll2 : StringSpec({
    "min (단언문 checkALl 대신 람다식)" {
        checkAll { a: Int, b: Int ->
            (a min b).let { it <= a && it <= b }
        }
    }
})

/***
 * 타입 생성기
 *  Arb : 미리 하드코딩된 에지케이스와 무한한 난수 샘플을 생성해준다 2%는 에지, 98%는 난수
 *  Exhaustive: 주어진 공간에 속한 모든 데이터 생성
 *
 *  유리수 테스트 예제
 *
 *  Rational not found
**/

//class RationTest: StringSpec({
//    "substraction (Arb)" {
//        forAll(
//            //첫번째 인자를 arb로
//            object : Arb<Rational>() {
//                override fun edgecase(rs: RandomSource): Rational? = null
//                override fun sample(rs: RandomSource): Sample<Rational> =
//                    Sample(Rational.of(rs.random.nextInt(), rs.random.nextInt(0, Int.MAX_VALUE)))
//            }
//        ) { a: Rational -> (a - a).num == 0 }
//    }
// }
//})

/***
 *
 *   Arb.int(range), ARb.long(range), Arb.nats(range) 범위에 수를 생성
 *   Exhaustive.ints(range) Exhaustive.longs(range) 범위에 속한 모든 수 생성
 *   Arb.string(range), Arb.StringPAttern(pattern) 문자열이나 패턴
 *   arb.orNull(), arb.orNull(nullProbability) : Arb가 만들어낼 값인 arb에 널값 데이터 생성
 *
 */

//class RationTest2 : StringSpec({
//    val rationArb = Arb.bind(Arb.int(), Arb.int(0, Int.MAX_VALUE)) { x, y -> Rational.of(x, y) }
//    "substraction (Arb.int(), arb.bind() 사용)" {
//        forAll(rationArb) { a: Rational -> (a - a).num == 0 }
//    }
//  }
//})

/***
 * arb 구현 시에 arbitary를 쓰면 좀 더 편하게 작성 가능
 */
//class RationTest3: StringSpec({
//    val rationArb = arbitrary { Rational.of(it.random.nextInt(), it.random.nextInt(0, Int.MAX_VALUE)) }
//    "subtraction (arbitary use)" {
//        forAll(rationArb) { a: Rational -> (a - a).num == 0 }
//    }
// }
//})

