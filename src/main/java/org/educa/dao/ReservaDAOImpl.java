package org.educa.dao;

import org.educa.entity.ClienteEntity;
import org.educa.entity.ReservaEntity;
import org.educa.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAOImpl implements ReservaDAO {

    @Override
    public List<ReservaEntity> findReservaByCliente(ClienteEntity cliente) throws SQLException {
        List<ReservaEntity> list = new ArrayList<>();
        String sql = "SELECT r.id_reserva, r.cod_reserva, r.id_cliente, r.fec_reserva, r.estado " +
                "FROM RESERVAS r " +
                "WHERE r.id_cliente = ?";
        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, cliente.getIdCliente());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ReservaEntity reserva = new ReservaEntity();
                    reserva.setCliente(cliente);
                    reserva.setIdReserva(rs.getInt("id_reserva"));
                    reserva.setCodReserva(rs.getString("cod_reserva"));
                    reserva.setFecReserva(rs.getObject("fec_reserva", LocalDateTime.class));
                    reserva.setEstado(rs.getString("estado"));
                    list.add(reserva);
                }
            }
        }
        return list;
    }

    @Override
    public void save(Connection connection, ReservaEntity reserva) throws SQLException {
        String sql = "INSERT INTO reservas (cod_reserva, id_cliente) " +
                "VALUES (?,?) " +
                "RETURNING id_reserva";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, reserva.getCodReserva());
            ps.setInt(2, reserva.getCliente().getIdCliente());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    reserva.setIdReserva(rs.getInt("id_reserva"));
                }
            }
        }
    }

    @Override
    public boolean existsByCodigo(Connection connection, String codReserva) throws SQLException {
        String sql = "SELECT 1 FROM reservas r WHERE r.cod_reserva = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, codReserva);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
}