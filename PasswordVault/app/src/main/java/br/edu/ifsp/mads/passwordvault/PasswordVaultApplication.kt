package br.edu.ifsp.mads.passwordvault

import android.app.Application
import br.edu.ifsp.mads.passwordvault.data.CredencialDatabase
import br.edu.ifsp.mads.passwordvault.repository.CredencialRepository

class PasswordVaultApplication: Application() {
    private val database by lazy { CredencialDatabase.getDatabase(this) }
    val repository by lazy { CredencialRepository(database.credencialDAO()) }
}

