package br.ufpe.cin.android.podcast.DAO

import androidx.room.*
import br.ufpe.cin.android.podcast.ItemFeed

@Dao
interface ItemFeedDAO {
    @Query("SELECT * FROM itemfeed")
    fun getAllItems(): List<ItemFeed>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(vararg items: ItemFeed)

    @Query("DELETE FROM itemfeed")
    fun deleteAllItems()
}