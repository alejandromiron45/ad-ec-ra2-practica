package org.educa.dao;

import org.educa.entity.ClienteEntity;

import java.sql.SQLException;

/**
 * Acceso a datos de los clientes.
 */
public interface ClienteDAO {

    /**
     * Busca un cliente por su DNI.
     *
     * @param dni DNI del cliente
     * @return cliente encontrado, o {@code null} si no existe
     * @throws SQLException si se produce un error al consultar la base de datos
     */
    ClienteEntity findByDni(String dni) throws SQLException;

    /**
     * Inserta un cliente en la base de datos. Al terminar, el identificador
     * generado queda asignado en el objeto recibido.
     *
     * @param cliente cliente a insertar
     * @throws SQLException si se produce un error al insertar el cliente
     */
    void saveCliente(ClienteEntity cliente) throws SQLException;
}