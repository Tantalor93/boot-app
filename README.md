# test application

## build
```
mvn clean package
```
## local run

runs application on `localhost:8080`
```
mvn spring-boot:run
```

## API doc
GUI doc provided on endpoint
```
/swagger-ui.html
```

## RabbitMQ integration
creating feedbacks sends given feedback to exchange `boot-app-exchange` with routing key which matches `feedback.#` routing key, 
for example `feedback.1`