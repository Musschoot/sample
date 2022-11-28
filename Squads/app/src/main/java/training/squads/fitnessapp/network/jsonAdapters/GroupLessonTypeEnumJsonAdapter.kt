package training.squads.fitnessapp.network.jsonAdapters
import com.squareup.moshi.*
import training.squads.fitnessapp.domain.GroupLessonType

class GroupLessonTypeEnumJsonAdapter: JsonAdapter<GroupLessonType>() {

        @FromJson
        override fun fromJson(reader: JsonReader): GroupLessonType? {
            return if (reader.peek() != JsonReader.Token.NULL) {
                GroupLessonType.fromValueOrNull(reader.nextInt())
            } else {
                reader.nextNull()
            }
        }

        @ToJson
        override fun toJson(writer: JsonWriter, value: GroupLessonType?) {
            writer.value(value?.value)
        }
    }