# MNDWRK - Apache Kafka Webinar

- [Webinar I. - Apache Kafka](https://www.mndwrk.com/events/esemenyvezerelt-szoftver-apache-kafka-fejlesztesi-alapok)
- [Webinar II. - Kafka Streams](https://www.mndwrk.com/events/esemenyvezerelt-szoftver-apache-kafka-fejlesztesi-alapok-ii)
- [Webinar III. - ksqlDB](https://www.mndwrk.com/events/esemenyvezerelt-szoftver-apache-kafka-fejlesztesi-alapok-iii)

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
