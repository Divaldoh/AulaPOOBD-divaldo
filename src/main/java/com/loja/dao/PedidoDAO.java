package com.loja.dao;

import com.loja.domain.Pedido;

import java.sql.SQLException;

public interface PedidoDAO extends DAO<Pedido> {
        Pedido buscarPedido(int ID) throws SQLException;
}
