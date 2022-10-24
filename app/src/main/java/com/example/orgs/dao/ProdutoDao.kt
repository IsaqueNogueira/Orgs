package com.example.orgs.dao

import com.example.orgs.model.Produto
import java.math.BigDecimal

class ProdutoDao {

    fun adiociona(produto: Produto) {

        produtos.add(produto)

    }

    fun buscaTodos(): List<Produto> {
        return produtos.toList()
    }

    companion object {
        private val produtos = mutableListOf<Produto>(
            Produto("Salada de frutas", "Laranja, maça e uva", BigDecimal("19.84"))
        )
    }
}