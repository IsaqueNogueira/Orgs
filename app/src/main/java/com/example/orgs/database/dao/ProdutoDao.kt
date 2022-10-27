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
}