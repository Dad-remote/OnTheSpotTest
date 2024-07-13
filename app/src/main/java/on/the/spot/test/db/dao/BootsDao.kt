package on.the.spot.test.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import on.the.spot.test.db.model.BootEntity

@Dao
abstract class BootsDao {

    @Query("SELECT * FROM boots")
    abstract fun loadAll(): Flow<List<BootEntity>>

    @Query("SELECT * FROM boots")
    abstract suspend fun getAll(): List<BootEntity>

    @Insert
    abstract suspend fun save(item: BootEntity)

    @Query("SELECT * FROM boots ORDER BY time DESC LIMIT 1")
    abstract fun loadLast(): Flow<BootEntity?>

}
