package br.edu.ifsp.mads.passwordvault.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import br.edu.ifsp.mads.passwordvault.R
import br.edu.ifsp.mads.passwordvault.databinding.FragmentCadastroBinding
import br.edu.ifsp.mads.passwordvault.domain.Credencial
import br.edu.ifsp.mads.passwordvault.viewmodel.CadastroState
import br.edu.ifsp.mads.passwordvault.viewmodel.CredencialViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class CadastroFragment : Fragment() {
    private var _binding: FragmentCadastroBinding? = null
    private val binding get() = _binding!!

    // ViewModel atualizado para CredencialViewModel
    private val viewModel: CredencialViewModel by viewModels { CredencialViewModel.credencialViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCadastroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupStateObserver()
        setupMenu()
    }

    private fun setupStateObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.stateCadastro.collect {
                when (it) {
                    is CadastroState.InsertSuccess -> {
                        // Mensagem de sucesso atualizada
                        Snackbar.make(binding.root, "Credencial salva com sucesso", Snackbar.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                    }
                    is CadastroState.ShowLoading -> {
                        // Poderia mostrar um progresso aqui
                    }
                }
            }
        }
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // O menu pode ser renomeado, mas por enquanto mantemos o mesmo ID
                menuInflater.inflate(R.menu.cadastro_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    // O ID do item de menu pode ser renomeado depois
                    R.id.action_salvarCredencial -> {
                        // Coleta os dados dos novos campos de texto
                        // Os IDs (editTextServico, etc.) darão erro até o layout ser atualizado
                        val servico = binding.commonLayout.editTextServico.text.toString()
                        val login = binding.commonLayout.editTextLogin.text.toString()
                        val senha = binding.commonLayout.editTextSenha.text.toString()
                        val site = binding.commonLayout.editTextSite.text.toString()
                        val observacao = binding.commonLayout.editTextObservacao.text.toString()


                        val credencial = Credencial(
                            servico = servico,
                            login = login,
                            senha = senha,
                            site = site,
                            observacao = observacao,
                        )
                        viewModel.insert(credencial)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
