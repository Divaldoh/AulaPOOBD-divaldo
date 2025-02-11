package com.loja.dao;

import java.sql.SQLException;

public interface DAO<T> {
    public void cadastrar(T obj) throws SQLException;
}


