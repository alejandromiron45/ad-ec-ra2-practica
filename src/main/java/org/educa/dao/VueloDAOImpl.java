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
        String sql = "SELECT v.num_vuelo, ao.cod_iata AS cod_iata_origen, " +
                "ad.cod_iata AS cod_iata_destino, v.fec_salida, v.fec_llegada,  ad.ciudad  as ciudad_destino " +
                "FROM vuelos v " +
                "INNER JOIN aeropuertos ao ON v.id_origen = ao.id_aeropuerto " +
                "INNER JOIN aeropuertos ad ON v.id_destino = ad.id_aeropuerto " +
                "WHERE v.id_origen = ? " +
                "ORDER BY v.fec_salida";
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
