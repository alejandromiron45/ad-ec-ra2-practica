package org.educa.dao;

import org.educa.entity.ClienteEntity;
import org.educa.entity.ReservaEntity;

import java.util.List;

public class ResrvaDAOImpl implements ReservaDAO {
    @Override
    public List<ReservaEntity> findReservaByCliente(ClienteEntity cliente) {
        return List.of();
    }
}
