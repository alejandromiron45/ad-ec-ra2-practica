package org.educa.service;

import org.educa.dao.AsientoDAO;
import org.educa.dao.AsientoDAOImpl;
import org.educa.entity.AsientoEntity;
import org.educa.entity.VueloEntity;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Servicio de consulta de asientos disponibles y cálculo de sus suplementos.
 */
public class AsientoService {

    /** Suplemento en euros de cada clase de asiento. */
    private static final Map<String, BigDecimal> SUPLEMENTOS = Map.of(
            "TURISTA", new BigDecimal("0.00"),
            "PREMIUM_ECONOMY", new BigDecimal("35.00"),
            "BUSINESS", new BigDecimal("120.00"),
            "FIRST", new BigDecimal("250.00"));

    private final AsientoDAO asientoDAO = new AsientoDAOImpl();

    /**
     * Obtiene los asientos libres de un vuelo, con su suplemento asignado.
     *
     * @param vuelo vuelo del que se buscan asientos disponibles
     * @return lista de asientos libres
     * @throws SQLException si se produce un error al consultar la base de datos
     */
    public List<AsientoEntity> findDisponibles(VueloEntity vuelo) throws SQLException {
        List<AsientoEntity> asientos = asientoDAO.findDisponibles(vuelo);
        for (AsientoEntity asiento : asientos) {
            asiento.setSuplemento(getSuplemento(asiento.getClase()));
        }
        return asientos;
    }

    /**
     * Devuelve el suplemento asociado a una clase de asiento.
     *
     * @param clase clase del asiento (TURISTA, PREMIUM_ECONOMY, BUSINESS o FIRST)
     * @return suplemento en euros
     * @throws IllegalArgumentException si la clase no existe
     */
    private static BigDecimal getSuplemento(String clase) {
        BigDecimal suplemento = SUPLEMENTOS.get(clase);
        if (suplemento == null) {
            throw new IllegalArgumentException("Clase de asiento desconocida: " + clase);
        }
        return suplemento;
    }
}