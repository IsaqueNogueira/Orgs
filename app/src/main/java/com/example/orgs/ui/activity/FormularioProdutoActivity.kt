package com.example.orgs.ui.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.orgs.R
import com.example.orgs.database.AppDatabase
import com.example.orgs.database.dao.ProdutoDao
import com.example.orgs.databinding.ActivityFormularioProdutoBinding
import com.example.orgs.extensions.tentaCarregarImagem
import com.example.orgs.model.Produto
import com.example.orgs.ui.dialog.FormularioImagemDialog
import java.math.BigDecimal

class FormularioProdutoActivity :
    AppCompatActivity(R.layout.activity_formulario_produto) {

    private val binding by lazy {
        ActivityFormularioProdutoBinding.inflate(layoutInflater)
    }
    private val produtoDao: ProdutoDao by lazy {
        val db = AppDatabase.instancia(this)
        db.produtoDao()
    }
    private var url: String? = null
    private var produtoId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = "Cadastrar produto"
        configuraBotaoSalvar()
        binding.activityFormularioProdutoImagem.setOnClickListener {
            FormularioImagemDialog(this)
                .mostra(url) { imagem ->
                    url = imagem
                    binding.activityFormularioProdutoImagem.tentaCarregarImagem(url)
                }
        }

        tentaCarregarProduto()

    }

    override fun onResume() {
        super.onResume()
        tentaBuscarProdutoNoBanco()
    }

    private fun tentaBuscarProdutoNoBanco() {
        produtoDao.buscaPorId(produtoId)?.let {
            title = "Alterar produto"
            preencheCampos(it)
        }
    }

    private fun tentaCarregarProduto() {
        produtoId = intent.getLongExtra(CHAVE_PRODUTO_ID, 0L)
    }

    private fun preencheCampos(produto: Produto) {

        url = produto.imagem
        binding.activityFormularioProdutoImagem
            .tentaCarregarImagem(produto.imagem)
        binding.activityFormularioEditNome
            .setText(produto.nome)
        binding.activityFormularioEditDescricao
            .setText(produto.descricao)
        binding.activityFormularioEditValor
            .setText(produto.valor.toPlainString())
    }

    private fun configuraBotaoSalvar() {
        val botaoSalvar = findViewById<Button>(R.id.activity_formulario_botao_salvar)
        botaoSalvar.setOnClickListener {
            val produtoNovo = criaProduto()
            produtoDao.salva(produtoNovo)
            finish()
        }
    }

    private fun criaProduto(): Produto {
        val campoNome = findViewById<EditText>(R.id.activity_formulario_edit_nome)
        val nome = campoNome.text.toString()
        val campoDescricao = findViewById<EditText>(R.id.activity_formulario_edit_descricao)
        val descricao = campoDescricao.text.toString()
        val campoValor = findViewById<EditText>(R.id.activity_formulario_edit_valor)
        val valorEmTexto = campoValor.text.toString()
        val valor = if (valorEmTexto.isBlank()) {
            BigDecimal.ZERO
        } else {
            BigDecimal(valorEmTexto)
        }

        return Produto(
            id = produtoId,
            nome = nome,
            descricao = descricao,
            valor = valor,
            imagem = url
        )
    }

}