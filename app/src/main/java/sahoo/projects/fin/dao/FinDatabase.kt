package sahoo.projects.fin.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import sahoo.projects.fin.model.CardDetail

@Database(
    entities = [
        CardDetail::class
    ],
    version = 1 //increase on DDL change
)
abstract class FinDatabase : RoomDatabase() {


    abstract val cardDetailDao: CardDetailDao

    companion object {
        private const val DATABASE_NAME = "fin_db"

        @Volatile
        private var INSTANCE: FinDatabase? = null

        fun getInstance(context: Context): FinDatabase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    FinDatabase::class.java,
                    DATABASE_NAME
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}