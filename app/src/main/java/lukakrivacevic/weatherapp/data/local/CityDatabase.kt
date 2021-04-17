package lukakrivacevic.weatherapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import lukakrivacevic.weatherapp.data.local.entities.City

@Database(
    entities = [City::class],
    version = 1
)
abstract class CityDatabase : RoomDatabase() {

    abstract fun getCityDao(): CityDao

    companion object {
        @Volatile
        private var instance: CityDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                CityDatabase::class.java,
                "city_db"
            ).build()
    }
}