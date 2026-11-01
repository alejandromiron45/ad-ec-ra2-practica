package org.educa.dao;

import org.educa.entity.AeropuertoEntity;
import org.educa.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AeropuertoDAOImpl implements AeropuertoDAO {
    @Override
    public List<AeropuertoEntity> findAll() throws SQLException {
        List<AeropuertoEntity> aeropuertos = new ArrayList<>();
        String sql = "SELECT a.id_aeropuerto, a.cod_iata, a.nombre, a.ciudad " +
                "FROM aeropuertos a " +
                "ORDER BY a.id_aeropuerto";
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                AeropuertoEntity aeropuerto = new AeropuertoEntity();
                aeropuerto.setIdAeropuerto(rs.getInt("id_aeropuerto"));
                aeropuerto.setCodIata(rs.getString("cod_iata"));
                aeropuerto.setNombre(rs.getString("nombre"));
                aeropuerto.setCiudad(rs.getString("ciudad"));
                aeropuertos.add(aeropuerto);
            }
        }
        return aeropuertos;
    }
}
