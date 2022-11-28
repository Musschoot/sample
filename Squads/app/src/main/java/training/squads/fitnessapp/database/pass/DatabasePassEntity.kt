package training.squads.fitnessapp.database.pass

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import training.squads.fitnessapp.database.BaseEntity
import training.squads.fitnessapp.domain.Pass
import training.squads.fitnessapp.network.jsonAdapters.localDateTimeJsonAdapter
import training.squads.fitnessapp.utilities.DateConverter
import training.squads.fitnessapp.utilities.uuidConverter
import java.time.LocalDateTime

// val dateConverter: DateConverter = DateConverter()
val dateConverter: localDateTimeJsonAdapter = localDateTimeJsonAdapter()
val idConverter: uuidConverter = uuidConverter()

@Entity(tableName = "passes")
data class DatabasePassEntity (
    @PrimaryKey
    @ColumnInfo(name = "pass_id")
    override val id: String,

    val userId: String,

    // override var productName: String,
    @ColumnInfo(name = "payment_status")
    var paymentStatus: Boolean,
    @ColumnInfo(name = "purchase_time")
    var purchaseTime: String,
    @ColumnInfo(name = "sessions")
    var sessions: Int
): BaseEntity()

fun List<DatabasePassEntity>.asPassListDomainModel(): List<Pass> {

    return map {
        Pass(
            id = idConverter.toUuid(it.id),
            paymentStatus = it.paymentStatus,
            purchaseTime = dateConverter.fromJson(it.purchaseTime),
            sessions = it.sessions
        )
    }
}