package org.educa.dao;

import org.educa.entity.ClienteEntity;
import org.educa.entity.ReservaEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ReservaDAO {
    List<ReservaEntity> findReservaByCliente(ClienteEntity cliente) throws SQLException;

    boolean existsByCodigo(Connection connection, String codigo) throws SQLException;

    void save(Connection connection, ReservaEntity reserva) throws SQLException;
}
