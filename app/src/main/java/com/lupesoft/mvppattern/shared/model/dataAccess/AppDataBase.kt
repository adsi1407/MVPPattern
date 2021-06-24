package com.lupesoft.mvppattern.shared.model.dataAccess

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lupesoft.mvppattern.BuildConfig.DataBaseName
import com.lupesoft.mvppattern.movie.model.dataAccess.daos.MovieDao
import com.lupesoft.mvppattern.cart.model.dataAccess.daos.ShoppingCartDao
import com.lupesoft.mvppattern.movie.model.dataAccess.entities.MovieEntity
import com.lupesoft.mvppattern.cart.model.dataAccess.entities.ShoppingCarEntity

@Database(
        entities = [MovieEntity::class
            , ShoppingCarEntity::class],
        version = 1,
        exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun shoppingCartDao(): ShoppingCartDao

    companion object {

        @Volatile
        private var instance: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDataBase {
            return Room.databaseBuilder(context, AppDataBase::class.java, DataBaseName)
                    .fallbackToDestructiveMigration()
                    .build()
        }
    }
}