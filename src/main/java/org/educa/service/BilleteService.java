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

public class BilleteService {


    private final VueloService vueloService = new VueloService();
    private final BilleteDAO billeteDAO = new BilleteDAOImpl();


    public void calculatePrecio(BilleteEntity billete, int numReservas) {
        BigDecimal precio = vueloService.getPrecioBase(billete.getVuelo())
                .add(billete.getAsiento().getSuplemento());

        LocalDate fecSalida = billete.getVuelo().getFecSalida().toLocalDate();
        int edad = Period.between(billete.getPasajero().getFecNac(), fecSalida).getYears();

        if (edad < 12) {
            precio = precio.multiply(new BigDecimal("0.80"));
        }
        if (numReservas > 3) {
            precio = precio.multiply(new BigDecimal("0.90"));
        }
        billete.setPrecio(precio.setScale(2, RoundingMode.HALF_UP));
    }


    public void save(Connection connection, BilleteEntity billete) throws SQLException {
        billeteDAO.save(connection, billete);
    }
}
