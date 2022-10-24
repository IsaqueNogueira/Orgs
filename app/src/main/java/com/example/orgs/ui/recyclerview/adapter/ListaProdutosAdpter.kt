package com.example.orgs.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.orgs.R
import com.example.orgs.databinding.ActivityFormularioProdutoBinding
import com.example.orgs.databinding.ProdutoItemBinding
import com.example.orgs.model.Produto
import java.text.NumberFormat
import java.util.*

class ListaProdutosAdpter(
    private val context: Context,
    produtos: List<Produto>
) : RecyclerView.Adapter<ListaProdutosAdpter.ViewHolder>() {


    private val produtos = produtos.toMutableList()

    class ViewHolder(private val binding: ProdutoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun vincula(produto: Produto) {
            val nome = binding.produtoItemNome
            nome.text = produto.nome

            val descricao = binding.produtoItemDescricao
            descricao.text = produto.descricao
            val valor = binding.valor
            val formatador = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
            val valorEmMoeda: String = formatador.format(produto.valor)
            valor.text = valorEmMoeda
            binding.activityFormularioProdutoImagem.load(produto.imagem)

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = ProdutoItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val produto = produtos[position]
        holder.vincula(produto)

    }

    override fun getItemCount(): Int = produtos.size

    fun atualiza(produtos: List<Produto>) {
        this.produtos.clear()
        this.produtos.addAll(produtos)
        notifyDataSetChanged()
    }


}
