package sahoo.projects.fin.dao

import androidx.room.*
import sahoo.projects.fin.model.CardDetail

@Dao
interface CardDetailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cardDetail: CardDetail)

    @Query("SELECT * FROM CardDetail WHERE id = :id")
    suspend fun getCardDetail(id: Int): CardDetail

    @Query("SELECT * FROM CardDetail")
    suspend fun getAllCardDetails(): List<CardDetail>

    @Delete
    suspend fun delete(cardDetail: CardDetail)
}