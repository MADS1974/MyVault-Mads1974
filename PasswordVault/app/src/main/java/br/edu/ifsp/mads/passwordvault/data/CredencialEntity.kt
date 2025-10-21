package br.edu.ifsp.mads.passwordvault.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.edu.ifsp.mads.passwordvault.domain.Credencial

@Entity(tableName = "credenciais")
data class CredencialEntity (
 @PrimaryKey(autoGenerate = true)
 val id: Int = 0,
 val servico: String,
 val login: String,
 val senha: String,
 val site: String,
 var observacao: String
)
{
 fun toDomain(): Credencial {
  return Credencial(id, servico, login, senha, site, observacao)
 }
}
