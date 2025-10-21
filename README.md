
# 🔐 PasswordVault App

Um aplicativo Android para gerenciamento de **credenciais e senhas** (Cofre de Senhas) desenvolvido em **Kotlin**.
O objetivo principal é demonstrar a implementação de uma **arquitetura moderna** com Room, ViewModel e Navegação por Fragmentos, além de operações **CRUD** (Criar, Ler, Atualizar, Excluir) em um banco de dados local seguro.

Este projeto foi criado para fins acadêmicos no contexto da disciplina de **PERSISTÊNCIA DE DADOS – D1PDD**, com foco em:

* Arquitetura **MVVM** (Model-View-ViewModel) para separação de responsabilidades
* Uso de **Room** para persistência de dados local
* Navegação entre **Fragmentos** com Navigation Component
* Gerenciamento de estado com **ViewModel** e **StateFlow**
* Interação do usuário com listas usando **RecyclerView** e Adapter

---

## 🚀 Funcionalidades

✔️ **Listagem de Credenciais**: Exibe uma lista de credenciais salvas
➕ **Adicionar Nova Credencial**: Abre um fragmento para preenchimento dos dados (serviço, login, senha, site, observações)
✏️ **Visualizar/Editar Credencial**: Exibe os detalhes com opção de edição
🗑️ **Remover Credencial**: Permite excluir a credencial do banco de dados
🔍 **Busca em Tempo Real**: Filtra conforme o usuário digita
💾 **Persistência de Dados**: Tudo salvo localmente via Room

---

## 🛠 Tecnologias Utilizadas

* **Kotlin** — Linguagem principal
* **Android Studio Narwhal** — IDE
* **ViewBinding** — Acesso seguro à UI
* **Room Persistence Library** — Banco local
* **ViewModel** — Gerenciamento de estado
* **Coroutines & StateFlow** — Reatividade moderna
* **Navigation Component** — Navegação entre Fragmentos
* **RecyclerView** — Exibição de listas
* **SearchView** — Busca integrada
* **Material Design** — UI moderna

---

## 📸 Demonstração do App

01 - **Tela Principal (Lista de Credenciais)**
02 - **Adicionar/Editar Credencial**
03 - **Detalhes da Credencial**

---

## ▶️ Como Executar o Projeto

```bash
# 1. Clone o repositório
git clone https://github.com/MADS1974/MyVault-Mads1974
# 2. Abra o projeto no Android Studio
# 3. Aguarde a sincronização do Gradle
# 4. Execute no emulador ou dispositivo físico
```

---

## 📚 Créditos

Projeto acadêmico desenvolvido para a disciplina **PERSISTÊNCIA DE DADOS – D1PDD**, ministrada pelo professor **Pablo Dalbem (IFSP)**.
