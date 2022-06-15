# MNDWRK - Apache Kafka Webinar

## Getting started

```console
docker-compose up -d

docker exec -it  webinar-kafka-demo_broker_1 /bin/bash

kafka-console-producer --topic webinar-demo-inbound --bootstrap-server localhost:9092

```

#### Payload

```json
{"source": "TLC", "description":"Green signal"}
```

```json
{"source": "CCTV", "description":"People detected"}
```

```json
{"source": "SENSOR", "description":"Something happend"}
```

### ksqlDB
```sh
docker exec -it ksqldb-cli ksql http://ksqldb-server:8088
```

```console
kafka-console-consumer --topic webinar-demo-outbound --bootstrap-server localhost:9092
```

### Restart Broker

```console
sudo docker-compose down -v && sudo docker-compose up -d
```
