package br.edu.ifsp.mads.passwordvault.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.mads.passwordvault.databinding.CredencialCelulaBinding
import br.edu.ifsp.mads.passwordvault.domain.Credencial

class CredencialAdapter : RecyclerView.Adapter<CredencialAdapter.CredencialViewHolder>(),
    Filterable {

    var onItemClick: ((Credencial) -> Unit)? = null

    var credenciaisLista = ArrayList<Credencial>()
    var credenciaisListaFilterable = ArrayList<Credencial>()

    private lateinit var binding: CredencialCelulaBinding

    fun updateList(newList: List<Credencial>) {
        credenciaisLista = ArrayList(newList)
        credenciaisListaFilterable = credenciaisLista
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CredencialViewHolder {
        binding = CredencialCelulaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CredencialViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CredencialViewHolder, position: Int) {
        // Atualiza os campos para servico e login
        holder.servicoVH.text = credenciaisListaFilterable[position].servico
        holder.loginVH.text = credenciaisListaFilterable[position].login
    }

    override fun getItemCount(): Int {
        return credenciaisListaFilterable.size
    }

    inner class CredencialViewHolder(view: CredencialCelulaBinding) : RecyclerView.ViewHolder(view.root) {
        // As Views (IDs) serão definidas no arquivo credencial_celula.xml
        val servicoVH = view.servico
        val loginVH = view.login

        init {
            view.root.setOnClickListener {
                onItemClick?.invoke(credenciaisListaFilterable[adapterPosition])
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                if (constraint.isNullOrEmpty()) {
                    credenciaisListaFilterable = credenciaisLista
                } else {
                    val resultList = ArrayList<Credencial>()
                    val filterPattern = constraint.toString().lowercase().trim()
                    for (row in credenciaisLista) {
                        // Filtra pelo nome do serviço
                        if (row.servico.lowercase().contains(filterPattern)) {
                            resultList.add(row)
                        }
                    }
                    credenciaisListaFilterable = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = credenciaisListaFilterable
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                credenciaisListaFilterable = results?.values as? ArrayList<Credencial> ?: ArrayList()
                notifyDataSetChanged()
            }
        }
    }
}
