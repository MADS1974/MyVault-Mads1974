package br.edu.ifsp.mads.passwordvault.repository

import br.edu.ifsp.mads.passwordvault.data.CredencialDAO
import br.edu.ifsp.mads.passwordvault.domain.Credencial
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

class CredencialRepository(private val credencialDAO: CredencialDAO) {

    suspend fun insert(credencial: Credencial) {
        credencialDAO.insert(credencial.toEntity())
    }

    suspend fun update(credencial: Credencial) {
        credencialDAO.update(credencial.toEntity())
    }

    suspend fun delete(credencial: Credencial) {
        credencialDAO.delete(credencial.toEntity())
    }

    fun getAllCredentials(): Flow<List<Credencial>> {
        return credencialDAO.getAllCredentials().map { list ->
            list.map {
                it.toDomain()
            }
        }
    }

    fun getCredentialById(id: Int): Flow<Credencial> {
        return credencialDAO.getCredentialById(id).filterNotNull().map { it.toDomain() }
    }
}
