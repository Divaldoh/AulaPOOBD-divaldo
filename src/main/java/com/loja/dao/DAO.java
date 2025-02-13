package com.loja.dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DAO<T> {
    public ArrayList<T> buscarTodos() throws SQLException;
    public void salvar(T t) throws SQLException;
}


