package homework.week1

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class CalculateDayOfTheWeekFor2016SilencerTest : StringSpec({
    "2016년 5월 24일 (화)" {
        val dayOfWeek = CalculateDayOfTheWeekFor2016Silencer().solution(5, 24)

        dayOfWeek shouldBe "TUE"
    }

    "2016년 1월 1일 (금)" {
        val dayOfWeek = CalculateDayOfTheWeekFor2016Silencer().solution(1, 1)

        dayOfWeek shouldBe "FRI"
    }

    "2016년 2월 16일 (화)" {
        val dayOfWeek = CalculateDayOfTheWeekFor2016Silencer().solution(2, 16)

        dayOfWeek shouldBe "TUE"
    }

    "2016년 2월 29일 (월)" {
        val dayOfWeek = CalculateDayOfTheWeekFor2016Silencer().solution(2, 29)

        dayOfWeek shouldBe "MON"
    }

    "2016년 12월 24일 (토)" {
        val dayOfWeek = CalculateDayOfTheWeekFor2016Silencer().solution(12, 24)

        dayOfWeek shouldBe "SAT"
    }

    "2016년 12월 31일 (토)" {
        val dayOfWeek = CalculateDayOfTheWeekFor2016Silencer().solution(12, 31)

        dayOfWeek shouldBe "SAT"
    }
})
