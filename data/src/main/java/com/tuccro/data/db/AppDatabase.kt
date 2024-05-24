package com.tuccro.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.tuccro.data.db.dao.WalletDao
import com.tuccro.data.db.entity.BalanceEntity

@Database(entities = [BalanceEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun walletDao(): WalletDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        private const val DB_NAME = "wallet_database"
        private const val INITIAL_SQL =
            "INSERT INTO balance_table (currency, amount) VALUES ('EUR', 1000.0)"

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DB_NAME
                )
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            db.execSQL(INITIAL_SQL)
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
