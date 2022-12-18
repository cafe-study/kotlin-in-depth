package ch14.fixture

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.io.FileReader

/**
 *
 *
 *  @author ysj
 *  @since 2022.12.19
 */

class FileTest: FunSpec() {
    val reader = autoClose(FileReader("data.txt"))

    init {
        test("line count") {
            reader.readLines().isNotEmpty() shouldBe true
        }
    }
}