package lukakrivacevic.weatherapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "cities")
data class City (
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val name: String,
    val longitude: String,   //Double
    val latitude: String   //Double
): Serializable