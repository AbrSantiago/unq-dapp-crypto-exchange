# Crypto Exchange API

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=AbrSantiago_unq-dapp-crypto-exchange&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=AbrSantiago_unq-dapp-crypto-exchange)

Swagger: http://localhost:8080/swagger-ui/index.html

## Descripción
Este proyecto es un MVP (Minimum Viable Product) para un sistema de intercambio de criptomonedas Peer-to-Peer (P2P). El objetivo es crear una comunidad de confianza para canjear criptomonedas por pesos argentinos. El sistema es similar a billeteras que permiten pagos P2P entre personas, como Airtm, Skrill o Binance.

## Características
- Registro de usuarios con validaciones estrictas.
- Visualización de cotizaciones de criptomonedas.
- Registro de intenciones de compra/venta de criptomonedas.
- Listado de intenciones activas de compra/venta.
- Procesamiento de transacciones entre usuarios.
- Informes de volumen operado de criptoactivos entre dos fechas.
- APIs documentadas con Swagger.
- Aplicación stateless.

---
## Entrega Nro 1

| Entregable | Estado |
|------------|--------|
| Creación de repositorio GitHub | 🟢     |
| Configuración en GitHubActions | 🟢     |
| Build corriendo y SUCCESS | 🟢     |
| SonarCloud (Registrar el proyecto Backend) | 🟢     |
| TAG en GitHub y Confeccionar Release Notes | 🟢     |
| Clean Code según la materia (todo en Inglés) | 🟢     |
| Configuración de Swagger en el back-API (v3) | 🟢     |
| Implementar modelo completo | 🟢     |
| Testing automático unitario | 🟢     |
| Proveer servicio de registración de usuario (punto 1) | 🟢     |

## Entrega Nro 2

| Entregable | Estado |
|------------|--------|
| Estado de build en "Verde" | 🟢     |
| Utilizar HSQLDB para persistir datos (opcion H2) | 🟢     |
| Crear datos de prueba cuando levanta la aplicación | 🟢     |
| Documentation de Endpoints (APIs) con Swagger (v3) | 🟢      |
| TAG en GitHub y Confeccionar Release Notes de entrega 2 | 🟢      |
| Implementar JOB de Coverage | 🟢     |
| Listar cotizacion de criptoactivos | 🟢     |
| Permitir que un usuario exprese su intención de compra/venta | 🟢     |
| Construir un listado donde se muestran las intenciones activas de compra/venta | 🟢     |
| Procesar la transacción informada por un usuario | 🟢      |
| Informar al usuario el volumen operado de cripto activos entre dos fechas | 🟢      |
| Testing integral de 2 controllers (end-to-end) | 🟢     |

## Entrega Nro 3

| Entregable | Estado |
|------------|-------|
| Crear un test de arquitectura | 🟢    |
| Auditoria de Web-Services. Loguear <timestamp,user,operación/metodo, parámetros, tiempoDeEjecicion> de los servicios publicados con Spring utilizando Log4j/logback | 🟢      |
| TAG en GitHub y Release Notes |       |
| Configurar https://www.baeldung.com/spring-boot-prometheus (Metricas) | 🟢     |
| Configurar https://www.baeldung.com/spring-boot-actuators (Endpoints de Monitoreo) | 🟢     |
| Segurizar el acceso a la API (JWT) | 🟢      |
| Mostrar las cotizaciones de las últimas 24hs para un cripto activo dado |       |
| Listado de cotizaciones (alta performance - implementar cache) |       |
