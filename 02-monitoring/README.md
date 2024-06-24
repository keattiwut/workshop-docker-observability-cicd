

# Starting Your Application

`docker compose up -d frontend`

`docker compose up -d db`

`docker compose up -d user_api`

# Explore the application

## Access to frontend

`http://localhost:3000`

## Explore API service

### Get all users

`http://localhost:8080/api/v1/users`

### Get user by id

`http://localhost:8080/api/v1/users`

### Explore Application Metrics 

`http://localhost:8080/actuator`

`http://localhost:8080/actuator/health`

`http://localhost:8080/actuator/prometheus`

`http://localhost:8080/actuator/metrics`


```xml
    <dependency>
        <groupId>io.micrometer</groupId>
        <artifactId>micrometer-registry-prometheus</artifactId>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>io.opentelemetry</groupId>
        <artifactId>opentelemetry-api</artifactId>
    </dependency>
    <dependency>
        <groupId>io.micrometer</groupId>
        <artifactId>micrometer-registry-otlp</artifactId>
        <scope>runtime</scope>
    </dependency>
```

[references](https://docs.micrometer.io/micrometer/reference/implementations/otlp.html)

[otel-configuration](https://opentelemetry.io/docs/specs/otel/configuration/sdk-environment-variables/)



# Setup Obserability

`docker compose up -d lgtm`

references
[grafana/otel-lgtm](https://hub.docker.com/r/grafana/otel-lgtm)

[introduce grafana/otel-lgtm](https://grafana.com/blog/2024/03/13/an-opentelemetry-backend-in-a-docker-image-introducing-grafana/otel-lgtm/)


## Access to Grafana Dashboard

`http://localhost:4000`

Username: `admin` and Password `admin`


### Explore Datasources

Connections > Datasources

### Explore Dashboard (Default Application Metrics from Actuator)


### Add Logging Dashboard

Go to `Dashboard > New > New Dashboard > Import Dashboard`

Go to `grafana/dashboard-template/loki-log.json`

# trying to bulk some load to user_api

`hey -z 10s http://localhost:8080/api/v1/users`

`brew install hey`

