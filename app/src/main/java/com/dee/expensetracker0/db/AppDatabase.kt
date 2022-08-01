package com.dee.expensetracker0.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dee.expensetracker0.model.Transaction

@Database(
    entities = [Transaction::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase(){

    abstract fun getTransactionDao() : TransactionDao

    companion object{
        @Volatile
        private var instance :AppDatabase? = null
        private val LOCK =Any()

        //check for an instance if not null then get or insert or else create new instance

        operator fun invoke(context: Context) = instance?: synchronized(LOCK){
            instance?: createDatabase(context).also { instance = it }
        }
        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "transaction.db"
        ).build()
    }
}
