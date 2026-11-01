package org.educa.dao;

import org.educa.entity.BilleteEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BilleteDAOImpl implements BilleteDAO {
    @Override
    public void save(Connection connection, BilleteEntity billete) throws SQLException, SQLException {
        String sql = "INSERT INTO billetes (id_pasajero, id_vuelo, id_asiento, precio) " +
                "VALUES (?,?,?,?) " +
                "RETURNING id_billete";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, billete.getPasajero().getIdPasajero());
            ps.setInt(2, billete.getVuelo().getIdVuelo());
            ps.setInt(3, billete.getAsiento().getIdAsiento());
            ps.setBigDecimal(4, billete.getPrecio());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    billete.setIdBillete(rs.getInt("id_billete"));
                }
            }
        }
    }
}