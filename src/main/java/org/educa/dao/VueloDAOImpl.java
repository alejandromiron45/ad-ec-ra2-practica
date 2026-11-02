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

/**
 * Implementación JDBC de {@link VueloDAO}.
 */
public class VueloDAOImpl implements VueloDAO {

    /**
     * Obtiene los vuelos que salen de un aeropuerto y no están cancelados, ordenados por fecha de salida.
     *
     * @param origen aeropuerto de origen
     * @return lista de vuelos
     * @throws SQLException si se produce un error al consultar la base de datos
     */
    @Override
    public List<VueloEntity> findByOrigen(AeropuertoEntity origen) throws SQLException {
        List<VueloEntity> vuelos = new ArrayList<>();
        String sql = "SELECT v.id_vuelo, v.num_vuelo, v.id_avion, v.fec_salida, v.fec_llegada, v.estado, " +
                "ad.id_aeropuerto AS id_destino, ad.cod_iata AS cod_iata_destino, " +
                "ad.nombre AS nombre_destino, ad.ciudad AS ciudad_destino " +
                "FROM vuelos v " +
                "INNER JOIN aeropuertos ad ON v.id_destino = ad.id_aeropuerto " +
                "WHERE v.id_origen = ? AND v.estado <> 'CANCELADO' " +
                "ORDER BY v.fec_salida";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, origen.getIdAeropuerto());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    VueloEntity vuelo = new VueloEntity();
                    vuelo.setIdVuelo(rs.getInt("id_vuelo"));
                    vuelo.setNumVuelo(rs.getString("num_vuelo"));
                    vuelo.setIdAvion(rs.getInt("id_avion"));
                    vuelo.setOrigen(origen);

                    AeropuertoEntity destino = new AeropuertoEntity();
                    destino.setIdAeropuerto(rs.getInt("id_destino"));
                    destino.setCodIata(rs.getString("cod_iata_destino"));
                    destino.setNombre(rs.getString("nombre_destino"));
                    destino.setCiudad(rs.getString("ciudad_destino"));
                    vuelo.setDestino(destino);

                    vuelo.setFecSalida(rs.getTimestamp("fec_salida").toLocalDateTime());
                    vuelo.setFecLlegada(rs.getTimestamp("fec_llegada").toLocalDateTime());
                    vuelo.setEstado(rs.getString("estado"));
                    vuelos.add(vuelo);
                }
            }
        }
        return vuelos;
    }
}