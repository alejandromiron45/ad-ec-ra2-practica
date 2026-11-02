package org.educa.service;

import org.educa.dao.BilleteDAO;
import org.educa.dao.BilleteDAOImpl;
import org.educa.entity.BilleteEntity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;

/**
 * Servicio de cálculo del precio de los billetes y de su persistencia.
 */
public class BilleteService {

    /** Edad máxima, en años y exclusiva, para aplicar el descuento infantil. */
    private static final int EDAD_INFANTIL = 12;

    /** Número de reservas previas a partir del cual se aplica el descuento por fidelidad (exclusivo). */
    private static final int RESERVAS_PARA_DESCUENTO = 3;

    /** Factor aplicado al precio con descuento infantil del 20 %. */
    private static final BigDecimal FACTOR_DESCUENTO_INFANTIL = new BigDecimal("0.80");

    /** Factor aplicado al precio con descuento por reservas previas del 10 %. */
    private static final BigDecimal FACTOR_DESCUENTO_RESERVAS = new BigDecimal("0.90");

    /** Número de decimales del precio final. */
    private static final int ESCALA_PRECIO = 2;

    private final VueloService vueloService = new VueloService();
    private final BilleteDAO billeteDAO = new BilleteDAOImpl();

    /**
     * Calcula el precio final de un billete aplicando el suplemento del asiento
     * y los descuentos infantil y por reservas previas, en ese orden.
     *
     * @param billete    billete cuyo precio se calcula y se actualiza
     * @param numReservas número de reservas previas del cliente
     */
    public void calculatePrecio(BilleteEntity billete, int numReservas) {
        BigDecimal precio = vueloService.getPrecioBase(billete.getVuelo())
                .add(billete.getAsiento().getSuplemento());

        LocalDate fecSalida = billete.getVuelo().getFecSalida().toLocalDate();
        int edad = Period.between(billete.getPasajero().getFecNac(), fecSalida).getYears();

        if (edad < EDAD_INFANTIL) {
            precio = precio.multiply(FACTOR_DESCUENTO_INFANTIL);
        }
        if (numReservas > RESERVAS_PARA_DESCUENTO) {
            precio = precio.multiply(FACTOR_DESCUENTO_RESERVAS);
        }
        billete.setPrecio(precio.setScale(ESCALA_PRECIO, RoundingMode.HALF_UP));
    }

    /**
     * Guarda un billete en la base de datos dentro de la conexión recibida.
     *
     * @param connection conexión con la transacción en curso
     * @param billete    billete a guardar
     * @throws SQLException si se produce un error al insertar el billete
     */
    public void save(Connection connection, BilleteEntity billete) throws SQLException {
        billeteDAO.save(connection, billete);
    }
}