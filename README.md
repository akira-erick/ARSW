# ARSW

Make orchestrator jar

```
cd orchestrator
.\mvnw clean package -DskipTests
```

Run the project, on root of the project

```
docker-compose up -d --build
```

stop

```
docker-compose down -v
```

Orchestrator endpoint on:
```
http://localhost:8080/buy/
```

With type:

```
{
  "customerName": string,
  "item": int,
  "quantity": int,
  "amount": float,
  "work": boolean
}
```

Database on localhost:5433

To trigger the stock saga, just but more items than its on stock
To trigger the payment saga, just buy and spend more than 1000