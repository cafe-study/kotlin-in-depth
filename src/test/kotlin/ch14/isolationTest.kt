import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

/***
 * SingleInstance 인스턴스를 하나만 디폴트 옵션
 * InstancePerTest 테스트 실행 시 마다 생성
 * InstnacePerLeaf (말단 ) 개별 테스트 블록 실행 전에 인스턴스화
 * **/

class IncTest : FunSpec() {
    var x = 0
    init {
        context("Increment") {
            println("Increment : ${this@IncTest} ${this}")
            test("prefix") {
                println("prefix: ${this@IncTest} ${this@context} ${this}")
                ++x shouldBe 1
            }
            test("postfix") {
                println("postfix: ${this@IncTest} ${this@context} ${this}")
                x++ shouldBe 0
            }
        }
    }
}

object IncTestProjectConfig : AbstractProjectConfig() {
    /***
     * SingleInstance 인스턴스 하나인데 중복이므로 실패
     * Increment가 세번 표시된다 테스트당 생성
     * perLeaf 는 두번 prefix, postfix를 위해
     */
    override val isolationMode = IsolationMode.InstancePerTest
    //override val isolationMode = IsolationMode.InstancePerLeaf
    //override val isolationMode = IsolationMode.SingleInstance
}