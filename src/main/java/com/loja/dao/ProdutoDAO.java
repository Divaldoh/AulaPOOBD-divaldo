package com.loja.dao;

import com.loja.DTO.VendaDTO;
import com.loja.models.Produto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProdutoDAO extends DAO<Produto> {
    public void cadastrarProduto(Produto produto) throws SQLException;
    Produto buscarProduto(int id) throws SQLException;
    public void efetuarVenda(VendaDTO vendaDTO) throws SQLException;
    ArrayList<VendaDTO> buscarVenda() throws SQLException;

}
