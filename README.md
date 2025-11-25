# ARSW

Make orchestrator jar

```
cd orchestrator
.\mvnw clean package -DskipTests
```

Run the project

```
docker-compose up -d --build
```

stop

```
docker-compose down -v
```