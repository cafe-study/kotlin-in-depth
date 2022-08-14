package homework.week1

class CalculateDayOfTheWeekFor2016Juwon {
    val modArr = arrayOf("FRI", "SAT", "SUN", "MON", "TUE", "WED", "THU")
    val daysArr = arrayOf(31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
    var days = 0

    fun solution(a: Int, b: Int): String {
        // multiple days for month
        for (i in 0 until (a-1)) {
            days += daysArr[i]
        }
        // add days
        days += (b-1)

        return  modArr[days.mod(7)]

    }
}