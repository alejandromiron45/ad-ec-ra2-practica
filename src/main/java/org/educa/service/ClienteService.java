package org.educa.service;

import org.educa.dao.ClienteDAO;
import org.educa.dao.ClienteDAOImpl;
import org.educa.entity.ClienteEntity;

import java.sql.SQLException;

/**
 * Servicio de gestión de clientes.
 */
public class ClienteService {

    private final ClienteDAO clienteDAO = new ClienteDAOImpl();

    /**
     * Busca un cliente por su DNI.
     *
     * @param dni DNI del cliente
     * @return cliente encontrado, o {@code null} si no existe
     * @throws SQLException si se produce un error al consultar la base de datos
     */
    public ClienteEntity findByDNI(String dni) throws SQLException {
        return clienteDAO.findByDni(dni);
    }

    /**
     * Da de alta un cliente en la base de datos. Al terminar, el identificador
     * generado queda asignado en el objeto recibido.
     *
     * @param cliente cliente a registrar
     * @throws SQLException si se produce un error al insertar el cliente
     */
    public void saveCliente(ClienteEntity cliente) throws SQLException {
        clienteDAO.saveCliente(cliente);
    }
}