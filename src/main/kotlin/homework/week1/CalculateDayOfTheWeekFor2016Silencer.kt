package homework.week1

class CalculateDayOfTheWeekFor2016Silencer {
    val dayCountOfMonth = listOf(31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
    val nameOfDayOfWeek = listOf("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT")

    fun solution(a: Int, b: Int): String {
        val dayOfYear = dayCountOfMonth.subList(0, a - 1).sum() + b
        val dayOfWeek = (dayOfYear + 4) % 7 // 2016년 1월 1일 = 금(index=5) 이므로 4를 더함

        return nameOfDayOfWeek[dayOfWeek]
    }
}