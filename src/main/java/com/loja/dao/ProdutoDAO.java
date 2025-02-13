package com.loja.dao;

import com.loja.domain.Produto;
import java.sql.SQLException;

public interface ProdutoDAO extends DAO<Produto> {
    public Produto buscarProduto(int ID) throws SQLException;
}
