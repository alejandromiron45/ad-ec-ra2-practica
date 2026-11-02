package org.educa.dao;

import org.educa.entity.AeropuertoEntity;
import org.educa.entity.VueloEntity;

import java.sql.SQLException;
import java.util.List;

/**
 * Acceso a datos de los vuelos.
 */
public interface VueloDAO {

    /**
     * Obtiene los vuelos que salen de un aeropuerto y no están cancelados.
     *
     * @param origen aeropuerto de origen
     * @return lista de vuelos ordenada por fecha de salida
     * @throws SQLException si se produce un error al consultar la base de datos
     */
    List<VueloEntity> findByOrigen(AeropuertoEntity origen) throws SQLException;
}