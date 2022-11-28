package training.squads.fitnessapp.utilities

import android.util.Log
import androidx.room.TypeConverter
import java.time.LocalDateTime

/**
 * Converts dates to strings and vice versa
 */
class DateConverter {
    @TypeConverter
    fun fromDate(date: LocalDateTime): String {
        var result: String = dateTimeStringConversion(date)
        return result
    }

    fun toDate(date: String): LocalDateTime {
        var result: LocalDateTime = dateTimeDateConversion(date)
        return result
    }

    fun dateTimeStringConversion(date: LocalDateTime): String {
        val builder = StringBuilder()

        var year: Int = date.year
        var month: Int = date.monthValue
        month
        var day: Int = date.dayOfMonth
        var hour: Int = date.hour
        var minute: Int = date.minute
        var second: Int = date.second
        var timeOffset = builder.append("T")
            .append(if (hour >= 0 && hour < 10) "0" + hour.toString() else hour.toString())
            .append(":")
            .append(if (minute >= 0 && minute < 10) "0" + minute.toString() else minute.toString())
            .append(":")
            .append(if (second >= 0 && second < 10) "0" + second.toString() else second.toString())

        return year.toString() +
                "-" +
                (if (month > 0 && month < 10) "0" + month.toString() else month.toString()) +
                "-" +
                (if (day > 0 && day < 10) "0" + day.toString() else day.toString()) +
                timeOffset
    }

    fun dateTimeDateConversion(date: String): LocalDateTime {
        var year: Int = date.split("-")[0].toInt()
        var month: Int = date.split("-")[1].toInt()
        var day: Int = date.split("-")[2].split("T")[0].toInt()
        var hour: Int = date.split("-")[2].split("T")[1].split(":")[0].toInt()
        var minute: Int = date.split("-")[2].split("T")[1].split(":")[1].toInt()
        var second: Int = date.split("-")[2].split("T")[1].split(":")[2].take(2).toInt()

        var time = LocalDateTime.of(year, month, day, hour, minute, second)

        return time
    }
}