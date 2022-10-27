package com.example.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.orgs.R
import com.example.orgs.database.AppDatabase
import com.example.orgs.databinding.ActivityListaProdutosBinding
import com.example.orgs.ui.recyclerview.adapter.ListaProdutosAdpter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListaProdutosActivity : AppCompatActivity() {
    private val adapter = ListaProdutosAdpter(this)

    private val binding by lazy {
        ActivityListaProdutosBinding.inflate(layoutInflater)
    }
    private val produtoDao by lazy {
        val db = AppDatabase.instancia(this)
        db.produtoDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraRecyclerview()
        configuraFab()

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_lista_produtos, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.lista_nomedesc ->{
              produtoDao.nomeDesc()
                adapter.atualiza(produtoDao.nomeDesc())
            }
            R.id.lista_nomeasc ->{
                produtoDao.nomeAsc()
                adapter.atualiza(produtoDao.nomeAsc())
            }
            R.id.lista_descricaodesc ->{
                produtoDao.descricaoDesc()
                adapter.atualiza(produtoDao.descricaoDesc())
            }
            R.id.lista_descricaoasc ->{
                produtoDao.descricaoAsc()
                adapter.atualiza(produtoDao.descricaoAsc())
            }
            R.id.lista_valordesc ->{
                produtoDao.valorDesc()
                adapter.atualiza(produtoDao.valorDesc())
            }
            R.id.lista_valorasc ->{
                produtoDao.valorAsc()
                adapter.atualiza(produtoDao.valorAsc())
            }
            R.id.lista_semordem ->{
                produtoDao.buscaTodos()
                adapter.atualiza(produtoDao.buscaTodos())
            }
        }

        return super.onOptionsItemSelected(item)
    }


    private fun configuraFab() {
        val fab = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        fab.setOnClickListener(View.OnClickListener {
            vaiParaFormularioProduto()
        })
    }

    private fun vaiParaFormularioProduto() {
        val intent = Intent(this, FormularioProdutoActivity::class.java)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        adapter.atualiza(produtoDao.buscaTodos())

    }

    private fun configuraRecyclerview() {
        val recyclerView = binding.activityRecyclerview
        recyclerView.adapter = adapter
        adapter.quandoClicarNoItem = {
            val intent = Intent(
                this,
                DetalheProdutoActivity::class.java
            ).apply {
                putExtra(CHAVE_PRODUTO_ID, it.id)
            }
            startActivity(intent)
        }
    }

}