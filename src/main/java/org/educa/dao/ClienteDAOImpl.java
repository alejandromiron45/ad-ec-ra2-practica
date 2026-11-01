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
        String sql = "SELECT * FROM CLIENTES c" +
                "WHERE c.dni = ?";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, dni);
            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()){
                    ClienteEntity cliente = new ClienteEntity();
                    cliente.setIdCliente(rs.getInt("id_cliente"));
                    cliente.setDni(rs.getString("dni"));
                    cliente.setNombre(rs.getString("nombre"));
                    cliente.setPrimerApellido(rs.getString("p_apellido"));
                    cliente.setSegundoApellido(rs.getString("s_apellido"));
                    cliente.setEmail(rs.getString("email"));
                    cliente.setTelefono(rs.getString("telefono"));
                    return cliente;
                }else{
                    return null;
                }
            }
        }

    }

        @Override
        public void saveCliente (ClienteEntity cliente){

        }

    }

