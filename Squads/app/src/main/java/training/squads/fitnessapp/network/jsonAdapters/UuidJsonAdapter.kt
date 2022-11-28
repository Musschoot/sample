package training.squads.fitnessapp.network.jsonAdapters

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.util.*

class UuidJsonAdapter {
        @ToJson
        fun toJson(value: UUID?) = value?.toString()
        @FromJson
        fun fromJson(input: String) = UUID.fromString(input)
    }