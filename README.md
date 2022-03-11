# MNDWRK - Apache Kafka Webinar

## Getting started

```console
docker-compose up -d

docker exec -it  webinar-kafka-demo_broker_1 /bin/bash

kafka-console-producer --topic meetup-demo-inbound --bootstrap-server localhost:9092

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

```console
kafka-console-consumer --topic meetup-demo-outbound --bootstrap-server localhost:9092
```
