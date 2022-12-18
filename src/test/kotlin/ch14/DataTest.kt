package ch14

import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
import io.kotest.matchers.ints.shouldBeGreaterThanOrEqual

/**
 *
 *
 *  @author ysj
 *  @since 2022.12.18
 */


class DataDriveTest: StringSpec({
        "Minimum" {
            forAll(
                row(1, 1),
                row(1, 2),
                row(2, 1)
            ) { a: Int, b: Int ->
                (a min b).let { it <= a && it <= b }
            }
        }
    })

/**
 *  속성기반의 forAll과 차이
 *  데이터 기반 forAll에서는 unint를 반환하는 람다를 받는다
 * 그래서 람다내부에서 불린 리턴 대신 matcher를 써야 한다. (실패함)
 */
class DataDriveTest2 : StringSpec({
    "Minimum" {
        forAll(
            table(
                headers("name", "age"),
                row(1, 1),
                row(1, 2),
                row(2, 1)
            )

        ) { name, age ->
            age shouldBeGreaterThanOrEqual 18
        }
    }
})


class DataDriveTest3: StringSpec({
    "Minimum" {
        forAll(
            table(
                headers("name", "age"),
                row(1, 1),
                row(1, 2),
                row(2, 1)
              )
            ) { a: Int, b: Int ->
            (a min b).let { it <= a && it <= b }
        }
    }
})

