package org.educa.dao;

import org.educa.entity.ClienteEntity;
import org.educa.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteDAOImpl implements ClienteDAO {
    @Override
    public ClienteEntity findByDni(String dni) throws SQLException {
        String sql = "SELECT c.id_cliente, c.dni, c.nombre, c.p_apellido, c.s_apellido, c.email, c.telefono " +
                "FROM clientes c " +
                "WHERE c.dni = ?";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ClienteEntity cliente = new ClienteEntity();
                    cliente.setIdCliente(rs.getInt("id_cliente"));
                    cliente.setDni(rs.getString("dni"));
                    cliente.setNombre(rs.getString("nombre"));
                    cliente.setPrimerApellido(rs.getString("p_apellido"));
                    cliente.setSegundoApellido(rs.getString("s_apellido"));
                    cliente.setEmail(rs.getString("email"));
                    cliente.setTelefono(rs.getString("telefono"));
                    return cliente;
                } else {
                    return null;
                }
            }
        }

    }

    @Override
    public void saveCliente(ClienteEntity cliente) throws SQLException {
        String sql = "INSERT INTO clientes (dni, nombre, p_apellido, s_apellido, email, telefono) " +
                "VALUES (?,?,?,?,?,?) " +
                "RETURNING id_cliente";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, cliente.getDni());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getPrimerApellido());
            ps.setString(4, cliente.getSegundoApellido());
            ps.setString(5, cliente.getEmail());
            ps.setString(6, cliente.getTelefono());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    cliente.setIdCliente(rs.getInt("id_cliente"));
                }
            }
        }
    }
}


