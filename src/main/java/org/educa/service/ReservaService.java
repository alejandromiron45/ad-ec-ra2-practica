package org.educa.service;

import org.educa.dao.ReservaDAO;
import org.educa.dao.ReservaDAOImpl;
import org.educa.entity.BilleteEntity;
import org.educa.entity.ClienteEntity;
import org.educa.entity.PasajeroEntity;
import org.educa.entity.ReservaEntity;
import org.educa.pool.ConnectionPool;
import org.educa.util.ReservaUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Servicio de gestión de reservas. Guardar una reserva inserta en una única
 * transacción la reserva, sus pasajeros y sus billetes.
 */
public class ReservaService {

    private final ReservaDAO reservaDAO = new ReservaDAOImpl();
    private final PasajeroService pasajeroService = new PasajeroService();
    private final BilleteService billeteService = new BilleteService();

    /**
     * Obtiene las reservas de un cliente.
     *
     * @param cliente cliente del que se buscan las reservas
     * @return lista de reservas ordenada por fecha
     * @throws SQLException si se produce un error al consultar la base de datos
     */
    public List<ReservaEntity> findByCliente(ClienteEntity cliente) throws SQLException {
        return reservaDAO.findReservaByCliente(cliente);
    }

    /**
     * Guarda una reserva con sus pasajeros y billetes en una única transacción.
     * Genera un código de reserva que no existe previamente. Si alguna inserción
     * falla, se deshace toda la operación.
     *
     * @param reserva reserva a guardar, con sus billetes asociados
     * @throws SQLException si se produce un error al guardar la reserva
     */
    public void save(ReservaEntity reserva) throws SQLException {
        try (Connection connection = ConnectionPool.getDataSource().getConnection()) {
            try {
                connection.setAutoCommit(false);

                String codigo;
                do {
                    codigo = ReservaUtils.generarCodigoReserva();
                } while (reservaDAO.existsByCodigo(connection, codigo));
                reserva.setCodReserva(codigo);

                reservaDAO.save(connection, reserva);

                for (BilleteEntity billete : reserva.getBilletes()) {
                    PasajeroEntity pasajero = billete.getPasajero();
                    pasajero.setIdReserva(reserva.getIdReserva());
                    pasajeroService.save(connection, pasajero);
                    billeteService.save(connection, billete);
                }

                connection.commit();
            } catch (SQLException | RuntimeException e) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackError) {
                    e.addSuppressed(rollbackError);
                }
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        }
    }
}