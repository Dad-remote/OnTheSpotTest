package on.the.spot.test.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "boots")
data class BootEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val time: Long
)
