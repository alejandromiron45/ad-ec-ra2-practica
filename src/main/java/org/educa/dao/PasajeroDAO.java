package org.educa.dao;

import org.educa.entity.PasajeroEntity;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Acceso a datos de los pasajeros.
 */
public interface PasajeroDAO {

    /**
     * Inserta un pasajero dentro de la conexión recibida. Al terminar, el identificador
     * generado queda asignado en el objeto recibido.
     *
     * @param connection conexión con la transacción en curso
     * @param pasajero   pasajero a insertar
     * @throws SQLException si se produce un error al insertar el pasajero
     */
    void save(Connection connection, PasajeroEntity pasajero) throws SQLException;
}