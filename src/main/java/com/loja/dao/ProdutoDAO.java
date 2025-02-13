package com.loja.dao;

import com.loja.domain.Cliente;
import com.loja.domain.Produto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProdutoDAO extends DAO<Produto> {
    public Produto buscarProduto(int ID) throws SQLException;
}
