package com.example.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraRecyclerview()
        configuraFab()

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
        val db = AppDatabase.instancia(this)
        val produtoDao = db.produtoDao()
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