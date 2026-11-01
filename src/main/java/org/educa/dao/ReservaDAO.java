package org.educa.dao;

import org.educa.entity.ClienteEntity;
import org.educa.entity.ReservaEntity;

import java.util.List;

public interface ReservaDAO {
    List<ReservaEntity> findReservaByCliente(ClienteEntity cliente);
}
