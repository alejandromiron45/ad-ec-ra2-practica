DROP SCHEMA IF EXISTS gestion_vuelos CASCADE;
CREATE SCHEMA gestion_vuelos;
SET search_path TO gestion_vuelos;

-- Tipos ENUM
CREATE TYPE estado_avion AS ENUM ('OPERATIVO', 'MANTENIMIENTO', 'BAJA');
CREATE TYPE clase_asiento AS ENUM ('TURISTA', 'PREMIUM_ECONOMY', 'BUSINESS', 'FIRST');
CREATE TYPE estado_vuelo AS ENUM ('PROGRAMADO', 'EN_HORA', 'RETRASADO', 'EMBARCANDO', 'EN_VUELO', 'ATERRIZADO', 'CANCELADO');
CREATE TYPE rol_tripulante AS ENUM ('COMANDANTE', 'PRIMER_OFICIAL', 'JEFE_CABINA', 'TRIPULANTE_CABINA');
CREATE TYPE estado_reserva AS ENUM ('PENDIENTE', 'CONFIRMADA', 'CANCELADA');
CREATE TYPE tipo_equipaje AS ENUM ('MANO', 'FACTURADO_ESTANDAR', 'FACTURADO_ESPECIAL');

-- 1. Aeropuertos (con tasa_aero)
CREATE TABLE aeropuertos (
    id_aeropuerto INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    cod_iata CHAR(3) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    ciudad VARCHAR(80) NOT NULL,
    pais VARCHAR(80) NOT NULL,
    tasa_aero NUMERIC(8, 2) NOT NULL DEFAULT 1200.00 CHECK (tasa_aero >= 0),
    CONSTRAINT chk_cod_iata CHECK (cod_iata ~ '^[A-Z]{3}$')
);

-- 2. Modelos de Avión
CREATE TABLE modelos_avion (
    id_modelo INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    cod_modelo VARCHAR(20) NOT NULL UNIQUE,
    fabricante VARCHAR(50) NOT NULL,
    capacidad SMALLINT NOT NULL CHECK (capacidad > 0)
);

-- 3. Aviones (con coste_flete y consumo_hora)
CREATE TABLE aviones (
    id_avion INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    matricula VARCHAR(15) NOT NULL UNIQUE,
    id_modelo INT NOT NULL REFERENCES modelos_avion(id_modelo),
    coste_flete NUMERIC(10, 2) NOT NULL DEFAULT 2500.00 CHECK (coste_flete >= 0),
    consumo_hora NUMERIC(8, 2) NOT NULL CHECK (consumo_hora > 0), -- Litros/hora
    estado estado_avion NOT NULL DEFAULT 'OPERATIVO'
);

-- 4. Asientos
CREATE TABLE asientos (
    id_asiento INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_avion INT NOT NULL REFERENCES aviones(id_avion) ON DELETE CASCADE,
    cod_asiento VARCHAR(4) NOT NULL,
    clase clase_asiento NOT NULL DEFAULT 'TURISTA',
    CONSTRAINT uq_avion_asiento UNIQUE (id_avion, cod_asiento)
);

-- 5. Vuelos (con coste_extra)
CREATE TABLE vuelos (
    id_vuelo INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    num_vuelo VARCHAR(10) NOT NULL,
    id_origen INT NOT NULL REFERENCES aeropuertos(id_aeropuerto),
    id_destino INT NOT NULL REFERENCES aeropuertos(id_aeropuerto),
    id_avion INT NOT NULL REFERENCES aviones(id_avion),
    fec_salida TIMESTAMPTZ NOT NULL,
    fec_llegada TIMESTAMPTZ NOT NULL,
    duracion INTERVAL NOT NULL,
    coste_extra NUMERIC(8, 2) NOT NULL DEFAULT 600.00 CHECK (coste_extra >= 0),
    estado estado_vuelo NOT NULL DEFAULT 'PROGRAMADO',
    CONSTRAINT chk_origen_destino CHECK (id_origen <> id_destino),
    CONSTRAINT chk_horario_valido CHECK (fec_llegada > fec_salida)
);

-- 6. Tripulación
CREATE TABLE tripulacion (
    id_empleado INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    licencia VARCHAR(30) NOT NULL UNIQUE,
    nombre VARCHAR(50) NOT NULL,
    p_apellido VARCHAR(50) NOT NULL,
    s_apellido VARCHAR(50) NOT NULL,
    rol rol_tripulante NOT NULL
);

-- 7. Habilitaciones Modelo (N:M Pura)
CREATE TABLE habilitaciones_modelo (
    id_empleado INT NOT NULL REFERENCES tripulacion(id_empleado) ON DELETE CASCADE,
    id_modelo INT NOT NULL REFERENCES modelos_avion(id_modelo) ON DELETE CASCADE,
    PRIMARY KEY (id_empleado, id_modelo)
);

-- 8. Asignación Tripulación (N:M Pura)
CREATE TABLE asignacion_tripulacion (
    id_vuelo INT NOT NULL REFERENCES vuelos(id_vuelo) ON DELETE CASCADE,
    id_empleado INT NOT NULL REFERENCES tripulacion(id_empleado) ON DELETE CASCADE,
    PRIMARY KEY (id_vuelo, id_empleado)
);

-- 9. Clientes
CREATE TABLE clientes (
    id_cliente INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    dni VARCHAR(20) NOT NULL UNIQUE,
    nombre VARCHAR(50) NOT NULL,
    p_apellido VARCHAR(50) NOT NULL,
    s_apellido VARCHAR(50) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    telefono VARCHAR(20) NOT NULL
);

-- 10. Reservas
CREATE TABLE reservas (
    id_reserva INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    cod_reserva CHAR(6) NOT NULL UNIQUE,
    id_cliente INT NOT NULL REFERENCES clientes(id_cliente),
    fec_reserva TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    estado estado_reserva NOT NULL DEFAULT 'CONFIRMADA'
);

-- 11. Pasajeros
CREATE TABLE pasajeros (
    id_pasajero INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_reserva INT NOT NULL REFERENCES reservas(id_reserva) ON DELETE CASCADE,
    dni VARCHAR(20) NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    p_apellido VARCHAR(50) NOT NULL,
    s_apellido VARCHAR(50) NOT NULL,
    email VARCHAR(120) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    fec_nac DATE NOT NULL,
    CONSTRAINT uq_reserva_dni UNIQUE (id_reserva, dni)
);

-- 12. Billetes
CREATE TABLE billetes (
    id_billete INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_pasajero INT NOT NULL REFERENCES pasajeros(id_pasajero) ON DELETE CASCADE,
    id_vuelo INT NOT NULL REFERENCES vuelos(id_vuelo),
    id_asiento INT NOT NULL REFERENCES asientos(id_asiento),
    precio NUMERIC(8, 2) NOT NULL CHECK (precio >= 0),
    CONSTRAINT uq_vuelo_asiento UNIQUE (id_vuelo, id_asiento),
    CONSTRAINT uq_pasajero_vuelo UNIQUE (id_pasajero, id_vuelo)
);

-- 13. Equipajes
CREATE TABLE equipajes (
    id_equipaje INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_billete INT NOT NULL REFERENCES billetes(id_billete) ON DELETE CASCADE,
    peso NUMERIC(4, 2) NOT NULL CHECK (peso > 0 AND peso <= 50.00),
    tipo tipo_equipaje NOT NULL DEFAULT 'FACTURADO_ESTANDAR'
);

-- Índices
CREATE INDEX idx_vuelos_salida ON vuelos(fec_salida);
CREATE INDEX idx_vuelos_origen ON vuelos(id_origen);
CREATE INDEX idx_vuelos_destino ON vuelos(id_destino);
CREATE INDEX idx_pasajeros_reserva ON pasajeros(id_reserva);
CREATE INDEX idx_billetes_pasajero ON billetes(id_pasajero);
CREATE INDEX idx_billetes_vuelo ON billetes(id_vuelo);
CREATE INDEX idx_reservas_cliente ON reservas(id_cliente);