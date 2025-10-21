package br.edu.ifsp.mads.passwordvault.domain

import br.edu.ifsp.mads.passwordvault.data.CredencialEntity

data class Credencial (
    var id: Int = 0,
    var servico: String,
    var login: String,
    var senha: String,
    val site: String,
    var observacao: String
){


    fun toEntity(): CredencialEntity {
        return CredencialEntity(id, servico, login, senha, site, observacao)
    }
}
