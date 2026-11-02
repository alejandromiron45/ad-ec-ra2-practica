package org.educa.dao;

import org.educa.entity.ClienteEntity;
import org.educa.entity.ReservaEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Acceso a datos de las reservas.
 */
public interface ReservaDAO {

    /**
     * Obtiene las reservas de un cliente, ordenadas por fecha.
     *
     * @param cliente cliente del que se buscan las reservas
     * @return lista de reservas del cliente
     * @throws SQLException si se produce un error al consultar la base de datos
     */
    List<ReservaEntity> findReservaByCliente(ClienteEntity cliente) throws SQLException;

    /**
     * Comprueba si ya existe una reserva con el código indicado.
     *
     * @param connection conexión con la transacción en curso
     * @param codigo     código de reserva a comprobar
     * @return {@code true} si el código ya existe, {@code false} en caso contrario
     * @throws SQLException si se produce un error al consultar la base de datos
     */
    boolean existsByCodigo(Connection connection, String codigo) throws SQLException;

    /**
     * Inserta una reserva dentro de la conexión recibida. Al terminar, el identificador
     * generado queda asignado en el objeto recibido.
     *
     * @param connection conexión con la transacción en curso
     * @param reserva    reserva a insertar
     * @throws SQLException si se produce un error al insertar la reserva
     */
    void save(Connection connection, ReservaEntity reserva) throws SQLException;
}