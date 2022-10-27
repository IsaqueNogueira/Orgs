package com.example.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.orgs.R
import com.example.orgs.database.AppDatabase
import com.example.orgs.databinding.ActivityDetalheProdutoBinding
import com.example.orgs.extensions.formataParaMoedaBrasileira
import com.example.orgs.extensions.tentaCarregarImagem
import com.example.orgs.model.Produto


class DetalheProdutoActivity : AppCompatActivity() {

    private var produtoId: Long = 0L
    private var produto: Produto? = null
    private val binding by lazy {
        ActivityDetalheProdutoBinding.inflate(layoutInflater)
    }

    private val produtoDao by lazy {
        AppDatabase.instancia(this).produtoDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tentaCarregarProduto()
    }

    override fun onResume() {
        super.onResume()
        buscaProdutoNoBanco()
    }

    private fun buscaProdutoNoBanco() {
        produto = produtoDao.buscaPorId(produtoId)
        produto?.let {
            preencheCampos(it)
        } ?: finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalhes_produto, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_detalhes_produto_remover -> {
                produto?.let {
                    produtoDao.remove(it)
                }

                finish()
            }
            R.id.menu_detalhes_produto_editar -> {
                Intent(this, FormularioProdutoActivity::class.java)
                    .apply {
                        putExtra(CHAVE_PRODUTO_ID, produtoId)
                        startActivity(this)
                    }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun tentaCarregarProduto() {
        produtoId = intent.getLongExtra(CHAVE_PRODUTO_ID, 0)
    }

    private fun preencheCampos(produtoCarregado: Produto) {
        with(binding) {
            activityDetalheProdutoImageview.tentaCarregarImagem(produtoCarregado.imagem)
            activityDetalheProdutoNome.text = produtoCarregado.nome
            activityDetalheProdutoDescricao.text = produtoCarregado.descricao
            activityDetalheProdutoValor.text =
                produtoCarregado.valor.formataParaMoedaBrasileira()
        }
    }
}