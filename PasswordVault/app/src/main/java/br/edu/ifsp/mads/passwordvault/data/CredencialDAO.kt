package br.edu.ifsp.mads.passwordvault.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CredencialDAO {
    @Insert
    suspend fun insert(credencialEntity: CredencialEntity)

    @Update
    suspend fun update(credencialEntity: CredencialEntity)

    @Delete
    suspend fun delete(credencialEntity: CredencialEntity)

    @Query("SELECT * FROM credenciais ORDER BY servico")
    fun getAllCredentials(): Flow<List<CredencialEntity>>

    @Query("SELECT * FROM credenciais WHERE id = :id")
    fun getCredentialById(id: Int): Flow<CredencialEntity>
}
