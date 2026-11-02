package org.educa.service;

import org.educa.dao.PasajeroDAO;
import org.educa.dao.PasajeroDAOImpl;
import org.educa.entity.PasajeroEntity;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Servicio de persistencia de pasajeros.
 */
public class PasajeroService {

    private final PasajeroDAO pasajeroDAO = new PasajeroDAOImpl();

    /**
     * Guarda un pasajero dentro de la conexión recibida. Al terminar, el identificador
     * generado queda asignado en el objeto recibido.
     *
     * @param connection conexión con la transacción en curso
     * @param pasajero   pasajero a guardar
     * @throws SQLException si se produce un error al insertar el pasajero
     */
    public void save(Connection connection, PasajeroEntity pasajero) throws SQLException {
        pasajeroDAO.save(connection, pasajero);
    }
}