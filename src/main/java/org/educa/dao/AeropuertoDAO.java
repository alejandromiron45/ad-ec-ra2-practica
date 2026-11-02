package org.educa.dao;

import org.educa.entity.AeropuertoEntity;

import java.sql.SQLException;
import java.util.List;

/**
 * Acceso a datos de los aeropuertos.
 */
public interface AeropuertoDAO {

    /**
     * Obtiene todos los aeropuertos almacenados.
     *
     * @return lista de aeropuertos ordenada por identificador
     * @throws SQLException si se produce un error al consultar la base de datos
     */
    List<AeropuertoEntity> findAll() throws SQLException;
}