package org.educa.service;

import org.educa.dao.VueloDAO;
import org.educa.dao.VueloDAOImpl;
import org.educa.entity.AeropuertoEntity;
import org.educa.entity.VueloEntity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.time.Duration;
import java.util.List;

/**
 * Servicio de consulta de vuelos y cálculo del precio base.
 */
public class VueloService {

    /** Precio base por cada minuto de vuelo, en euros. */
    private static final BigDecimal PRECIO_POR_MINUTO = new BigDecimal("0.75");

    /** Número de decimales del precio base. */
    private static final int ESCALA_PRECIO = 2;

    private final VueloDAO vueloDAO = new VueloDAOImpl();

    /**
     * Obtiene los vuelos que salen de un aeropuerto de origen y no están cancelados.
     *
     * @param origen aeropuerto de origen
     * @return lista de vuelos ordenada por fecha de salida
     * @throws SQLException si se produce un error al consultar la base de datos
     */
    public List<VueloEntity> findByOrigen(AeropuertoEntity origen) throws SQLException {
        return vueloDAO.findByOrigen(origen);
    }

    /**
     * Calcula el precio base de un vuelo según su duración.
     *
     * @param vuelo vuelo del que se calcula el precio base
     * @return precio base en euros, con dos decimales
     */
    public BigDecimal getPrecioBase(VueloEntity vuelo) {
        long minutos = Duration.between(vuelo.getFecSalida(), vuelo.getFecLlegada()).toMinutes();
        return BigDecimal.valueOf(minutos)
                .multiply(PRECIO_POR_MINUTO)
                .setScale(ESCALA_PRECIO, RoundingMode.HALF_UP);
    }
}