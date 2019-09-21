package br.ufpe.cin.android.podcast.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.ufpe.cin.android.podcast.DAO.ItemFeedDAO
import br.ufpe.cin.android.podcast.ItemFeed

@Database(version = 1, entities = arrayOf(ItemFeed::class))
abstract class AppDataBase : RoomDatabase() {
    abstract fun itemDao(): ItemFeedDAO
}