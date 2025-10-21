package br.edu.ifsp.mads.passwordvault.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import br.edu.ifsp.mads.passwordvault.R
import br.edu.ifsp.mads.passwordvault.adapter.CredencialAdapter
import br.edu.ifsp.mads.passwordvault.databinding.FragmentListaCredenciaisBinding
import br.edu.ifsp.mads.passwordvault.domain.Credencial
import br.edu.ifsp.mads.passwordvault.viewmodel.CredencialViewModel
import br.edu.ifsp.mads.passwordvault.viewmodel.ListaState
import kotlinx.coroutines.launch

class ListaCredenciaisFragment : Fragment() {

    private var _binding: FragmentListaCredenciaisBinding? = null
    private val binding get() = _binding!!

    private lateinit var credencialAdapter: CredencialAdapter

    // Este ViewModel e o Factory darão erro até os refatorarmos
    private val viewModel: CredencialViewModel by viewModels { CredencialViewModel.credencialViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListaCredenciaisBinding.inflate(inflater, container, false)

        // Esta ação de navegação dará erro até refatorarmos o nav_graph.xml
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_listaCredenciaisFragment_to_cadastroFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllCredentials()
        setupViewModel()
        setupMenu()
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main_menu, menu)

                val searchView = menu.findItem(R.id.action_search).actionView as SearchView
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        credencialAdapter.filter.filter(newText)
                        return true
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Futuramente, podemos adicionar outras opções de menu aqui
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setupViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            // A classe ListaState também será refatorada com o ViewModel
            viewModel.stateList.collect { state ->
                when (state) {
                    is ListaState.SearchAllSuccess -> {
                        setupRecyclerView(state.credenciais) // A propriedade será 'credenciais'
                    }
                    is ListaState.EmptyState -> {
                        binding.textEmptyList.visibility = View.VISIBLE
                        // Garante que o RecyclerView não seja mostrado se a lista estiver vazia
                        binding.recyclerview.visibility = View.GONE
                    }
                    is ListaState.ShowLoading -> {
                        // Lógica para mostrar um indicador de carregamento, se desejado
                    }
                }
            }
        }
    }

    private fun setupRecyclerView(credentialList: List<Credencial>) {
        // O RecyclerView só deve ser visível se houver dados
        binding.recyclerview.visibility = View.VISIBLE
        binding.textEmptyList.visibility = View.GONE

        credencialAdapter = CredencialAdapter().apply { updateList(credentialList) }
        binding.recyclerview.adapter = credencialAdapter

        credencialAdapter.onItemClick = { credencial ->
            val bundle = Bundle()
            bundle.putInt("idCredencial", credencial.id) // Chave do bundle atualizada
            findNavController().navigate(
                // Ação de navegação será atualizada no nav_graph.xml
                R.id.action_listaCredenciaisFragment_to_detalheFragment,
                bundle
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
