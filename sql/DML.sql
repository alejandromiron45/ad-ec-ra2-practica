SET search_path TO gestion_vuelos;

TRUNCATE TABLE 
    equipajes, billetes, pasajeros, reservas, clientes,
    asignacion_tripulacion, habilitaciones_modelo, tripulacion,
    vuelos, asientos, aviones, modelos_avion, aeropuertos
RESTART IDENTITY CASCADE;

-- 1. Aeropuertos (con tasa_aero)
INSERT INTO aeropuertos (cod_iata, nombre, ciudad, pais, tasa_aero) VALUES
('MAD', 'Adolfo Suárez Madrid-Barajas', 'Madrid', 'España', 1850.00),
('BCN', 'Josep Tarradellas Barcelona-El Prat', 'Barcelona', 'España', 1600.00),
('SVQ', 'San Pablo', 'Sevilla', 'España', 950.00),
('BIO', 'Bilbao', 'Bilbao', 'España', 900.00),
('CDG', 'Charles de Gaulle', 'París', 'Francia', 2400.00),
('FCO', 'Leonardo da Vinci-Fiumicino', 'Roma', 'Italia', 1750.00);

-- 2. Modelos de Avión
INSERT INTO modelos_avion (cod_modelo, fabricante, capacidad) VALUES
('A320neo', 'Airbus', 180),
('A321LR', 'Airbus', 220),
('B737-MAX8', 'Boeing', 189),
('B787-9', 'Boeing', 290);

-- 3. Aviones (con coste_flete y consumo_hora en litros)
INSERT INTO aviones (matricula, id_modelo, coste_flete, consumo_hora, estado) VALUES
('EC-MTA', 1, 2800.00, 2400.00, 'OPERATIVO'), -- A320neo: 2.400 L/h
('EC-MTB', 2, 3200.00, 2700.00, 'OPERATIVO'), -- A321LR: 2.700 L/h
('EC-MTC', 3, 2900.00, 2550.00, 'OPERATIVO'), -- B737-MAX8: 2.550 L/h
('EC-MTD', 4, 6500.00, 5400.00, 'OPERATIVO'); -- B787-9: 5.400 L/h

-- 4. Asientos
INSERT INTO asientos (id_avion, cod_asiento, clase) VALUES
(1, '1A', 'BUSINESS'), (1, '1B', 'BUSINESS'), (1, '1C', 'BUSINESS'), (1, '1D', 'BUSINESS'),
(1, '2A', 'BUSINESS'), (1, '2B', 'BUSINESS'), (1, '2C', 'BUSINESS'), (1, '2D', 'BUSINESS'),
(1, '3A', 'TURISTA'),  (1, '3B', 'TURISTA'),  (1, '3C', 'TURISTA'),  (1, '3D', 'TURISTA'),
(1, '4A', 'TURISTA'),  (1, '4B', 'TURISTA'),  (1, '4C', 'TURISTA'),  (1, '4D', 'TURISTA'),

(2, '1A', 'BUSINESS'), (2, '1B', 'BUSINESS'), (2, '1C', 'BUSINESS'), (2, '1D', 'BUSINESS'),
(2, '2A', 'BUSINESS'), (2, '2B', 'BUSINESS'), (2, '2C', 'BUSINESS'), (2, '2D', 'BUSINESS'),
(2, '3A', 'TURISTA'),  (2, '3B', 'TURISTA'),  (2, '3C', 'TURISTA'),  (2, '3D', 'TURISTA'),
(2, '4A', 'TURISTA'),  (2, '4B', 'TURISTA'),  (2, '4C', 'TURISTA'),  (2, '4D', 'TURISTA'),

(3, '1A', 'BUSINESS'), (3, '1B', 'BUSINESS'), (3, '1C', 'BUSINESS'), (3, '1D', 'BUSINESS'),
(3, '2A', 'TURISTA'),  (3, '2B', 'TURISTA'),  (3, '2C', 'TURISTA'),  (3, '2D', 'TURISTA'),

(4, '1A', 'FIRST'),    (4, '1B', 'FIRST'),    (4, '1C', 'FIRST'),    (4, '1D', 'FIRST'),
(4, '2A', 'BUSINESS'), (4, '2B', 'BUSINESS'), (4, '2C', 'BUSINESS'), (4, '2D', 'BUSINESS'),
(4, '3A', 'TURISTA'),  (4, '3B', 'TURISTA'),  (4, '3C', 'TURISTA'),  (4, '3D', 'TURISTA');

-- 5. Vuelos (con coste_extra operativo)
INSERT INTO vuelos (num_vuelo, id_origen, id_destino, id_avion, fec_salida, fec_llegada, duracion, coste_extra, estado) VALUES
('IB3100', 1, 2, 1, '2026-10-15 07:00:00+02', '2026-10-15 08:15:00+02', '01:15:00', 450.00, 'PROGRAMADO'),
('IB3101', 2, 1, 1, '2026-10-15 09:30:00+02', '2026-10-15 10:45:00+02', '01:15:00', 450.00, 'PROGRAMADO'),
('IB3450', 1, 5, 2, '2026-10-15 11:00:00+02', '2026-10-15 13:10:00+02', '02:10:00', 850.00, 'PROGRAMADO'),
('IB3620', 1, 6, 3, '2026-10-16 15:30:00+02', '2026-10-16 18:05:00+02', '02:35:00', 920.00, 'PROGRAMADO'),
('IB3890', 3, 4, 4, '2026-10-16 18:45:00+02', '2026-10-16 20:15:00+02', '01:30:00', 600.00, 'PROGRAMADO');

-- 6. Tripulación
INSERT INTO tripulacion (licencia, nombre, p_apellido, s_apellido, rol) VALUES
('LIC-ES-0101', 'Alejandro', 'Martínez', 'García',   'COMANDANTE'),
('LIC-ES-0102', 'Laura',     'Fernández', 'López',    'COMANDANTE'),
('LIC-ES-0201', 'David',     'Sánchez',   'Ruiz',     'PRIMER_OFICIAL'),
('LIC-ES-0202', 'Elena',     'Gómez',     'Torres',   'PRIMER_OFICIAL'),
('LIC-ES-0301', 'Carlos',    'Navarro',   'Blanco',   'JEFE_CABINA'),
('LIC-ES-0302', 'Sofía',     'Molina',    'Serrano',  'JEFE_CABINA'),
('LIC-ES-0401', 'Javier',    'Ramírez',   'Gil',      'TRIPULANTE_CABINA'),
('LIC-ES-0402', 'Lucía',     'Castro',    'Ortiz',    'TRIPULANTE_CABINA');

-- 7. Habilitaciones Modelo
INSERT INTO habilitaciones_modelo (id_empleado, id_modelo) VALUES
(1, 1), (1, 2), (2, 3), (2, 4),
(3, 1), (3, 2), (4, 3),
(5, 1), (5, 2), (5, 3), (5, 4),
(6, 1), (6, 2),
(7, 1), (7, 2), (7, 3),
(8, 1), (8, 4);

-- 8. Asignación Tripulación
INSERT INTO asignacion_tripulacion (id_vuelo, id_empleado) VALUES
(1, 1), (1, 3), (1, 5), (1, 7),
(2, 1), (2, 3), (2, 5), (2, 7),
(3, 1), (3, 3), (3, 6), (3, 8),
(4, 2), (4, 4), (4, 5), (4, 7);

-- 9. Clientes
INSERT INTO clientes (dni, nombre, p_apellido, s_apellido, email, telefono) VALUES
('47123984A', 'Manuel', 'González', 'Pérez',     'manuel.gonzalez@correo.es', '+34600112233'),
('52981234B', 'Carmen', 'Rodríguez', 'Jiménez',  'carmen.rodriguez@correo.es', '+34611223344'),
('71456789C', 'Pablo',  'López',     'Martín',   'pablo.lopez@correo.es',    '+34622334455'),
('12345678C', 'Lara',  'Torres',     'Morgan',   'lara.torres@correo.es',    '+34622334466'),
('30987654D', 'Sara',   'Vázquez',   'Morales',  'sara.vazquez@correo.es',   '+34633445577');

-- 10. Reservas
INSERT INTO reservas (cod_reserva, id_cliente, fec_reserva, estado) VALUES
('W8X9K2', 1, '2026-09-10 10:15:00+02', 'CONFIRMADA'),
('M4T7P1', 2, '2026-09-12 14:30:00+02', 'CONFIRMADA'),
('B3N8Q5', 3, '2026-09-15 09:00:00+02', 'CONFIRMADA'),
('L9K2V7', 4, '2026-09-18 18:45:00+02', 'CONFIRMADA'),
('R5T2Z8', 4, '2026-09-20 12:20:00+02', 'CONFIRMADA'),
('FJT2N9', 5, '2026-09-20 12:20:00+02', 'CONFIRMADA');

-- 11. Pasajeros
INSERT INTO pasajeros (id_reserva, dni, nombre, p_apellido, s_apellido, email, telefono, fec_nac) VALUES
(1, '47123984A', 'Manuel',  'González', 'Pérez',    'manuel.gonzalez@correo.es', '+34600112233', '1982-05-14'),
(1, '48991234F', 'Marta',   'Herrera',  'Delgado',  'marta.herrera@correo.es',   '+34644556677', '1985-08-22'),
(1, '50112233G', 'Lucas',   'González', 'Herrera',  'lucas.gh@correo.es',        '+34655667788', '2014-11-03'),
(2, '52981234B', 'Carmen',  'Rodríguez', 'Jiménez', 'carmen.rodriguez@correo.es', '+34611223344', '1990-03-30'),
(2, '53445566H', 'Javier',  'Romero',    'Díaz',    'javier.romero@correo.es',    '+34666778899', '1988-12-19'),
(3, '71456789C', 'Pablo',   'López',     'Martín',  'pablo.lopez@correo.es',    '+34622334455', '1995-07-08'),
(4, '30987654D', 'Sara',    'Vázquez',   'Morales', 'sara.vazquez@correo.es',   '+34633445577', '1993-01-25'),
(5, '12345678C', 'Lara',  'Torres',     'Morgan',   'lara.torres@correo.es',    '+34622334466','1993-01-25'),
(6, '47123984A', 'Manuel',  'González', 'Pérez',    'manuel.gonzalez@correo.es', '+34600112233', '1982-05-14');

-- 12. Billetes
INSERT INTO billetes (id_pasajero, id_vuelo, id_asiento, precio) VALUES
(1, 1, 9,  85.50),
(2, 1, 10, 85.50),
(3, 1, 11, 65.00),
(4, 3, 17, 175.00),
(5, 3, 18, 175.00),
(6, 4, 33, 210.00),
(7, 1, 12, 92.00),
(7, 2, 9,  92.00),
(9, 2, 8,  92.00),
(8, 1, 1,  180.00);

-- 13. Equipajes
INSERT INTO equipajes (id_billete, peso, tipo) VALUES
(1, 22.40, 'FACTURADO_ESTANDAR'),
(2, 19.80, 'FACTURADO_ESTANDAR'),
(3, 8.50,  'MANO'),
(4, 23.00, 'FACTURADO_ESTANDAR'),
(5, 21.50, 'FACTURADO_ESTANDAR'),
(6, 18.20, 'FACTURADO_ESTANDAR');