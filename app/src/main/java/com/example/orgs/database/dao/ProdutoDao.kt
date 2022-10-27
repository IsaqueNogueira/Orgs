package com.example.orgs.database.dao

import androidx.room.*
import com.example.orgs.model.Produto

@Dao
interface ProdutoDao {
    @Query("SELECT * FROM Produto")
    fun buscaTodos() : List<Produto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun salva(vararg produto: Produto)

    @Delete
    fun remove(produto: Produto)

    @Query("SELECT * FROM Produto WHERE id = :id")
    fun buscaPorId(id: Long) : Produto?


    @Query("SELECT * FROM Produto ORDER BY nome Desc")
    fun nomeDesc() : List<Produto>

    @Query("SELECT * FROM Produto ORDER BY nome Asc")
    fun nomeAsc() : List<Produto>

    @Query("SELECT * FROM Produto ORDER BY descricao Desc")
    fun descricaoDesc() : List<Produto>

    @Query("SELECT * FROM Produto ORDER BY descricao Asc")
    fun descricaoAsc() : List<Produto>

    @Query("SELECT * FROM Produto ORDER BY valor Desc")
    fun valorDesc() : List<Produto>

    @Query("SELECT * FROM Produto ORDER BY valor Asc")
    fun valorAsc() : List<Produto>
}