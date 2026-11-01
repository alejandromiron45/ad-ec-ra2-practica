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

public class ReservaService {

    private final ReservaDAO reservaDAO = new ReservaDAOImpl();
    private final PasajeroService pasajeroService = new PasajeroService();
    private final BilleteService billeteService = new BilleteService();

    public List<ReservaEntity> findByCliente(ClienteEntity cliente) throws SQLException {
        return reservaDAO.findReservaByCliente(cliente);
    }

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
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        }
    }
}