package org.educa.dao;

import org.educa.entity.AsientoEntity;
import org.educa.entity.VueloEntity;

import java.sql.SQLException;
import java.util.List;

/**
 * Acceso a datos de los asientos de los aviones.
 */
public interface AsientoDAO {

    /**
     * Obtiene los asientos del avión de un vuelo que no tienen billete vendido para ese vuelo.
     *
     * @param vuelo vuelo del que se buscan asientos libres
     * @return lista de asientos libres ordenada por identificador
     * @throws SQLException si se produce un error al consultar la base de datos
     */
    List<AsientoEntity> findDisponibles(VueloEntity vuelo) throws SQLException;
}