package br.edu.ifsp.mads.passwordvault.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import br.edu.ifsp.mads.passwordvault.PasswordVaultApplication
import br.edu.ifsp.mads.passwordvault.domain.Credencial
import br.edu.ifsp.mads.passwordvault.repository.CredencialRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Classes de Estado Refatoradas
sealed class CadastroState {
    data object InsertSuccess : CadastroState()
    data object ShowLoading : CadastroState()
}

sealed class DetalheState {
    data object UpdateSuccess : DetalheState()
    data object DeleteSuccess : DetalheState()
    data class GetByIdSuccess(val credencial: Credencial) : DetalheState()
    data object ShowLoading : DetalheState()
}

sealed class ListaState {
    data class SearchAllSuccess(val credenciais: List<Credencial>) : ListaState()
    data object ShowLoading : ListaState()
    data object EmptyState : ListaState()
}

// ViewModel Refatorado
class CredencialViewModel(private val repository: CredencialRepository) : ViewModel() {

    private val _stateCadastro = MutableStateFlow<CadastroState>(CadastroState.ShowLoading)
    val stateCadastro = _stateCadastro.asStateFlow()

    private val _stateDetail = MutableStateFlow<DetalheState>(DetalheState.ShowLoading)
    val stateDetail = _stateDetail.asStateFlow()

    private val _stateList = MutableStateFlow<ListaState>(ListaState.ShowLoading)
    val stateList = _stateList.asStateFlow()

    fun insert(credencial: Credencial) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(credencial)
        _stateCadastro.value = CadastroState.InsertSuccess
    }

    fun update(credencial: Credencial) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(credencial)
        _stateDetail.value = DetalheState.UpdateSuccess
    }

    fun delete(credencial: Credencial) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(credencial)
        _stateDetail.value = DetalheState.DeleteSuccess
    }

    fun getAllCredentials() {
        viewModelScope.launch {
            repository.getAllCredentials().collect { result ->
                if (result.isEmpty()) {
                    _stateList.value = ListaState.EmptyState
                } else {
                    _stateList.value = ListaState.SearchAllSuccess(result)
                }
            }
        }
    }

    fun getCredentialById(id: Int) {
        viewModelScope.launch {
            repository.getCredentialById(id).collect { result ->
                _stateDetail.value = DetalheState.GetByIdSuccess(result)
            }
        }
    }

    // ViewModel Factory Refatorado
    companion object {
        fun credencialViewModelFactory(): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras
                ): T {
                    val application = checkNotNull(
                        extras[APPLICATION_KEY]
                    )
                    return CredencialViewModel(
                        (application as PasswordVaultApplication).repository
                    ) as T
                }
            }
    }
}
