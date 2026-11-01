package org.educa.dao;

import org.educa.entity.AeropuertoEntity;
import org.educa.entity.VueloEntity;
import org.educa.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VueloDAOImpl implements VueloDAO {
    @Override
    public List<VueloEntity> findByOrigen(AeropuertoEntity origen) throws SQLException {
        List<VueloEntity> vuelos = new ArrayList<>();
        String sql = "";
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    VueloEntity vuelo = new VueloEntity();

                    vuelos.add(vuelo);
                }

            }
        }
        return vuelos;
    }
}
