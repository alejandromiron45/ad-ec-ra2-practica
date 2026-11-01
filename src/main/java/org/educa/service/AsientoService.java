package org.educa.service;

import org.educa.dao.AsientoDAO;
import org.educa.dao.AsientoDAOImpl;
import org.educa.entity.AsientoEntity;
import org.educa.entity.VueloEntity;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class AsientoService {

    private final AsientoDAO asientoDAO = new AsientoDAOImpl();

    public List<AsientoEntity> findDisponibles(VueloEntity vuelo) throws SQLException {
        List<AsientoEntity> asientos = asientoDAO.findDisponibles(vuelo);
        for (AsientoEntity asiento : asientos) {
            asiento.setSuplemento(getSuplemento(asiento.getClase()));
        }
        return asientos;
    }

    private BigDecimal getSuplemento(String clase) {
        return switch (clase) {
            case "TURISTA" -> new BigDecimal("0.00");
            case "PREMIUM_ECONOMY" -> new BigDecimal("35.00");
            case "BUSINESS" -> new BigDecimal("120.00");
            case "FIRST" -> new BigDecimal("250.00");
            default -> throw new IllegalArgumentException("Clase de asiento desconocida: " + clase);
        };
    }
}
