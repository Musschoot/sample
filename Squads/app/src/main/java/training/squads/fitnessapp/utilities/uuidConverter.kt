package training.squads.fitnessapp.utilities

import androidx.room.TypeConverter
import java.util.UUID

/**
 * Converts uuid's to strings and vice versa
 */
class uuidConverter {
    @TypeConverter
    fun fromUuid(uuid: UUID): String {
        var result: String = uuid.toString()
        return result
    }

    fun toUuid(uuid: String): UUID {
        var result: UUID = UUID.fromString(uuid)
        return result
    }
}