package on.the.spot.test.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import on.the.spot.test.db.dao.BootsDao
import on.the.spot.test.db.model.BootEntity

@Database(
    entities = [
        BootEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class TestDatabase : RoomDatabase() {

    companion object {
        fun build(applicationContext: Context) = Room.databaseBuilder(
            applicationContext,
            TestDatabase::class.java, "test-database"
        ).fallbackToDestructiveMigration()
            .build()
    }

    abstract fun bootsDao(): BootsDao
}
