# Getting Started

java: 17
Databases:
    postgresql
    mongodb

## Build

```bash
./gradlew clean build -x test
```

## Build via DOCKER

```bash
docker compose up -d
```

## OpenAPI
<a href="http://localhost:8080/openapi/webjars/swagger-ui/index.html">Swagger</a>


## Docker Images

### AMD
docker-compose.yml: platform: linux/amd64
Dokcerfile: FROM --platform=linux/x86_64 eclipse-temurin:17.0.10_7-jre-alpine

### ARM
docker-compose.yml: platform: linux/arm64/v8
Dokcerfile: FROM arm64v8/eclipse-temurin:17-jre-ubi9-minimal
