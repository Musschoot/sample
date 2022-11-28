package training.squads.fitnessapp.utilities

import android.util.Log
import java.time.LocalDateTime
import java.util.*

class MaterialDateConverter {
    /**
     * Transforms a string into a date
     *
     * @param date A date string
     * @return A date object
     */
    fun toDate(date: String): LocalDateTime {
        var result: LocalDateTime = dateTimeDateConversion(date)
        return result
    }

    fun dateTimeDateConversion(date: String): LocalDateTime {
        var year: Int = date.split(" ")[2].toInt()
        var month = -1
        var monthString: String = date.split(" ")[0]
        when(monthString) {
            "Jan" -> month = 0
            "Feb" -> month = 1
            "Mar" -> month = 2
            "Apr" -> month= 3
            "May" -> month = 4
            "Jun" -> month = 5
            "Jul" -> month = 6
            "Aug" -> month = 7
            "Sep" -> month = 8
            "Oct" -> month = 9
            "Nov" -> month = 10
            "Dec" -> month = 11
        }
        var day: Int = date.split(" ")[1].split(",")[0].toInt()
        val date = LocalDateTime.of(year, month, day, 0, 0, 0)
        return date
    }

    fun dateLongToLocalDateTimeConversion(value: Long?): LocalDateTime {
        var gc = GregorianCalendar()
        gc.setTimeInMillis(value!!)

        var year = gc.get(GregorianCalendar.YEAR)
        Log.i("GREG CAL", year.toString())
        var month = gc.get(GregorianCalendar.MONTH) + 1
        Log.i("GREG CAL", month.toString())
        var day = gc.get(GregorianCalendar.DAY_OF_MONTH)

        //if(month == 12 && day = )
        Log.i("GREG CAL", day.toString())

        return LocalDateTime.of(year, month, day, 0, 0, 0)
    }
}