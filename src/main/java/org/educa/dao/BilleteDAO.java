package org.educa.dao;

import org.educa.entity.BilleteEntity;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Acceso a datos de los billetes.
 */
public interface BilleteDAO {

    /**
     * Inserta un billete dentro de la conexión recibida. Al terminar, el identificador
     * generado queda asignado en el objeto recibido.
     *
     * @param connection conexión con la transacción en curso
     * @param billete    billete a insertar
     * @throws SQLException si se produce un error al insertar el billete
     */
    void save(Connection connection, BilleteEntity billete) throws SQLException;
}