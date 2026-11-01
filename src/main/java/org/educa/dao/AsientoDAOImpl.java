package org.educa.dao;

import org.educa.entity.AsientoEntity;
import org.educa.entity.VueloEntity;

import java.util.List;

public class AsientoDAOImpl implements AsientoDAO {

    @Override
    public List<AsientoEntity> findDisponibles(VueloEntity vuelo) {
        return List.of();
    }
}
