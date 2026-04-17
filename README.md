# Notification Middleware

Middleware de notificaciones construido con **Spring Boot 3.2.5** (Java 17 + Gradle) que expone una API REST para publicar mensajes en **RabbitMQ** y procesarlos de forma asíncrona. Incluye un stack completo de observabilidad con **Promtail + Loki + Grafana**.

## Stack tecnológico

| Componente        | Versión       |
|-------------------|---------------|
| Java              | 17            |
| Spring Boot       | 3.2.5         |
| Spring AMQP       | (via Boot)    |
| Gson              | 2.13.2        |
| jqwik (PBT)       | 1.8.4         |
| RabbitMQ          | 3.13-management |
| Loki / Promtail   | 2.9.4         |
| Grafana           | 10.4.2        |

---

## Endpoints

| Método | Ruta               | Descripción                          | Respuesta exitosa |
|--------|--------------------|--------------------------------------|-------------------|
| POST   | `/post-message`    | Publica un mensaje en RabbitMQ       | `201 Created`     |
| GET    | `/healthStatus`    | Verifica que la aplicación está viva | `200 OK`          |

### POST `/post-message`

**Request body:**
```json
{
  "notificationType": "smtp",
  "subject": "Asunto del mensaje",
  "messageBody": "Cuerpo del mensaje",
  "htmlMessage": "<p>HTML opcional</p>",
  "senderEmail": "sender@example.com",
  "recipientsEmails": ["recipient@example.com"],
  "ccEmails": [],
  "bccEmails": [],
  "replayToEmails": [],
  "attachments": []
}
```

**Respuesta exitosa (`201 Created`):**
```json
{
  "appCodeName": "OK",
  "appMessage": "OK"
}
```

**Error — RabbitMQ no disponible (`200 OK` con código de error):**
```json
{
  "appCodeName": "FAIL_TO_POST_ON_MQ",
  "appMessage": null
}
```

### GET `/healthStatus`

```json
{
  "appCodeName": "OK",
  "appMessage": "OK"
}
```

---

## Arquitectura

```
POST /post-message
       │
       ▼
PostMessageController
       │
       ▼
PostMessageService
       │  serializa con Gson
       ▼
MessagePublisher ──► RabbitMQ (DirectExchange → Queue)
                                    │
                                    ▼
                      MessageProcessorController (@RabbitListener)
                                    │
                                    ▼
                            FactoryManager
                                    │  lookup por notificationType
                                    ▼
                      IMessageProcessorFactory.getInstance()
                                    │
                                    ▼
                          IMessageProcessor.process()
                          (ej: DefaultSmtpProcessor)
```

### Patrón Factory para procesadores

El sistema usa un patrón **Abstract Factory** para seleccionar el procesador correcto según el campo `notificationType` del mensaje:

| `notificationType` | Factory                        | Processor               |
|--------------------|--------------------------------|-------------------------|
| `smtp`             | `DefaultSmtpProcessorFactory`  | `DefaultSmtpProcessor`  |

`FactoryManager` inicializa el mapa de factories con **double-checked locking** para thread safety. Para agregar un nuevo tipo de notificación, basta con implementar `IMessageProcessorFactory` e `IMessageProcessor` y registrarlos en `FactoryManager.initFactories()`.

---

## Configuración

### Variables de entorno

Copia `.env.example` a `.env` y ajusta los valores:

```bash
cp .env.example .env
```

| Variable                  | Default                   | Descripción                              |
|---------------------------|---------------------------|------------------------------------------|
| `APP_PORT`                | `8080`                    | Puerto expuesto de la aplicación         |
| `APP_LOGS_PATH`           | —                         | Ruta del host donde se montan los logs   |
| `RABBITMQ_HOST`           | `rabbitmq` (Docker) / `localhost` (local) | Host de RabbitMQ        |
| `RABBITMQ_PORT`           | `5672`                    | Puerto AMQP                              |
| `RABBITMQ_USER`           | `guest`                   | Usuario RabbitMQ                         |
| `RABBITMQ_PASS`           | `guest`                   | Contraseña RabbitMQ                      |
| `RABBITMQ_QUEUE_NAME`     | `notification.queue`      | Nombre de la cola                        |
| `RABBITMQ_EXCHANGE_NAME`  | `notification.exchange`   | Nombre del exchange (DirectExchange)     |
| `RABBITMQ_ROUTING_KEY`    | `notification.key`        | Routing key                              |
| `RABBITMQ_MANAGEMENT_PORT`| `15672`                   | Puerto de la consola de administración   |
| `LOKI_PORT`               | `3100`                    | Puerto de Loki                           |
| `GRAFANA_PORT`            | `3000`                    | Puerto de Grafana                        |
| `GF_ADMIN_PASSWORD`       | `admin`                   | Contraseña del admin de Grafana          |

> `APP_LOGS_PATH` es obligatorio para el stack Docker. Ejemplo en Windows: `C:\Users\tu-usuario\Documents\_logs`

---

## Ejecución

### Local (sin Docker)

Requiere una instancia de RabbitMQ corriendo en `localhost:5672`.

```bash
./gradlew bootRun
```

La aplicación arranca en `http://localhost:8080`.

### Docker Compose (stack completo)

```bash
docker compose up --build
```

Servicios disponibles:

| Servicio              | URL                                      |
|-----------------------|------------------------------------------|
| API                   | `http://localhost:8080`                  |
| RabbitMQ Management   | `http://localhost:15672` (`guest/guest`) |
| Grafana               | `http://localhost:3000` (`admin/admin`)  |
| Loki                  | `http://localhost:3100`                  |

El servicio `notification-middleware` espera a que RabbitMQ pase su healthcheck (`rabbitmq-diagnostics ping`) antes de arrancar.

---

## Tests

```bash
./gradlew test
```

El proyecto usa **JUnit Jupiter** y **jqwik** (Property-Based Testing). Ambos engines se ejecutan con el mismo comando.

---

## Observabilidad — Logs con Grafana Loki

Los logs de la aplicación se escriben en `/app/logs/app.log` (dentro del contenedor) y se montan en el host vía `APP_LOGS_PATH`. Promtail los recoge y los envía a Loki.

### Formato de log

```
2024-01-15 10:30:45.123 [http-nio-8080-exec-1] DEBUG c.c.e.c.n._commons.filters.RequestBodyLoggingFilter - Incoming request [POST /post-message] | Headers: [...] | Body: {...}
```

El filtro `RequestBodyLoggingFilter` loggea el raw body y headers de cada request **antes** de la deserialización. Se activa solo en nivel `DEBUG`:

```properties
logging.level.com.corp.esaa.corp.notificationMiddleware._commons.filters.RequestBodyLoggingFilter=DEBUG
```

### Rotación de logs

| Parámetro              | Valor  |
|------------------------|--------|
| Tamaño máximo por archivo | 10 MB |
| Historial de archivos  | 7      |
| Tamaño total máximo    | 100 MB |
| Retención en Loki      | 30 días |

### Consultas LogQL en Grafana

1. Abrir `http://localhost:3000` → **Explore** → datasource **Loki**
2. Ejemplos de consultas:

```logql
# Todos los logs de la aplicación
{app="health-api"}

# Solo errores
{app="health-api"} |= "ERROR"

# Requests al endpoint post-message
{app="health-api"} |= "PostMessage"

# Filtrar por nivel extraído
{app="health-api", level="DEBUG"}
```

---

## Estructura del proyecto

```
src/main/java/com/corp/esaa/corp/notificationMiddleware/
├── NotificationMiddlewareApplication.java
├── apiPostMessage/
│   ├── controllers/
│   │   ├── PostMessageController.java      # POST /post-message
│   │   └── MessagePublisher.java           # Publica en RabbitMQ vía RabbitTemplate
│   └── services/
│       └── PostMessageService.java         # Lógica de negocio, manejo de AmqpException
├── messageProcessor/
│   ├── MessageProcessorController.java     # @RabbitListener — consume la cola
│   ├── FactoryManager.java                 # Registro de factories (double-checked locking)
│   ├── abstracts/
│   │   ├── IFactoryManager.java
│   │   ├── IMessageProcessorFactory.java
│   │   └── IMessageProcessor.java
│   ├── factories/
│   │   └── DefaultSmtpProcessorFactory.java
│   └── processors/
│       └── DefaultSmtpProcessor.java
├── healthStatus/
│   └── HealthController.java               # GET /healthStatus
└── _commons/
    ├── config/
    │   └── RabbitMQConfig.java             # Queue, DirectExchange, Binding, Gson bean
    ├── filters/
    │   ├── RequestBodyLoggingFilter.java   # Loggea raw body/headers (OncePerRequestFilter)
    │   └── CachingRequestWrapper.java      # Permite múltiples lecturas del InputStream
    └── models/
        └── api/
            ├── request/
            │   └── PostMessageRequestModel.java
            └── response/
                └── CommonResponseModel.java
```

```
config/
├── loki.yml                                # Retención 30 días, schema v13, TSDB
├── promtail.yml                            # Scraping de /var/log/app/*.log
└── grafana/
    └── provisioning/
        └── datasources/
            └── loki.yml                    # Datasource Loki pre-configurado
```
