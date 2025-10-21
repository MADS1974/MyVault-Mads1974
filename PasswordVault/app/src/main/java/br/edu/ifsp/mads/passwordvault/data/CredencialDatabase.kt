package br.edu.ifsp.mads.passwordvault.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CredencialEntity::class], version = 2)
abstract class CredencialDatabase : RoomDatabase() {
    abstract fun credencialDAO(): CredencialDAO

    companion object {
        @Volatile
        private var INSTANCE: CredencialDatabase? = null

        fun getDatabase(context: Context): CredencialDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CredencialDatabase::class.java,
                    "passwordvault.db"
                )
                    .fallbackToDestructiveMigration() // Recria o banco se houver erro de versão
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
