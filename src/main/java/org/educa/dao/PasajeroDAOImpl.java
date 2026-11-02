package org.educa.dao;

import org.educa.entity.PasajeroEntity;

import java.sql.*;

/**
 * Implementación JDBC de {@link PasajeroDAO}.
 */
public class PasajeroDAOImpl implements PasajeroDAO {

    /**
     * Inserta un pasajero dentro de la conexión recibida y asigna al objeto el identificador generado.
     *
     * @param connection conexión con la transacción en curso
     * @param pasajero   pasajero a insertar
     * @throws SQLException si se produce un error al insertar el pasajero
     */
    @Override
    public void save(Connection connection, PasajeroEntity pasajero) throws SQLException {
        String sql = "INSERT INTO pasajeros (id_reserva, dni, nombre, p_apellido, s_apellido, email, telefono, fec_nac) " +
                "VALUES (?,?,?,?,?,?,?,?) " +
                "RETURNING id_pasajero";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, pasajero.getIdReserva());
            ps.setString(2, pasajero.getDni());
            ps.setString(3, pasajero.getNombre());
            ps.setString(4, pasajero.getPrimerApellido());
            ps.setString(5, pasajero.getSegundoApellido());
            ps.setString(6, pasajero.getEmail());
            ps.setString(7, pasajero.getTelefono());
            ps.setDate(8, Date.valueOf(pasajero.getFecNac()));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    pasajero.setIdPasajero(rs.getInt("id_pasajero"));
                }
            }
        }
    }
}