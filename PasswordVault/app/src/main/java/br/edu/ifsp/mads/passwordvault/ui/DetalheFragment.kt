package br.edu.ifsp.mads.passwordvault.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import br.edu.ifsp.mads.passwordvault.R
import br.edu.ifsp.mads.passwordvault.databinding.FragmentDetalheBinding
import br.edu.ifsp.mads.passwordvault.domain.Credencial
import br.edu.ifsp.mads.passwordvault.viewmodel.CredencialViewModel
import br.edu.ifsp.mads.passwordvault.viewmodel.DetalheState
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class DetalheFragment : Fragment() {
    private var _binding: FragmentDetalheBinding? = null
    private val binding get() = _binding!!

    // Propriedade para guardar a credencial atual
    private lateinit var credencial: Credencial

    // Referências aos EditTexts para os campos da credencial
    private lateinit var servicoEditText: EditText
    private lateinit var loginEditText: EditText
    private lateinit var senhaEditText: EditText
    private lateinit var siteEditText: EditText
    private lateinit var observacaoEditText: EditText

    // ViewModel refatorado
    private val viewModel: CredencialViewModel by viewModels { CredencialViewModel.credencialViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetalheBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Associa as variáveis aos EditTexts do layout (darão erro por enquanto)
        servicoEditText = binding.commonLayout.editTextServico
        loginEditText = binding.commonLayout.editTextLogin
        senhaEditText = binding.commonLayout.editTextSenha
        siteEditText = binding.commonLayout.editTextSite
        observacaoEditText = binding.commonLayout.editTextObservacao

        // Pega o ID da credencial passado pelo ListaCredenciaisFragment
        val idCredencial = requireArguments().getInt("idCredencial")
        viewModel.getCredentialById(idCredencial)

        setupStateObserver()
        setupMenu()
    }

    private fun setupStateObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.stateDetail.collect { state ->
                when (state) {
                    is DetalheState.DeleteSuccess -> {
                        Snackbar.make(binding.root, "Credencial removida com sucesso", Snackbar.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                    }
                    is DetalheState.UpdateSuccess -> {
                        Snackbar.make(binding.root, "Credencial alterada com sucesso", Snackbar.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                    }
                    is DetalheState.GetByIdSuccess -> {
                        // Preenche os campos quando a credencial é carregada
                        fillFields(state.credencial)
                    }
                    is DetalheState.ShowLoading -> {
                        // Lógica de loading
                    }
                }
            }
        }
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Infla o menu de detalhes (os IDs serão atualizados depois)
                menuInflater.inflate(R.menu.detalhe_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                TODO("Not yet implemented")
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun fillFields(cred: Credencial) {
        credencial = cred
        servicoEditText.setText(credencial.servico)
        loginEditText.setText(credencial.login)
        senhaEditText.setText(credencial.senha)
        siteEditText.setText(credencial.site)
        observacaoEditText.setText(credencial.observacao)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
