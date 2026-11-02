# ad-ec-ra2-practica

Práctica del RA2 de Acceso a Datos (2º DAM, I.E.S. El Cañaveral): sistema de reservas de vuelos en Java, BBDD de PostgreSQL.

## Funcionalidad

1. **Cliente**: se pide el DNI. Si no existe, se da de alta. Si existe, se muestran sus reservas previas (código, fecha y estado).
2. **Vuelos**: se elige el aeropuerto de origen y se muestran los vuelos no cancelados con destino, horarios y precio base (0,75 € por minuto de vuelo).
3. **Asientos**: se muestran los asientos libres del avión del vuelo, con su suplemento según la clase.
4. **Precio y reserva**: se pide la fecha de nacimiento del pasajero, se calcula el precio final (suplemento, descuento infantil del 20 % y descuento del 10 % por más de 3 reservas previas) y, si el cliente confirma, se guarda la reserva en una única transacción.

## Requisitos

- Java 21
- Maven 3.9 o superior
- PostgreSQL con el esquema `gestion_vuelos`


## Configuración

Edita `src/main/resources/database.properties` con los datos de tu servidor:

```properties
db.url=jdbc:postgresql://localhost:5432/postgres?currentSchema=gestion_vuelos
db.username=tu_usuario
db.password=tu_contraseña
db.timeout=5000
db.initialSize=5
db.maxTotal=10
```


## Base de datos

Ejecuta los scripts en este orden:

1. `DDL.sql`: crea el esquema, los tipos, las tablas y los índices.
2. `DML.sql`: carga los datos de prueba.
